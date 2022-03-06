package myapp.server.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonValue;

public class InventoryLineItem {
    
    String name;
    int quantity;
   
    public InventoryLineItem() {
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public InventoryLineItem populate (SqlRowSet rs) {
        InventoryLineItem inventoryLineItem = new InventoryLineItem();
        inventoryLineItem.setName(rs.getString("name"));
        inventoryLineItem.setQuantity(rs.getInt("quantity"));
        return inventoryLineItem;
    }

    public JsonValue toJson() {
        return Json.createObjectBuilder()
                .add("name", name)
                .add("quantity", quantity)
                .build();
    }
}
