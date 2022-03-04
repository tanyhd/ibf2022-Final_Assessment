package myapp.server.services;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import myapp.server.models.Ingredients;
import myapp.server.models.Recipe;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RecipesService {

    public List<Recipe> getRecipes(String food) {
        String url = UriComponentsBuilder
                    .fromUriString("https://api.edamam.com/api/recipes/v2")
                    .queryParam("type", "public")
                    .queryParam("q", food)
                    .queryParam("app_id", System.getenv("edamam_app_id"))
                    .queryParam("app_key", System.getenv("edaman_app_key"))
                    .queryParam("imageSize", "REGULAR")
                    .toUriString();
        RequestEntity<Void> req = RequestEntity.get(url).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);

        if(resp.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("Error: status code %d".formatted(resp.getStatusCode().toString()));
        }

        String body = resp.getBody();
        List<Recipe> recipes = new LinkedList<>();
  
        try(InputStream is = new ByteArrayInputStream(body.getBytes())) {

            JsonReader reader = Json.createReader(is);
            JsonObject result = reader.readObject();
            JsonArray resultArray = result.getJsonArray("hits");
            System.out.println(resultArray.size());

            for(int i = 0; i < resultArray.size(); i++) {
                JsonObject recipeResult = resultArray.getJsonObject(i).getJsonObject("recipe");
                JsonArray ingredientsListArray = recipeResult.getJsonArray("ingredients");
    
                Recipe recipe = new Recipe();
                recipe.setLabel(recipeResult.getString("label"));
                recipe.setImageUrl(recipeResult.getString("image"));
                recipe.setServings(recipeResult.getInt("yield"));
                recipe.setTotalTime(recipeResult.getInt("totalTime"));
                recipe.setCalories(recipeResult.getInt("calories"));

                List<Ingredients> ingredientsList = new LinkedList<>();
                for(int j = 0; j < ingredientsListArray.size(); j++) {
                    Ingredients ingredients = new Ingredients();
                    ingredientsList.add(ingredients.populate(ingredientsListArray.getJsonObject(j)));
                }
                recipe.setIngredients(ingredientsList);
                recipes.add(recipe);
                //resultArray.getJsonObject(i).getJsonObject("recipe").getString("label")
            }

        } catch (Exception e) {}
        
        return recipes;
    }

    public String getFoodLabel(ResponseEntity<String> response) {
        String foodLabel = "";
        float percent = 0;

        try(InputStream is = new ByteArrayInputStream(response.getBody().getBytes())) {

            JsonReader reader = Json.createReader(is);
            JsonObject result = reader.readObject();
            JsonArray resultArray = result.getJsonArray("food_results");
            foodLabel = resultArray.getJsonArray(0).get(0).toString();
            percent = Float.parseFloat(resultArray.getJsonArray(1).get(0).toString());
        
        } catch (Exception e) {}
        return foodLabel;

    }


    
}
