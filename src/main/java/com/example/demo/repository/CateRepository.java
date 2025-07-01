package com.example.demo.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Recipe;
import com.example.demo.*;
import java.util.Optional;
public interface CateRepository extends JpaRepository<Category,Long>{
	public Optional<Category> findByname(String name);
}
