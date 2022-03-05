package myapp.server.models;

public class RecipeRequirement {
    String diet;
    String mealType;
    String calories;

    public RecipeRequirement() {
    }

    public RecipeRequirement(String diet, String mealType, String calories) {
        this.diet = diet;
        this.mealType = mealType;
        this.calories = calories;
    }

    public String getDiet() {
        return diet;
    }
    public void setDiet(String diet) {
        this.diet = diet;
    }
    
    public String getMealType() {
        return mealType;
    }
    public void setMealType(String mealType) {
        this.mealType = mealType;
    }
    public String getCalories() {
        return calories;
    }
    public void setCalories(String calories) {
        this.calories = calories;
    }

    
}
