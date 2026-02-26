package com.example.RecipeSecurinApplication.Repository;

import com.example.RecipeSecurinApplication.Entity.Recipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface RecipeRepository extends JpaRepository<Recipe,Integer> {
    List<Recipe> findAllByOrderByRatingDesc(Pageable pageable);
}
