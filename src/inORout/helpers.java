package inORout;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class helpers {
	public static boolean yelpCall(Search s, String latitude, String longitude, String searchTerm) throws Exception {
		try {
		String params = "term=" + searchTerm.replace(" ", "+") + "&latitude=" + latitude + "&longitude="
				+ longitude;
		System.out.println("1");


		URL url = new URL("https://api.yelp.com/v3/businesses/search?" + params);
		System.out.println("2");
		HttpURLConnection yelpCon = (HttpURLConnection) url.openConnection();
		// add headers
		yelpCon.setRequestProperty("Authorization", "Bearer " + s.YelpBearerId);
		yelpCon.setRequestMethod("GET");
		System.out.println("3");

		// parsing JSON
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject) jsonParser
				.parse(new InputStreamReader(yelpCon.getInputStream(), "UTF-8"));
		System.out.println("4");

		int total = jsonObject.getAsJsonPrimitive("total").getAsInt();
		JsonArray businesses = jsonObject.getAsJsonArray("businesses");
		System.out.println("5");

		// maximum 20 elements to return - can change this if you want
		for (int i = 0; i < ((total < 20) ? total : 20); i++) {
			// if not closed
			if (!businesses.get(i).getAsJsonObject().getAsJsonPrimitive("is_closed").getAsBoolean()) {
				Business b = new Business(businesses.get(i).getAsJsonObject());
				s.YelpResults.add(b);
			}
		}
		System.out.println("6");
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();

			throw new Exception();
		}
		
		return true;
	}
	
}
