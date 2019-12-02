package inORout;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class Business {
	
	public String id;
	public String name;
	public String categories;
	public String image_url;
	public String display_address;
	public String price;
	public double rating;
	public double distance = -1;
	public int review_count;
	public String display_phone;
	
	public Business(JsonObject obj) {
		// parse
		id = obj.getAsJsonPrimitive("id").getAsString();
		name = obj.getAsJsonPrimitive("name").getAsString();
		JsonArray categorylist = obj.getAsJsonArray("categories");
		categories = "";
		for (int i = 0; i < categorylist.size(); i++) {
			JsonObject category = categorylist.get(i).getAsJsonObject();
			categories += category.getAsJsonPrimitive("title").getAsString() + ((i == categorylist.size()-1) ? "" : ", ");
		}
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
		if (obj.getAsJsonPrimitive("distance") != null) {
			distance = obj.getAsJsonPrimitive("distance").getAsDouble();
		}
		review_count = obj.getAsJsonPrimitive("review_count").getAsInt();
		display_phone = obj.getAsJsonPrimitive("phone").getAsString();
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getCategories() {
		return categories;
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
	
	public double getDistance() {
		return distance;
	}
	
	public int getReviewCount() {
		return review_count;
	}
	
	public String getDisplayPhone() {
		return display_phone;
	}
}