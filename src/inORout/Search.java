package inORout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Vector;

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

import inORout.Business;
import inORout.Recipe;

@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Vector<String> ids;
	Vector<String> keys;
	int numkeys;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String searchTerm = request.getParameter("searchTerm");
		if (searchTerm == null || searchTerm.equals("")) {
			request.setAttribute("error", "No search term entered!");
			RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/HomePage.jsp");
			try {
				dispatch.forward(request, response);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ServletException e) {
				e.printStackTrace();
			}
		} else {

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
				ids = new Vector<String>();
				keys = new Vector<String>();
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

			} catch (FileNotFoundException e) {
				System.out.println("Make sure you have a config.txt file!");
			}

			Vector<Business> YelpResults = new Vector<Business>();
			Vector<Recipe> EdamamResults = new Vector<Recipe>();

			// YELP API CALL
			System.out.println("here");
			int sleeptime = 125;
			if (!searchTerm.contentEquals("")) {
				boolean yelpsuccess = false;
				while (!yelpsuccess) {
					try {
						HttpSession session = request.getSession();
						String latitude = "34.0205";
						String longitude = "-118.2856";
						if (session.getAttribute("latitude") != null && session.getAttribute("longitude") != null) {
							latitude = (String) session.getAttribute("latitude");
							longitude = (String) session.getAttribute("longitude");
						}
						String params = "term=" + searchTerm.replace(" ", "+") + "&latitude=" + latitude + "&longitude="
								+ longitude;

						URL url = new URL("https://api.yelp.com/v3/businesses/search?" + params);

						HttpURLConnection yelpCon = (HttpURLConnection) url.openConnection();
						// add headers
						yelpCon.setRequestProperty("Authorization", "Bearer " + YelpBearerId);
						yelpCon.setRequestMethod("GET");

						// parsing JSON
						JsonParser jsonParser = new JsonParser();
						JsonObject jsonObject = (JsonObject) jsonParser
								.parse(new InputStreamReader(yelpCon.getInputStream(), "UTF-8"));

						int total = jsonObject.getAsJsonPrimitive("total").getAsInt();
						JsonArray businesses = jsonObject.getAsJsonArray("businesses");

						// maximum 20 elements to return - can change this if you want
						for (int i = 0; i < ((total < 20) ? total : 20); i++) {
							// if not closed
							if (!businesses.get(i).getAsJsonObject().getAsJsonPrimitive("is_closed").getAsBoolean()) {
								Business b = new Business(businesses.get(i).getAsJsonObject());
								YelpResults.add(b);
							}
						}
						yelpsuccess = true;
					} catch (Exception e) {
						System.out.println(e.getMessage());
						try {
							Thread.sleep(sleeptime);
						} catch (InterruptedException e1) {
							System.out.println(e.getMessage());
						}
						sleeptime *=2;
						System.out.println(sleeptime);
						if(sleeptime == 2000) {
							break;
						}
					}
				}
			}

			// EDAMAM API CALL
			int current = 0;
			while (true) {
				try {

					if (!searchTerm.contentEquals("")) {

						String params = "q=" + searchTerm.replace(" ", "+") + "&app_key=" + keys.get(current)
								+ "&app_id=" + ids.get(current);
						URL url = new URL("https://api.edamam.com/search?" + params);

						HttpURLConnection edamamCon = (HttpURLConnection) url.openConnection();
						edamamCon.setRequestMethod("GET");

						// parsing JSON
						JsonParser jsonParser = new JsonParser();
						JsonObject jsonObject = (JsonObject) jsonParser
								.parse(new InputStreamReader(edamamCon.getInputStream(), "UTF-8"));

						JsonArray recipes = jsonObject.getAsJsonArray("hits");

						// figure out what to iterate up to
						int it = 10;
						if (recipes.size() < 10) {
							it = recipes.size();
						}
						for (int i = 0; i < it; i++) {
							Recipe r = new Recipe(recipes.get(i).getAsJsonObject());
							EdamamResults.add(r);
						}
					}

					// pass search result arrays back
					Business[] YResults = new Business[YelpResults.size()];
					for (int i = 0; i < YelpResults.size(); i++) {
						YResults[i] = YelpResults.get(i);
					}
					Recipe[] EResults = new Recipe[EdamamResults.size()];
					for (int i = 0; i < EdamamResults.size(); i++) {
						EResults[i] = EdamamResults.get(i);
					}
					request.setAttribute("YelpResults", YResults);
					request.setAttribute("EdamamResults", EResults);
					request.setAttribute("searchType", request.getParameter("searchType"));
					request.setAttribute("searchTerm", searchTerm);
					System.out.println(request.getParameter("searchType"));

					// send it back
					// CHANGE THIS TO CORRECT PAGE FOR REDIRECT
					RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/SearchResults.jsp");
					try {
						dispatch.forward(request, response);
						break;
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ServletException e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					current = (current + 1) % numkeys;
					System.out.println(e.getMessage());
					System.out.println(current);
					if (current == 0) {
						break;
					}
				}

			}

		}
	}

}