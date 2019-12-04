package inORout;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ResultsEdamamCall extends Thread {
	Search s;
	String searchTerm;
	String option;


	ResultsEdamamCall(Search search, String st) {
		s = search;
		searchTerm = st;
		option = null;
		this.start();
	}
	
	ResultsEdamamCall(Search search, String st, String option) {
		s = search;
		searchTerm = st;
		this.option = option;
		this.run();
	}

	public void run() {
		int current = 0;
		JsonObject jsonObject = null;
		while (current != s.numkeys) {
			try {

				String params = "q=" + searchTerm.replace(" ", "+") + "&app_key=" + s.keys.get(current % s.numkeys)
						+ "&app_id=" + s.ids.get(current % s.numkeys);
				if (option != null) {
					params += "&healthLabels=" + option;
				}
				
				URL url = new URL("https://api.edamam.com/search?" + params);
				
				HttpURLConnection edamamCon = (HttpURLConnection) url.openConnection();
				edamamCon.setRequestMethod("GET");

				// parsing JSON
				JsonParser jsonParser = new JsonParser();
				jsonObject = (JsonObject) jsonParser
						.parse(new InputStreamReader(edamamCon.getInputStream(), "UTF-8"));

				break;
			} catch (Exception e) {
				e.printStackTrace();
				current = current + 1;
				System.out.println(e.getMessage());
				e.printStackTrace();
				System.out.println(current);
			}
		}
		if(jsonObject != null) {
			JsonArray recipes = jsonObject.getAsJsonArray("hits");
	
			// figure out what to iterate up to
			int it = 10;
			if (recipes.size() < 10) {
				it = recipes.size();
			}
			for (int i = 0; i < it; i++) {
				Recipe r = new Recipe(recipes.get(i).getAsJsonObject());
				s.EdamamResults.add(r);
			}
		}
	}
}
