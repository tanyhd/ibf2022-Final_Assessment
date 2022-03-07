package myapp.server.controllers;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import myapp.server.models.Recipe;
import myapp.server.models.RecipeRequirement;
import myapp.server.services.RecipesService;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@RestController
public class RecipesRestController {
    
    @Autowired
    RecipesService recipesService;

    @GetMapping(path="/api/recipes/{food}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllRecipes(@PathVariable String food) {
        food = food.trim().replaceAll(" ", "+");
        List<Recipe> recipes = recipesService.getRecipes(food);
        if(recipes.size() == 0) {
            JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
            recipes.stream().forEach(v -> arrBuilder.add(v.toJson()));
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(arrBuilder.build().toString());
        } else {
            JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
            recipes.stream().forEach(v -> arrBuilder.add(v.toJson()));
            return ResponseEntity.ok(arrBuilder.build().toString());
        }
    }

    @GetMapping(path="/api/recipes/meal/{mealRequirement}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRecipeBaseOnRequirement(@PathVariable String mealRequirement) {
        RecipeRequirement recipeRequirment = new RecipeRequirement();
        String requirment[] = mealRequirement.split(" ");
        recipeRequirment.setDiet(requirment[0]);
        recipeRequirment.setMealType(requirment[1]);
        recipeRequirment.setCalories(requirment[2]);
        
        List<Recipe> recipes = recipesService.getRecipesBaseOnRequirement(recipeRequirment);
        if(recipes.size() == 0) {
            JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
            recipes.stream().forEach(v -> arrBuilder.add(v.toJson()));
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(arrBuilder.build().toString());
        } else {
            JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
            recipes.stream().forEach(v -> arrBuilder.add(v.toJson()));
            return ResponseEntity.ok(arrBuilder.build().toString());
        }
    }

    //To get food label from supplied image
    @PostMapping(path="/api/getFoodLabel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> getImageLabel(@RequestParam(name="image") MultipartFile image) throws IOException {

        byte[] buff = new byte[0];
        buff = image.getBytes();
        String s = Base64.getEncoder().encodeToString(buff);
        String base64ImageTest = "data:image/jpeg;base64," + s;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("image_url", base64ImageTest);
        map.add("num_tag", "1");
        map.add("api_key", System.getenv("FoodAI_api_key"));

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity("https://api.foodai.org/v4.1/classify", request , String.class);
        String foodLabel = recipesService.getFoodLabel(response);
        
        //search for receipe from given food label
        List<Recipe> recipes = recipesService.getRecipes(foodLabel);
        if(recipes.size() == 0) {
            JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
            recipes.stream().forEach(v -> arrBuilder.add(v.toJson()));
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(arrBuilder.build().toString());
        } else {
            JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
            recipes.stream().forEach(v -> arrBuilder.add(v.toJson()));
            return ResponseEntity.ok(arrBuilder.build().toString());
        }
    }

}
