package com.example.recipe_appRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Recipe;

public interface CookingRepository extends JpaRepository<Recipe,Long>{

}
