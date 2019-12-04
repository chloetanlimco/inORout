package inORout;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.http.HttpSession;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import inORout.Business;

public class ResultsYelpCall extends Thread {
	Search s;
	String searchTerm;
	String option;
	String price;
	String sortBy;

	ResultsYelpCall(Search search, String st) {
		s = search;
		searchTerm = st;
		option = null;
		price = null;
		sortBy = null;
		this.run();
	}

	ResultsYelpCall(Search search, String st, String option, String price, String sortBy) {
		s = search;
		searchTerm = st;
		this.option = option;
		if (option != null) {
			option.replace("dairy-free", "");
		}
		this.price = price;
		this.sortBy = sortBy;
		this.run();
	}

	public void run() {
		int sleeptime = 50;
		boolean yelpsuccess = false;
		HttpSession session = s.req.getSession();
		String latitude = "34.0205";
		String longitude = "-118.2856";
		if (session.getAttribute("latitude") != null && session.getAttribute("longitude") != null) {
			latitude = (String) session.getAttribute("latitude");
			longitude = (String) session.getAttribute("longitude");
		}

		String params = "term=" + searchTerm.replace(" ", "+") + "&latitude=" + latitude + "&longitude=" + longitude;
		if (price != null && !price.contentEquals("0")) {
			params += "&price=" + price;
		}
		if (option != null && !option.contentEquals("dairy-free")) {
			params += "&categories=" + option;
		}
		if (sortBy != null && !sortBy.contentEquals("none")) {
			params += "&sort_by=" + sortBy;
		}

		JsonObject jsonObject = null;

		while (!yelpsuccess) {
			try {
				URL url = new URL("https://api.yelp.com/v3/businesses/search?" + params);
				HttpURLConnection yelpCon = (HttpURLConnection) url.openConnection();
				// add headers
				yelpCon.setRequestProperty("Authorization", "Bearer " + s.YelpBearerId);
				yelpCon.setRequestMethod("GET");
				yelpCon.setConnectTimeout(100);


				// parsing JSON
				JsonParser jsonParser = new JsonParser();
				jsonObject = (JsonObject) jsonParser.parse(new InputStreamReader(yelpCon.getInputStream(), "UTF-8"));
				yelpsuccess = true;
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
				if (sleeptime == 1600) {
					break;
				}
			}
		}

		if (jsonObject != null) {
			int total = jsonObject.getAsJsonPrimitive("total").getAsInt();
			JsonArray businesses = jsonObject.getAsJsonArray("businesses");

			// maximum 20 elements to return - can change this if you want
			for (int i = 0; i < ((total-1 < 20) ? total-1 : 20); i++) {
				// if not closed
				if (!businesses.get(i).getAsJsonObject().getAsJsonPrimitive("is_closed").getAsBoolean()) {
					
					Business b = new Business(businesses.get(i).getAsJsonObject());
					s.YelpResults.add(b);
				}
			}
		}
	}
}
