package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Recipe;

import java.util.Optional;

public interface CookingRepository extends JpaRepository<Recipe,Long>{

}
