package com.example.RecipeSecurinApplication.Service;

import com.example.RecipeSecurinApplication.Dto.RecipeDTO;
import com.example.RecipeSecurinApplication.Entity.Recipe;
import com.example.RecipeSecurinApplication.Repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final int BATCH_SIZE = 500;

    public void saveRecipes(MultipartFile file) throws IOException {

        Map<String, RecipeDTO> value =
                objectMapper.readValue(file.getInputStream(),
                        new TypeReference<Map<String, RecipeDTO>>() {});

        List<Recipe> insert = new ArrayList<>();

        for (RecipeDTO data : value.values()) {

            Recipe recipe = new Recipe();
            recipe.setTitle(data.getTitle());
            recipe.setCuisine(data.getCuisine());
            if (data.getRating() == null) {
                recipe.setRating(3.0);
            } else {
                recipe.setRating(data.getRating());
            }
//            recipe.setRating(data.getRating());
            recipe.setPrepTime(data.getPrepTime());
            recipe.setCookTime(data.getCookTime());
            if (data.getPrepTime() != null && data.getCookTime() != null) {
                recipe.setTotalTime(data.getPrepTime() + data.getCookTime());
            }
            recipe.setDescription(data.getDescription());
            recipe.setNutrients(objectMapper.writeValueAsString(data.getNutrients()));
            recipe.setServes(data.getServes());
            insert.add(recipe);
            if (insert.size() == BATCH_SIZE) {
                recipeRepository.saveAll(insert);
                insert.clear();
            }
        }
        if (!insert.isEmpty()) {
            recipeRepository.saveAll(insert);
        }
    }
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }
    public Recipe uploadRecipe(RecipeDTO data) {
        data.setTotalTime(data.getCookTime() + data.getPrepTime());
        Recipe recipe = new Recipe();
        recipe.setTitle(data.getTitle());
        recipe.setCuisine(data.getCuisine());
        recipe.setRating(data.getRating());
        recipe.setPrepTime(data.getPrepTime());
        recipe.setCookTime(data.getCookTime());
        if (data.getPrepTime() != null && data.getCookTime() != null) {
            recipe.setTotalTime(data.getPrepTime() + data.getCookTime());
        }
        recipe.setDescription(data.getDescription());
        recipe.setNutrients(objectMapper.writeValueAsString(data.getNutrients()));
        recipe.setServes(data.getServes());
        return recipeRepository.save(recipe);
    }
    public List<RecipeDTO> getTopRecipes(int limit) {

        List<Recipe> recipes =
                recipeRepository.findAllByOrderByRatingDesc(PageRequest.of(0, limit));
        List<RecipeDTO> result = new ArrayList<>();
        for (Recipe recipe : recipes) {
            RecipeDTO dto = new RecipeDTO();
            dto.setId(recipe.getId());
            dto.setTitle(recipe.getTitle());
            dto.setCuisine(recipe.getCuisine());
            dto.setRating(recipe.getRating());
            dto.setPrepTime(recipe.getPrepTime());
            dto.setCookTime(recipe.getCookTime());
            dto.setTotalTime(recipe.getTotalTime());
            dto.setDescription(recipe.getDescription());
            dto.setServes(recipe.getServes());
            try {
                dto.setNutrients(
                        objectMapper.readValue(
                                recipe.getNutrients(),
                                new TypeReference<Map<String, String>>() {}
                        )
                );
            } catch (Exception e) {
                dto.setNutrients(null);
            }
            result.add(dto);
        }

        return result;
    }
}