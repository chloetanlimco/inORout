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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
/**
 * Servlet implementation class Profile
 */
@WebServlet("/Profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Profile() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> RecipeIDs = new ArrayList<String>();
		ArrayList<String> BusinessIDs = new ArrayList<String>();
		try {
			conn = DriverManager.getConnection("jdbc:mysql://google/foodapp?cloudSqlInstance=groupproject-258805:us-central1:project201&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=anthonyuser&password=wQHL223i4LJhEuCl1");
			PreparedStatement st = conn.prepareStatement("SELECT userID from User WHERE username=?");
			st.setString(1, request.getSession().getAttribute("Current user").toString());
			int userID;
			ResultSet userObj = st.executeQuery();
			if (userObj.next()) {
				userID = userObj.getInt("userID");
				// SQL statement, grabs favorites in order of timestamp, so most recent is at the top
				// Businesses
				ps = conn.prepareStatement("SELECT * from Restaurant WHERE userID=? ORDER BY time DESC");
				ps.setInt(1, userID);
				rs = ps.executeQuery();
				// Add to BusinessIDs array
				while (rs.next()) {
					BusinessIDs.add(rs.getString("restaurantID"));
				}
				// SQL statement, grabs favorites in order of timestamp, so most recent is at the top
				// Recipes
				ps = conn.prepareStatement("SELECT * from Recipe WHERE userID=? ORDER BY time DESC");
				ps.setInt(1, userID);
				rs = ps.executeQuery();
				// Add to BusinessIDs array
				while (rs.next()) {
					RecipeIDs.add(rs.getString("recipeID"));
				}
			}
		}
		catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		String[] RecipeArray = RecipeIDs.toArray(new String[RecipeIDs.size()]);
		String[] BusinessArray = BusinessIDs.toArray(new String[BusinessIDs.size()]);
		// SUGGESTIONS
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
		Map<String, ArrayList<Business>> YObject = new TreeMap<String, ArrayList<Business>>();
		Map<String, ArrayList<Recipe>> EObject = new TreeMap<String, ArrayList<Recipe>>();
		ArrayList<Recipe> LikedRecipes = new ArrayList<Recipe>();
		ArrayList<Business> LikedBusinesses = new ArrayList<Business>();
		// RESTAURANTS
		for (int i = 0; i < (BusinessArray.length > 3 ? 3 : BusinessArray.length); i++) {
			ArrayList<Business> temp = new ArrayList<Business>();
			// grab their categories
			URL url = new URL("https://api.yelp.com/v3/businesses/" + BusinessArray[i]);
			HttpURLConnection yelpCon = (HttpURLConnection) url.openConnection();
			// add headers
			yelpCon.setRequestProperty("Authorization", "Bearer " + YelpBearerId);
			yelpCon.setRequestMethod("GET");
			// parsing JSON
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = (JsonObject)jsonParser.parse(
					new InputStreamReader(yelpCon.getInputStream(), "UTF-8"));
			LikedBusinesses.add(new Business(jsonObject));
			String category = jsonObject.getAsJsonArray("categories").get(0).getAsJsonObject().get("title").getAsString();
			String name = jsonObject.getAsJsonPrimitive("name").getAsString();
			// SEARCH HERE
			String params = "term=" + category.replace(" ", "+") + "&location=Los+Angeles";
			URL u = new URL("https://api.yelp.com/v3/businesses/search?" + params);
			HttpURLConnection yCon = (HttpURLConnection) u.openConnection();
			// add headers
			yCon.setRequestProperty("Authorization", "Bearer " + YelpBearerId);
			yCon.setRequestMethod("GET");
			// parsing JSON
			JsonParser jP = new JsonParser();
			JsonObject jO = (JsonObject)jP.parse(
					new InputStreamReader(yCon.getInputStream(), "UTF-8"));
			int total = jO.getAsJsonPrimitive("total").getAsInt();
			JsonArray businesses = jO.getAsJsonArray("businesses");
			// maximum 10 elements to return - can change this if you want
			for (int j = 0; j < ((total < 10) ? total : 20); j++) {
				// if not closed
				if (!businesses.get(j).getAsJsonObject().getAsJsonPrimitive("is_closed").getAsBoolean()) {
					Business b = new Business(businesses.get(j).getAsJsonObject());
					temp.add(b);
				}
			}
			YObject.put(name, temp);
		}
		// RECIPES
		for (int i = 0; i < (RecipeArray.length > 2 ? 2 : RecipeArray.length); i++) {
			ArrayList<Recipe> temp = new ArrayList<Recipe>();
			String encodedLink = URLEncoder.encode(RecipeArray[i], StandardCharsets.UTF_8.toString());
			URL url = new URL("https://api.edamam.com/search?r=" + encodedLink + "&app_key=" + app_key + "&app_id=" + app_id);
			HttpURLConnection edamamCon = (HttpURLConnection) url.openConnection();
			JsonParser jsonParser = new JsonParser();
			JsonArray jsonObject = (JsonArray) jsonParser.parse(
					new InputStreamReader(edamamCon.getInputStream(), "UTF-8"));
			// UNSURE IF THIS WORKS
			JsonObject obj = jsonObject.get(0).getAsJsonObject();
			LikedRecipes.add(new Recipe(obj));
			String name = obj.getAsJsonPrimitive("label").getAsString();
			String a = name.substring(0, name.indexOf(' ')); // get first word
			// SEARCH HERE
			String params = "q=" + a + "&app_key=" + app_key + "&app_id=" + app_id;
			URL u = new URL("https://api.edamam.com/search?" + params);
			HttpURLConnection eCon = (HttpURLConnection) url.openConnection();
			edamamCon.setRequestMethod("GET");
			// parsing JSON
			JsonParser jP = new JsonParser();
			JsonObject jO = (JsonObject)jP.parse(
					new InputStreamReader(eCon.getInputStream(), "UTF-8"));
			JsonArray recipes = jO.getAsJsonArray("hits");
			// figure out what to iterate up to
			for (int j = 0; j < 10; j++) {
				Recipe r = new Recipe(recipes.get(j).getAsJsonObject());
				temp.add(r);
			}
			EObject.put(name, temp);
		}
		// PASS MAP OF SUGGESTIONS BACK
		Gson gson = new Gson();
		Type gsonType = new TypeToken<HashMap>(){}.getType();
		String eGson = gson.toJson(EObject,gsonType);
		String yGson = gson.toJson(YObject,gsonType);
		request.setAttribute("RecipesSuggestions", eGson);
		request.setAttribute("BusinessSuggestions", yGson);
		Recipe[] RecipeFav = LikedRecipes.toArray(new Recipe[LikedRecipes.size()]);
		Business[] BusinessFav = LikedBusinesses.toArray(new Business[LikedBusinesses.size()]);
		// PASS ARRAY OF RECIPES AND BUSINESSES BACK
		request.setAttribute("Recipes", RecipeFav);
		request.setAttribute("Businesses", BusinessFav);
		for (int i = 0; i < BusinessFav.length; i++) {
			System.out.println(BusinessFav[i].getId());
		}
		request.setAttribute("numRecipes", RecipeFav.length);
		request.setAttribute("numBusinesses", BusinessFav.length);
		// send it back
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/Profile.jsp");
		try {
			dispatch.forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
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