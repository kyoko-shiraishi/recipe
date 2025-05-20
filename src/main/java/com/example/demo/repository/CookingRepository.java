package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Recipe;

public interface CookingRepository extends JpaRepository<Recipe,Long>{

}
