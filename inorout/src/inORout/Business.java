package inORout;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class Business {
	
	public String id;
	public String name;
	public String image_url;
	public String display_address;
	public String price;
	public double rating;
	public String display_phone;
	
	public Business(JsonObject obj) {
		// parse
		id = obj.getAsJsonPrimitive("id").getAsString();
		name = obj.getAsJsonPrimitive("name").getAsString();
		image_url = obj.getAsJsonPrimitive("image_url").getAsString();
		display_address = "";
		JsonArray addresses = obj.getAsJsonObject("location").getAsJsonArray("display_address"); 
		for (int i = 0; i < addresses.size(); i++) {
			display_address += addresses.get(i).getAsString() + ((i == addresses.size()-1) ? "" : "\n");
		}
		try {
			price = obj.getAsJsonPrimitive("price").getAsString();
		} catch (NullPointerException npe) {
			price = "Price unavailable.";
		}
		rating = obj.getAsJsonPrimitive("rating").getAsDouble();
		display_phone = obj.getAsJsonPrimitive("phone").getAsString();
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getImageUrl() {
		return image_url;
	}
	
	public String getDisplayAddress() {
		return display_address;
	}
	
	public String getPrice() {
		return price;
	}
	
	public double getRating() {
		return rating;
	}
	
	public String getDisplayPhone() {
		return display_phone;
	}
}