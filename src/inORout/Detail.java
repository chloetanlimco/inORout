package inORout;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class Detail
 */
@WebServlet("/Detail")
public class Detail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Detail() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * Also consider just passing objects, then no need to make a second call unless
		 * you want to consider case where updated between pages
		 */
//		String searchType = request.getParameter("searchType");
		String recipe = request.getParameter("recipe");
		String restaurant = request.getParameter("restaurant");


		String encodedLink = null;
		if (recipe != null) {
			encodedLink = URLEncoder.encode(recipe, StandardCharsets.UTF_8.toString());
		}
		String username = (String) request.getSession().getAttribute("Current user");
		System.out.println(username + "is the user");

		String error = "";
		String next = "/Detail.jsp";

		// read in config file with API keys

		Vector<String> ids;
		Vector<String> keys;
		int numkeys;
		String YelpBearerId;

		FileReader fr = new FileReader(getServletContext().getRealPath("/WEB-INF/config.txt"));
		Properties p = new Properties();
		p.load(fr);

		ids = new Vector<String>();
		keys = new Vector<String>();
		YelpBearerId = p.getProperty("Yelp");
		keys.add(p.getProperty("EdamamKey"));
		keys.add(p.getProperty("EdamamKey2"));
		keys.add(p.getProperty("EdamamKey3"));
		keys.add(p.getProperty("EdamamKey4"));
		keys.add(p.getProperty("EdamamKey5"));
		ids.add(p.getProperty("EdamamId"));
		ids.add(p.getProperty("EdamamId2"));
		ids.add(p.getProperty("EdamamId3"));
		ids.add(p.getProperty("EdamamId4"));
		ids.add(p.getProperty("EdamamId5"));
		numkeys = keys.size();

		// restaurant details
		Business b = null;
		Recipe r = null;
		boolean fav = false;
		int sleeptime = 125;
		if (restaurant != null) {
			boolean success = false;
			while (!success) {
				Connection conn = null;
				PreparedStatement st = null;
				ResultSet rs = null;
				try {
					URL url = new URL("https://api.yelp.com/v3/businesses/" + restaurant);

					HttpURLConnection yelpCon = (HttpURLConnection) url.openConnection();
					// add headers
					yelpCon.setRequestProperty("Authorization", "Bearer " + YelpBearerId);
					yelpCon.setRequestMethod("GET");

					// parsing JSON
					JsonParser jsonParser = new JsonParser();
					JsonObject jsonObject = (JsonObject) jsonParser
							.parse(new InputStreamReader(yelpCon.getInputStream(), "UTF-8"));
					System.out.println(jsonObject);

					/* Need to check if returned as array or single object */
					// JsonArray businesses = jsonObject.getAsJsonArray("businesses");
					b = new Business(jsonObject);

					// see if restaurant in user's favorites
					// if username already taken

					conn = DriverManager.getConnection(
							"jdbc:mysql://google/foodapp?cloudSqlInstance=groupproject-258805:us-central1:project201&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=anthonyuser&password=wQHL223i4LJhEuCl1");
					st = conn.prepareStatement(
							"SELECT * from Restaurant WHERE userID=(SELECT userID from User WHERE username=?)");
					st.setString(1, username);
					rs = st.executeQuery();
					System.out.println("hi");


					while (rs.next()) {
						// if recipe already in favorites
						System.out.println(restaurant + " the restaurant");
						if (rs.getString("restaurantID").equals(restaurant)) {
							System.out.println(rs.getString("restaurantID") + "More stuff");
							
							fav = true;
						}
					}
					success = true;
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
					try {
						Thread.sleep(sleeptime);
					} catch (InterruptedException e1) {
						System.out.println(e.getMessage());
					}
					sleeptime *= 2;
					System.out.println(sleeptime);
					if (sleeptime == 2000) {
						break;
					}

				} finally {
					try {
						if (conn != null) {
							conn.close();
						}
						if (st != null) {
							st.close();
						}
						if (rs != null) {
							rs.close();
						}
					} catch (SQLException sqle) {
						System.out.println(sqle.getMessage());
					}
				}
			}
		}
		// recipe details
		/* Might not need if we want to forward to original site */
		else if (recipe != null) {
			int current = 0;
			while (current != numkeys) {
				Connection conn = null;
				PreparedStatement st = null;
				ResultSet rs = null;
				try {
					String params = "r=" + encodedLink + "&app_key=" + keys.get(current % numkeys) + "&app_id="
							+ ids.get(current % numkeys);
					URL url = new URL("https://api.edamam.com/search?" + params);

					HttpURLConnection edamamCon = (HttpURLConnection) url.openConnection();
					edamamCon.setRequestMethod("GET");

					// parsing JSON
					JsonParser jsonParser = new JsonParser();
					if (edamamCon.getInputStream() != null) {
						JsonArray jsonObject = (JsonArray) jsonParser
								.parse(new InputStreamReader(edamamCon.getInputStream(), "UTF-8"));

						JsonObject obj = jsonObject.get(0).getAsJsonObject();
						System.out.println(obj);
						// JsonArray recipes = jsonObject.getAsJsonArray("hits");
						r = new Recipe(obj, "detail");
					}
					// see if recipe in user's favorites
					// if username already taken

					conn = DriverManager.getConnection(
							"jdbc:mysql://google/foodapp?cloudSqlInstance=groupproject-258805:us-central1:project201&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=anthonyuser&password=wQHL223i4LJhEuCl1");
					st = conn.prepareStatement(
							"SELECT * from Recipe WHERE userID=(SELECT userID from User WHERE username=?)");
					st.setString(1, username);
					rs = st.executeQuery();

					while (rs.next()) {
						// if recipe already in favorites
						if (rs.getString("name").contentEquals(r.label)) {
							fav = true;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					current = current + 1;
					System.out.println(e.getMessage());
					e.printStackTrace();
					System.out.println(current);
				}
				try {
					if (conn != null) {
						conn.close();
					}
					if (st != null) {
						st.close();
					}
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException sqle) {
					System.out.println(sqle.getMessage());
				}
			}
		}

		/* Front End grab this value to display error */
		request.setAttribute("detail-error", error);

		// detail results
		request.setAttribute("business", b); // business object
		request.setAttribute("recipe", r); // restaurant object
		request.setAttribute("favorite", fav); // boolean

		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(next);

		try {
			dispatch.forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
