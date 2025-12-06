package com.example.demo.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Entities.Recipe;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe,Long>{
	
public List<Recipe> findByNameContaining(String name);
//Containing … 「部分一致（LIKE検索）」を意味するキーワード
}
