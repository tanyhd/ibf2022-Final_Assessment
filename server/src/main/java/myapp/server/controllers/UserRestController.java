package myapp.server.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
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

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

@RestController
public class UserRestController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    MailService mailService;
    
    @PostMapping(path="api/user/Signup", consumes = MediaType.APPLICATION_JSON_VALUE)
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


    @GetMapping(path ="api/user/login/{email}/{password}")
    public ResponseEntity<String> getUser(@PathVariable String email, @PathVariable String password) {
        User user = new User();
        user = userRepo.getUser(email, password);
        if (user != null) {
            return ResponseEntity.ok(user.toJson().toString());
        } else {
            String success = "User information retrive";
            String failure = "User does not exist";
            return responseMessage(false, success, failure);
        }
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
