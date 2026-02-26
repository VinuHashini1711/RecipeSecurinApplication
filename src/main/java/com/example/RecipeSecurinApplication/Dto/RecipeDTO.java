package com.example.RecipeSecurinApplication.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class RecipeDTO {
    private Integer id;
    private String title;
    private String cuisine;
    private Double rating;
    @JsonProperty("prep_time")
    private Integer prepTime;
    @JsonProperty("cook_time")
    private Integer cookTime;
    @JsonProperty("total_time")
    private Integer totalTime;
    private String description;
    private Map<String, String> nutrients;
    private String serves;
}



