package myapp.server.models;

import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonValue;

public class User {
    int userId;
    String username;
    String name;
    String email;
    String password;
    List<InventoryLineItem> lineItem = new LinkedList<>();
    String recipeList = "";

    public String getRecipeList() {
        return recipeList;
    }
    public void setRecipeList(String recipeList) {
        this.recipeList = recipeList;
    }
    public List<InventoryLineItem> getLineItem() {
        return lineItem;
    }
    public void setLineItem(List<InventoryLineItem> lineItem) {
        this.lineItem = lineItem;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    public User populate (SqlRowSet rs, List<InventoryLineItem> lineItem) {
        User user = new User();
        user.setUsername(rs.getString("username"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setLineItem(lineItem);
        return user;
    }

    public JsonValue toJson() {
        return Json.createObjectBuilder()
                .add("username", username)
                .add("name", name)
                .add("email", email)
                .add("lineItem", lineItemsToJson())
                .add("recipeListString", recipeList)
                .build();
    }

    public JsonValue lineItemsToJson() {
        JsonArrayBuilder lineItemsBuilder = Json.createArrayBuilder();
        for(int i = 0; i < this.lineItem.size(); i++) {
            lineItemsBuilder.add(this.lineItem.get(i).toJson());
        }
        return lineItemsBuilder.build();
    }
    
}
