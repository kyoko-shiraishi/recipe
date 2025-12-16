package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Entities.Ingredient;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient,Long>{
	public Optional<Ingredient> findByName(String name);
	
	@Query(value="""
			select * 
			from Ingredients
			where tempMate = true
			""",nativeQuery=true)
	List<Ingredient> findByTemp();
}
