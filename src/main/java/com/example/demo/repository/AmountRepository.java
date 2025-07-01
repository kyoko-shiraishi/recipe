package com.example.demo.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Amount;


import java.util.Optional;

public interface AmountRepository extends JpaRepository<Amount,Long>{
	public List<Amount>findByRecipeId(Long id);
}
