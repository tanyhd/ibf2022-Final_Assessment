package myapp.server.models;

import jakarta.json.Json;
import jakarta.json.JsonValue;

public class Recipe {
    String label;
    String imageUrl;
    int servings; //serving
    int totalTime;
    float calories;
    int caloriesPerServings;

    
    public Recipe() {
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public int getCaloriesPerServings() {
        return caloriesPerServings;
    }

    public void setCaloriesPerServings(int caloriesPerServings) {
        this.caloriesPerServings = caloriesPerServings;
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
                .build();
    }
    
}
