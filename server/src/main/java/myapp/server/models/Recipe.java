package myapp.server.models;

import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonValue;

public class Recipe {
    String label;
    String imageUrl;
    int servings; //serving
    int totalTime;
    float calories;
    int caloriesPerServings;
    List<Ingredients> ingredients;

    
    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public Recipe() {
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public int getCaloriesPerServings() {
        return this.caloriesPerServings = (int) this.calories / this.servings; 
    }

    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
  
    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }


    public JsonValue toJson() {
        return Json.createObjectBuilder()
                .add("label", label)
                .add("imageUrl", imageUrl)
                .add("servings", servings)
                .add("totalTime", totalTime)
                .add("calories", calories)
                .add("caloriesPerServing", getCaloriesPerServings())
                .add("ingredients", ingredientsToJson())
                .build();
    }

    public JsonValue ingredientsToJson() {
        JsonArrayBuilder ingredientsBuilder = Json.createArrayBuilder();
        for(int i=0; i < this.ingredients.size(); i++) {
            ingredientsBuilder.add(this.ingredients.get(i).toJson());
        }
        return ingredientsBuilder.build();
    }
}
