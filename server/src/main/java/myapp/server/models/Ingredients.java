package myapp.server.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

public class Ingredients {
    String text = "";
    float quantity = 0;
    String measure = "";
    String foodId = "";
    String imageUrl = "";
    String food = "";

    public Ingredients() {
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public JsonValue toJson() {
        return Json.createObjectBuilder()
                .add("quantity", quantity)
                .add("measure", measure)
                .add("foodId", foodId)
                .add("imageUrl", imageUrl)
                .add("food", food)
                .build();
    }

    public Ingredients populate(JsonObject jsonObject) {
        Ingredients ingredients = new Ingredients();
        ingredients.setQuantity(jsonObject.getInt("quantity"));
        ingredients.setFoodId(jsonObject.getString("foodId"));
        ingredients.setFood(jsonObject.getString("food"));
        ingredients.setText(jsonObject.getString("text"));
        
        if(jsonObject.isNull("measure")) {
            ingredients.setMeasure("null");
        } else {
            ingredients.setMeasure(this.measure = jsonObject.getString("measure"));
        }

        if(jsonObject.isNull("image")) {
            ingredients.setImageUrl("null");
        } else {
            ingredients.setImageUrl(jsonObject.getString("image"));
        }
        return ingredients;
    }

}
