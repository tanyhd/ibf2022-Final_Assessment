package myapp.server.repositories;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import myapp.server.models.InventoryLineItem;
import myapp.server.models.User;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate template;

    public static final String SQL_ADD_USER = "insert into user_profile (username, name, email, password) values (?, ?, ?, sha1(?))";
    public static final String SQL_CHECK_USER_EMAIL = "select count(email) as count from user_profile where email=?";
    public static final String SQL_CHECK_USER = "select count(email) as count, user_id as id from user_profile where email = ? and password = sha1(?)";
    public static final String SQL_GET_USER_INVENTORY_LIST = "select * from inventory_line_item where user_id = ?";
    public static final String SQL_GET_USER = "select * from user_profile where user_id = ?";
    public static final String SQL_DELETE_USER_INVENTORY_LIST = "delete from inventory_line_item where user_id = ?";
    public static final String SQL_GET_USER_ID_FROM_EMAIL = "select user_id as id from user_profile where email = ?";
    public static final String SQL_ADD_INVENTORY_LINE_ITEM = "INSERT INTO inventory_line_item (name, quantity, user_id) VALUES(?, ?, ?)";
    public static final String SQL_ADD_RECIPELIST = "insert into recipe_list (recipeListString, user_id) VALUES(?, ?)";
    public static final String SQL_DELETE_USER_RECIPELIST = "delete from recipe_list where user_id = ?";
    public static final String SQL_GET_USER_RECIPELIST = "select recipeListString from recipe_list where user_id = ?";  

    public boolean addUser(User user) {
        int added = template.update(SQL_ADD_USER, user.getUsername(), user.getName(), user.getEmail(), user.getPassword());
        return added > 0;
    }

    public boolean checkUserEmail(String userEmail) {
        SqlRowSet rs = template.queryForRowSet(SQL_CHECK_USER_EMAIL, userEmail);
        int count = 0;
        while(rs.next()) {
            count = rs.getInt("count");
        }
        if(count == 0) {
            return true;
        } else {
            return false;
        } 
    }

    public User getUser(String email, String password) {
        SqlRowSet rs = template.queryForRowSet(SQL_CHECK_USER, email, password);
        int count = 0;
        int id = 0;
        List<InventoryLineItem> inventoryLineItemList = new LinkedList<>();
        User user = new User();
        while(rs.next()) {
            count = rs.getInt("count");
            id = rs.getInt("id");
        }
        if(count == 0) {
            return null;
        } else {
            SqlRowSet rsInventoryList = template.queryForRowSet(SQL_GET_USER_INVENTORY_LIST, id);
            while(rsInventoryList.next()) {
                InventoryLineItem lineItem = new InventoryLineItem();
                inventoryLineItemList.add(lineItem.populate(rsInventoryList));
            }

            SqlRowSet rsUser = template.queryForRowSet(SQL_GET_USER, id);
            while(rsUser.next()) {
                user = user.populate(rsUser, inventoryLineItemList);
            }

            SqlRowSet rsRecipeList = template.queryForRowSet(SQL_GET_USER_RECIPELIST, id);
            while(rsRecipeList.next()) {
                user.setRecipeList(rsRecipeList.getString("recipeListString"));
            }
            return user;
        } 
    }

    int id = 0;

    public boolean updateUser(User user) {
        SqlRowSet rsUser = template.queryForRowSet(SQL_GET_USER_ID_FROM_EMAIL, user.getEmail());
        while(rsUser.next()) {
            id = rsUser.getInt("id");
            System.out.println(id);
        }
        template.update(SQL_DELETE_USER_INVENTORY_LIST, id);
        List<Object[]> lineItemArray = user.getLineItem().stream()
                                        .map(lineitem -> new Object[] {lineitem.getName(), lineitem.getQuantity(), id})
                                        .collect(Collectors.toList());
        int addedArray[] = template.batchUpdate(
                        SQL_ADD_INVENTORY_LINE_ITEM, lineItemArray);
        
        template.update(SQL_DELETE_USER_RECIPELIST, id);
        int add = template.update(SQL_ADD_RECIPELIST, user.getRecipeList() , id);
        return add + addedArray.length > 0;
    }
} 
