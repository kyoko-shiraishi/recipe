package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Mate;
import java.util.Optional;

public interface MateRepository extends JpaRepository<Mate,Long>{
	public Optional<Mate> findByName(String name);
}
