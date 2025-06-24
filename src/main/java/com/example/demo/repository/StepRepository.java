package com.example.demo.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Step;
public interface StepRepository extends JpaRepository<Step,Long>{
public List<Step> findByRecipeId(Long recipe_id);
public void deleteByRecipeId(Long recipeId);
}
