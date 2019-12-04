package inORout;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ProfileEdamamCall extends Thread {
	int num;
	ProfileHelper p;

	ProfileEdamamCall(ProfileHelper profile, int n) {
		num = n;
		p = profile;
		this.start();
	}

	public void run() {
		int current = 0;
		String a = null;
		HttpURLConnection edamamCon = null;
		Vector<Recipe> temp = new Vector<Recipe>();
		String name = null;
		while (current != p.numkeys) {
			try {
				String encodedLink = URLEncoder.encode(p.RecipeIDs.get(num), StandardCharsets.UTF_8.toString());
				URL url = new URL(
						"https://api.edamam.com/search?r=" + encodedLink + "&app_key=" + p.keys.get(current % p.numkeys) + "&app_id=" +p.ids.get(current % p.numkeys));
				edamamCon = (HttpURLConnection) url.openConnection();
				edamamCon.setConnectTimeout(100);
				JsonParser jsonParser = new JsonParser();
				JsonArray jsonObject = (JsonArray) jsonParser
						.parse(new InputStreamReader(edamamCon.getInputStream(), "UTF-8"));
				// UNSURE IF THIS WORKS
				JsonObject obj = jsonObject.get(0).getAsJsonObject();
	
				p.LikedRecipes.set(num, new Recipe(obj, "trash"));
				name = obj.getAsJsonPrimitive("label").getAsString();
				a = name.substring(0, name.indexOf(' ')); // get first word
				break;
			} catch (Exception e) {
				System.out.println("exception reached:" + e.getMessage());
				e.printStackTrace();
				current = current + 1;
				System.out.println(e.getMessage());
				System.out.println(current);
			}
		}
		
		
				
		if(name == null) {
			p.recipelatch.countDown();
			p.recipelength = 0;
			return;
		}
		
		while (current != p.numkeys) {
			try {		
	
				// SEARCH HERE
				String params = "q=" + a + "&app_key=" + p.keys.get(current % p.numkeys) + "&app_id=" +p.ids.get(current % p.numkeys);
				URL u = new URL("https://api.edamam.com/search?" + params);
				HttpURLConnection eCon = (HttpURLConnection) u.openConnection();
				eCon.setRequestMethod("GET");
				eCon.setConnectTimeout(200);
				// parsing JSON
	
				JsonParser jP = new JsonParser();
				JsonObject jO = (JsonObject) jP
				.parse(new InputStreamReader(eCon.getInputStream(), "UTF-8"));
				
//				JsonElement jE = jP.parse(new InputStreamReader(eCon.getInputStream(), "UTF-8"));
				JsonArray recipes = jO.getAsJsonArray("hits");
				//System.out.println(jE);

				//recipes = (JsonArray) jE.getAsJsonArray("hits");
				// figure out what to iterate up to
				for (int j = 0; j < ((10 < recipes.size()) ? 10 : recipes.size()); j++) {
					Recipe r = new Recipe(recipes.get(j).getAsJsonObject());
					temp.add(r);
				}
				p.EObject.put(name, temp);
				break;
			} catch (Exception e) {
				System.out.println("exception reached:" + e.getMessage());
				e.printStackTrace();
				current = current + 1;
				System.out.println(e.getMessage());
				System.out.println(current);
			}
		}
	
		if(current == p.numkeys) {
			p.recipelength = 0;
		}
		p.recipelatch.countDown();
	}
}
