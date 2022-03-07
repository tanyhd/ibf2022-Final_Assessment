package myapp.server.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import myapp.server.models.InventoryLineItem;
import myapp.server.models.User;
import myapp.server.repositories.UserRepository;
import myapp.server.services.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

@RestController
public class UserRestController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    MailService mailService;
    
    @PostMapping(path="/api/user/Signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> newUserSignUp(@RequestBody String payload) throws AddressException, MessagingException, IOException {
        
        User user = new User();
        boolean response = false; 
        try(InputStream is = new ByteArrayInputStream(payload.getBytes())) {

            JsonReader reader = Json.createReader(is);
            JsonObject result = reader.readObject();
            if(userRepo.checkUserEmail(result.getString("email"))) {
                user.setUsername(result.getString("username"));
                user.setName(result.getString("name"));
                user.setEmail(result.getString("email"));
                user.setPassword(result.getString("password"));
                response = userRepo.addUser(user);
            }

        } catch (Exception e) {}
        String success = "User added successfully";
        String failure = "failed to add user";
        if (response == true) {
            //added new user and send email
            //mailService.sendmail(user.getUsername(), user.getEmail());
        }
        return responseMessage(response, success, failure);
    }


    @PostMapping(path ="/api/user/login", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUser(@RequestBody String body) {
        User user = new User();
        JsonObject obj;
		try {
			JsonReader reader = Json.createReader(new ByteArrayInputStream(body.getBytes("UTF-8")));
			obj = reader.readObject();
            user = userRepo.getUser(obj.getString("email"), obj.getString("password"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
       
        if (user != null) {
            return ResponseEntity.ok(user.toJson().toString());
        } else {
            String success = "User information retrive";
            String failure = "User does not exist";
            return responseMessage(false, success, failure);
        }
    }

    @PostMapping(path="api/user/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateUser(@RequestBody String payload) {
        User user = new User();
        boolean response = false; 
        try(InputStream is = new ByteArrayInputStream(payload.getBytes())) {

            JsonReader reader = Json.createReader(is);
            JsonObject result = reader.readObject();
            user.setEmail(result.getString("email"));
            user.setRecipeList(result.getString("recipeListString"));
            System.out.println(">>>>>>>>>>>>>");
            List<InventoryLineItem> lineItemList = new LinkedList<>();
            JsonArray readings = result.getJsonArray("lineItem");

            for(int i = 0; i < readings.size(); i++) {
                InventoryLineItem lineItem = new InventoryLineItem();
                JsonObject obj = (JsonObject) readings.get(i);
                lineItem.setName(obj.getString("name"));
                try {
                    lineItem.setQuantity(obj.getInt("quantity"));
                } catch(Exception e) {
                    lineItem.setQuantity(Integer.parseInt(obj.getString("quantity")));
                }
                
                lineItemList.add(lineItem);
            }
            user.setLineItem(lineItemList);
            response = userRepo.updateUser(user);

        } catch (Exception e) {}

        String success = "Information updated successfully";
        String failure = "Unable to update information";
        return responseMessage(response, success, failure);
        
    }

    public ResponseEntity<String> responseMessage(boolean response, String success, String failure) {
        JsonObjectBuilder returnMessage = Json.createObjectBuilder();
        if(response == true) {
            returnMessage.add("message", success);
            return ResponseEntity
            .status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(returnMessage.build().toString());
        } else {
            returnMessage.add("message", failure);
            return ResponseEntity
            .status(HttpStatus.EXPECTATION_FAILED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(returnMessage.build().toString());
        }
    }
}
