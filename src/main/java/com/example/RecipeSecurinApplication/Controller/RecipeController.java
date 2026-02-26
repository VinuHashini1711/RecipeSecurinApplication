package com.example.RecipeSecurinApplication.Controller;

import com.example.RecipeSecurinApplication.Dto.RecipeDTO;
import com.example.RecipeSecurinApplication.Service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RecipeController {
    @Autowired
    RecipeService recipeService;

    @PostMapping("/upload")
    public ResponseEntity<?> saveRecipes(@RequestParam("file") MultipartFile file){
        try{
            recipeService.saveRecipes(file);
            return ResponseEntity.status(HttpStatus.OK).body("JSON data uploaded successfully");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload files");
        }
    }
    @PostMapping("/recipes")
    public ResponseEntity<?> uploadRecipes(@RequestBody RecipeDTO recipe){
        if(recipe.getTitle()==null||recipe.getCuisine()==null||recipe.getPrepTime()==null||recipe.getCookTime()==null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Required Fields are missing");
        }
        return new ResponseEntity<>(recipeService.uploadRecipe(recipe),HttpStatus.OK);
    }
    @GetMapping("/recipes")
    public ResponseEntity<?> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }
    @GetMapping("/recipes/top")
    public ResponseEntity<?> getTopRecipes(
            @RequestParam(defaultValue = "5") int limit) {

        if (limit <= 0) {
            return ResponseEntity
                    .badRequest()
                    .body("Limit must be greater than 0");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("data", recipeService.getTopRecipes(limit));

        return ResponseEntity.ok(response);
    }

}
