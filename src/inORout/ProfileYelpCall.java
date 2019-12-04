package inORout;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ProfileYelpCall extends Thread {

	ProfileHelper p;
	String latitude;
	String longitude;
	int num;


	ProfileYelpCall(ProfileHelper profile, String lat, String lon, int n) {
		p = profile;
		latitude = lat;
		longitude = lon;
		num = n;
		this.start();
	}

	public void run() {
		String category = null;
		String name = null;
		// grab their categories
		boolean success = false;
		int sleeptime = 125;
		while (!success) {
			try {
				URL url = new URL("https://api.yelp.com/v3/businesses/" + p.BusinessIDs.get(num));
				HttpURLConnection yelpCon = (HttpURLConnection) url.openConnection();
				// add headers
				yelpCon.setRequestProperty("Authorization", "Bearer " + p.YelpBearerId);
				yelpCon.setRequestMethod("GET");
				// parsing JSON
				JsonParser jsonParser = new JsonParser();
				yelpCon.setConnectTimeout(1000);
				JsonObject jsonObject = (JsonObject) jsonParser
						.parse(new InputStreamReader(yelpCon.getInputStream(), "UTF-8"));
				
				p.LikedBusinesses.set(num, new Business(jsonObject));

				category = jsonObject.getAsJsonArray("categories").get(0).getAsJsonObject().get("title").getAsString();
				name = jsonObject.getAsJsonPrimitive("name").getAsString();
				
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
					p.businesslength = 0;
					break;
				}
			}
		}

		int total = 0;
		JsonArray businesses = null;
		sleeptime = 125;
		if (success) {
			success = false;
			while (!success) {
				try {
					String params = "term=" + category.replace(" ", "+") + "&latitude=" + latitude + "&longitude=" + longitude;
					URL u = new URL("https://api.yelp.com/v3/businesses/search?" + params);
					HttpURLConnection yCon = (HttpURLConnection) u.openConnection();
					// add headers
					yCon.setRequestProperty("Authorization", "Bearer " + p.YelpBearerId);
					yCon.setRequestMethod("GET");
					yCon.setConnectTimeout(1000);
					// parsing JSON
					JsonParser jP = new JsonParser();
					JsonObject jO = (JsonObject) jP.parse(new InputStreamReader(yCon.getInputStream(), "UTF-8"));
					total = jO.getAsJsonPrimitive("total").getAsInt();
					businesses = jO.getAsJsonArray("businesses");
					
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
						p.businesslength = 0;
						break;
					}
				}
			}
		}

		// SEARCH HERE

		// maximum 10 elements to return - can change this if you want
		Vector<Business> temp = new Vector<Business>();
		if (total != 0) {
			for (int j = 0; j < ((total < 10) ? total : 20); j++) {
				// if not closed
				if (!businesses.get(j).getAsJsonObject().getAsJsonPrimitive("is_closed").getAsBoolean()) {
					Business b = new Business(businesses.get(j).getAsJsonObject());
					temp.add(b);
				}
			}
		}
		p.YObject.put(name, temp);
		System.out.println("Profile thread finished");
		p.restaurantlatch.countDown();
	}
}
