package inORout;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/* Also consider just passing objects, then no need to make a second call
		 * unless you want to consider case where updated between pages */
		String searchType = request.getParameter("searchType");
		String restaurant = request.getParameter("restaurant");
		String recipe = request.getParameter("recipe");
		
		String error = "";
		String next = "Detail.jsp";
		
		// read in config file with API keys
		String YelpBearerId = "";
		String app_key = "";
		String app_id = "";
		try {
			FileReader fr = new FileReader(getServletContext().getRealPath("/WEB-INF/config.txt"));
			Properties p = new Properties();
			p.load(fr);
			
			YelpBearerId = p.getProperty("Yelp");
			app_key = p.getProperty("EdamamKey");
			app_id = p.getProperty("EdamamId");
			
		} catch (FileNotFoundException e) {
			System.out.println("Make sure you have a config.txt file!");
		}
		
		//restaurant details
		Business b = null;
		Recipe r = null;
		if (searchType.contains("Restaurant")) {

			URL url = new URL("https://api.yelp.com/v3/businesses/" + restaurant);

			HttpURLConnection yelpCon = (HttpURLConnection) url.openConnection();
			// add headers
			yelpCon.setRequestProperty("Authorization", "Bearer " + YelpBearerId);
			yelpCon.setRequestMethod("GET");

			// parsing JSON
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = (JsonObject)jsonParser.parse(
				      new InputStreamReader(yelpCon.getInputStream(), "UTF-8"));
			
			int total = jsonObject.getAsJsonPrimitive("total").getAsInt();
			
			/* Need to check if returned as array or single object */
			JsonArray businesses = jsonObject.getAsJsonArray("businesses");
			b  = new Business(businesses.get(0).getAsJsonObject());
			
			
			// maximum 20 elements to return - can change this if you want
//			for (int i = 0; i < ((total < 20) ? total : 20); i++) {
//				// if not closed
//				if (!businesses.get(i).getAsJsonObject().getAsJsonPrimitive("is_closed").getAsBoolean()) {
//					Business b = new Business(businesses.get(i).getAsJsonObject());
//					YelpResults.add(b);
//				}
//			}
		}
		//recipe details
		/* Might not need if we want to forward to original site */
		else {
			
			/* Not sure for this search, source might just give original site? */
			String params = "r=" + recipe + "&app_key=" + app_key + "&app_id=" + app_id;
			URL url = new URL("https://api.edamam.com/search?" + params);
			
			HttpURLConnection edamamCon = (HttpURLConnection) url.openConnection(); 
			edamamCon.setRequestMethod("GET");
			
			// parsing JSON
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = (JsonObject)jsonParser.parse(
				      new InputStreamReader(edamamCon.getInputStream(), "UTF-8"));
			
			JsonArray recipes = jsonObject.getAsJsonArray("hits");
			r = new Recipe(recipes.get(0).getAsJsonObject());
			
			// figure out what to iterate up to
//			for (int i = 0; i < 10; i++) {
//				Recipe r = new Recipe(recipes.get(i).getAsJsonObject());
//				EdamamResults.add(r);
//			}
		}
		
		/* Front End grab this value to display error */ 
		request.setAttribute("login-error", error);
		
		//detail results
		request.setAttribute("business", b);
		request.setAttribute("recipe", r);
		
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(next);
		
		try {
			dispatch.forward(request, response);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch (ServletException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
