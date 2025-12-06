package com.example.demo.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Entities.Recipe_Ingredient;
import java.util.Optional;

public interface Recipe_IngredietRepository extends JpaRepository<Recipe_Ingredient,Long>{
	public List<Recipe_Ingredient>findByRecipeId(Long id);
}
