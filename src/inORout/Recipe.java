package inORout;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Recipe {
	
	private String label;
	private String image;
	private String source;
	private String[] healthLabels;
	private String[] cautions;
	private String[] ingredientLines;
	private double calories;
	
	public Recipe(JsonObject obj) {
		JsonObject recipe = obj.get("recipe").getAsJsonObject();
		label = recipe.getAsJsonPrimitive("label").getAsString();
		source = recipe.getAsJsonPrimitive("source").getAsString();
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
		
		calories = recipe.getAsJsonPrimitive("calories").getAsDouble();
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
	
}
