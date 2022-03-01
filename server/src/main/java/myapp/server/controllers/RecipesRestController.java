package myapp.server.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import myapp.server.models.Recipe;
import myapp.server.services.RecipesService;

@RestController
public class RecipesRestController {
    
    @Autowired
    RecipesService recipesService;

    @GetMapping(path="/api/recipes/{food}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllRecipes(@PathVariable String food) {
        List<Recipe> recipes = recipesService.getRecipes(food);
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        recipes.stream().forEach(v -> arrBuilder.add(v.toJson()));
        return ResponseEntity.ok(arrBuilder.build().toString());
    }
}
