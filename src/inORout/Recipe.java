package inORout;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Recipe {
	
	public String label;
	public String image;
	public String source;
	public String uri;
	public int servings;
	public String[] healthLabels;
	public String[] cautions;
	public String[] ingredientLines;
	public double calories;
	public String url;
	
	public Recipe(JsonObject obj) {
		JsonObject recipe = obj.get("recipe").getAsJsonObject();
		label = recipe.getAsJsonPrimitive("label").getAsString();
		source = recipe.getAsJsonPrimitive("source").getAsString();
		image = recipe.getAsJsonPrimitive("image").getAsString();
		uri = recipe.getAsJsonPrimitive("uri").getAsString();
		servings = recipe.getAsJsonPrimitive("yield").getAsInt();
		url = recipe.getAsJsonPrimitive("url").getAsString();
		
		JsonArray health = recipe.getAsJsonArray("healthLabels");
		if (health.size() > 0) {
			healthLabels = new String[health.size()];
			for (int i = 0; i < health.size(); i++) {
				healthLabels[i] = health.get(i).getAsString();
			}
		} else {
			healthLabels = new String[1];
			healthLabels[0] = "No health labels.";
		}
		
		JsonArray caution = recipe.getAsJsonArray("cautions");
		if (caution.size() > 0) {
			cautions = new String[caution.size()];
			for (int i = 0; i < caution.size(); i++) {
				cautions[i] = caution.get(i).getAsString();
			}
		} else {
			cautions = new String[1];
			cautions[0] = "No cautions.";
		}
		
		JsonArray ingredients = recipe.getAsJsonArray("ingredientLines");
		if (ingredients.size() > 0) {
			ingredientLines = new String[ingredients.size()];
			for (int i = 0; i < ingredients.size(); i++) {
				ingredientLines[i] = ingredients.get(i).getAsString();
			}
		} else {
			ingredientLines = new String[1];
			ingredientLines[0] = "No ingredients.";
		}
		
		calories = recipe.getAsJsonPrimitive("calories").getAsDouble() / servings;
	}
	
	public Recipe(JsonObject recipe, String detail) {
		label = recipe.getAsJsonPrimitive("label").getAsString();
		source = recipe.getAsJsonPrimitive("source").getAsString();
		image = recipe.getAsJsonPrimitive("image").getAsString();
		uri = recipe.getAsJsonPrimitive("uri").getAsString();
		url = recipe.getAsJsonPrimitive("url").getAsString();
		servings = recipe.getAsJsonPrimitive("yield").getAsInt();
		JsonArray health = recipe.getAsJsonArray("healthLabels");
		if (health.size() > 0) {
			healthLabels = new String[health.size()];
			for (int i = 0; i < health.size(); i++) {
				healthLabels[i] = health.get(i).getAsString();
			}
		} else {
			healthLabels = new String[1];
			healthLabels[0] = "No health labels.";
		}
		
		JsonArray caution = recipe.getAsJsonArray("cautions");
		if (caution.size() > 0) {
			cautions = new String[caution.size()];
			for (int i = 0; i < caution.size(); i++) {
				cautions[i] = caution.get(i).getAsString();
			}
		} else {
			cautions = new String[1];
			cautions[0] = "No cautions.";
		}
		
		JsonArray ingredients = recipe.getAsJsonArray("ingredientLines");
		if (ingredients.size() > 0) {
			ingredientLines = new String[ingredients.size()];
			for (int i = 0; i < ingredients.size(); i++) {
				ingredientLines[i] = ingredients.get(i).getAsString();
			}
		} else {
			ingredientLines = new String[1];
			ingredientLines[0] = "No ingredients.";
		}
		
		calories = recipe.getAsJsonPrimitive("calories").getAsDouble() / (double)servings;
	}

	public String getLabel() {
		return label;
	}
	public String getImage() {
		return image;
	}
	public String getSource() {
		return source;
	}
	public String getUri() {
		return uri;
	}
	public int getServings() {
		return servings;
	}
	public String[] getHealthLabels() {
		return healthLabels;
	}
	public String[] getCautions() {
		return cautions;
	}
	public String[] getIngredientLines() {
		return ingredientLines;
	}
	public double getCalories() {
		return calories;
	}
	public String getUrl() {
		return url;
	}
	
}