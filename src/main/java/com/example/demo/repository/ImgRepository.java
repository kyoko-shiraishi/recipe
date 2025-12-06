package com.example.demo.repository;
import com.example.demo.Entities.Img;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ImgRepository extends JpaRepository<Img,Long>{
	public Optional<Img> findByPath(String path);
	public Img getByPath(String path);
}
