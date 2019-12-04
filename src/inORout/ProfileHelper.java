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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class Profile
 */
@WebServlet("/ProfileHelper")
public class ProfileHelper extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String YelpBearerId;
	Vector<Recipe> LikedRecipes;
	Vector<Business> LikedBusinesses;
	Map<String, Vector<Business>> YObject;
	Map<String, Vector<Recipe>> EObject;
	Vector<String> RecipeIDs;
	Vector<String> BusinessIDs;
	CountDownLatch restaurantlatch;
	CountDownLatch recipelatch;
	HttpSession session;
	Vector<String> ids;
	Vector<String> keys;
	int numkeys;
	int businesslength;
	int recipelength;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		RecipeIDs = new Vector<String>();
		BusinessIDs = new Vector<String>();
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://google/foodapp?cloudSqlInstance=groupproject-258805:us-central1:project201&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=anthonyuser&password=wQHL223i4LJhEuCl1");
			PreparedStatement st = conn.prepareStatement("SELECT userID from User WHERE username=?");
			st.setString(1, request.getSession().getAttribute("Current user").toString());
			int userID;
			ResultSet userObj = st.executeQuery();
			if (userObj.next()) {
				userID = userObj.getInt("userID");
				// SQL statement, grabs favorites in order of timestamp, so most recent is at
				// the top
				// Businesses
				ps = conn.prepareStatement("SELECT * from Restaurant WHERE userID=? ORDER BY time DESC");
				ps.setInt(1, userID);
				rs = ps.executeQuery();
				// Add to BusinessIDs array
				while (rs.next()) {
					BusinessIDs.add(rs.getString("restaurantID"));
				}
				// SQL statement, grabs favorites in order of timestamp, so most recent is at
				// the top
				// Recipes
				ps.close();
				ps = conn.prepareStatement("SELECT * from Recipe WHERE userID=? ORDER BY time DESC");
				ps.setInt(1, userID);
				rs.close();
				rs = ps.executeQuery();
				// Add to BusinessIDs array
				while (rs.next()) {
					RecipeIDs.add(rs.getString("recipeID"));
				}
				ps.close();
				st.close();
				rs.close();
			}
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		// SUGGESTIONS
		// read in config file with API keys

		FileReader fr = new FileReader(getServletContext().getRealPath("/WEB-INF/config.txt"));
		Properties p = new Properties();
		p.load(fr);
		YelpBearerId = p.getProperty("Yelp");
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


		YObject = new TreeMap<String, Vector<Business>>();
		EObject = new TreeMap<String, Vector<Recipe>>();

		LikedRecipes = new Vector<Recipe>();
		LikedBusinesses = new Vector<Business>();
		LikedRecipes.setSize((RecipeIDs.size() > 3 ? 3 : RecipeIDs.size()));
		LikedBusinesses.setSize((BusinessIDs.size() > 3 ? 3 : BusinessIDs.size()));
		recipelength = LikedRecipes.size();
		businesslength = LikedBusinesses.size();
		
		session = request.getSession();
		String latitude = "34.0205";
		String longitude = "-118.2856";
		if (session.getAttribute("latitude") != null && session.getAttribute("longitude") != null) {
			latitude = (String) session.getAttribute("latitude");
			longitude = (String) session.getAttribute("longitude");
		}
		// RESTAURANTS
		restaurantlatch = new CountDownLatch((BusinessIDs.size() > 3 ? 3 : BusinessIDs.size()));
		for (int i = 0; i < (BusinessIDs.size() > 3 ? 3 : BusinessIDs.size()); i++) {
			new ProfileYelpCall(this, latitude, longitude, i);		
			}
		
		// RECIPES
		recipelatch = new CountDownLatch(LikedRecipes.size());
		for (int i = 0; i < LikedRecipes.size(); i++) {		
			new ProfileEdamamCall(this, i);
		}
		
		try {
			restaurantlatch.await();
			recipelatch.await();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		// PASS MAP OF SUGGESTIONS BACK
		Gson gson = new Gson();
		Type gsonType = new TypeToken<HashMap>() {
		}.getType();
		String eGson = gson.toJson(EObject, gsonType);
		String yGson = gson.toJson(YObject, gsonType);
		request.setAttribute("RecipesSuggestions", eGson);
		request.setAttribute("BusinessSuggestions", yGson);
		Recipe[] RecipeFav = LikedRecipes.toArray(new Recipe[LikedRecipes.size()]);
		Business[] BusinessFav = LikedBusinesses.toArray(new Business[LikedBusinesses.size()]);
		// PASS ARRAY OF RECIPES AND BUSINESSES BACK
		request.setAttribute("Recipes", RecipeFav);
		request.setAttribute("Businesses", BusinessFav);

		request.setAttribute("numRecipes", recipelength);
		request.setAttribute("numBusinesses", BusinessFav.length);
		// send it back
		
		RequestDispatcher dispatch = request.getRequestDispatcher("/Profile.jsp");
		try {
			dispatch.forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
}