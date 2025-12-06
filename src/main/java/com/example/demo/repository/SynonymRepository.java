package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo.Entities.Ingredient;
import com.example.demo.Entities.Synonym;
import java.util.Optional;
import java.util.List;
public interface SynonymRepository extends JpaRepository<Synonym,Long>{
	//Optional<Synonym> findByKeyword(String keyword);
	@Query(value="""
			SELECT i.name 
			FROM ingredients i 
			JOIN synonyms s ON s.ingredient_id=i.id 
			WHERE s.name = :keyword 
			"""
			,nativeQuery=true)
	Optional<String> findRightIngredientNameByKeyword(@Param("keyword") String keyword);
	
	@Query(value="""
			SELECT s.name
			FROM synonyms s
			WHERE s.ingredient_id = :id
			"""
			,nativeQuery=true)
	List<String> findByIngredientId(@Param("id") Long id);//Ingredientのidからシノニム検索

			
}
