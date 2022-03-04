package myapp.server.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
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

        JsonObjectBuilder returnMessage = Json.createObjectBuilder();
        if(response == true) {
            returnMessage.add("message", "User added successfully");
            //mailService.sendmail(user.getUsername(), user.getEmail());
            return ResponseEntity
            .status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(returnMessage.build().toString());
        } else {
            returnMessage.add("message", "failed to add user");
            return ResponseEntity
            .status(HttpStatus.EXPECTATION_FAILED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(returnMessage.build().toString());
        }
        
        
    
    }
}
