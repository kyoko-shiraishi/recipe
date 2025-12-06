package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Entities.Ingredient;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient,Long>{
	public Optional<Ingredient> findByName(String name);
}
