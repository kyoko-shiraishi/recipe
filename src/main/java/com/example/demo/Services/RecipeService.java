package com.example.demo.Services;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Recipe;
import com.example.demo.repository.CookingRepository;
@Service
public class RecipeService {
private final CookingRepository repository;
public RecipeService(CookingRepository repository) {
	this.repository = repository;
}
public Optional<Recipe> findById(long id) {
	//リポジトリの既存メソッドを引数渡して呼び出す
	return repository.findById(id);
}
@Transactional
public void deleteById(long id) {
	repository.deleteById(id);
}
@Transactional
public void saveAndFlush(Recipe recipe) {
	repository.saveAndFlush(recipe);
}
@Transactional
public List<Recipe>  findAll() {
	return repository.findAll();
}
public List<Recipe> findByNameContaining(String keyword){
	return repository.findByNameContaining(keyword);
}
}
