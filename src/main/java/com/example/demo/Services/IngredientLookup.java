package com.example.demo.Services;
import com.example.demo.Entities.*;
import com.example.demo.DTO.*;
import java.util.Optional;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

@Service
public class IngredientLookup {
	private final IngredientRepository ing_repository;
	public IngredientLookup(IngredientRepository ing_repository){
		this.ing_repository= ing_repository;
	}
	
	public Ingredient findOrCreateIng(NormalizeResult res) {
		
		if(res.matched()) {
			return  ing_repository.findByName(res.canonicalName()).orElseThrow();
		}else {
			return ing_repository.findByName(res.rawName()).orElseGet(()->{
				Ingredient ing = new Ingredient();
				ing.setName(res.rawName());
				ing.setTempMate(true);
				return ing_repository.save(ing);
			});
		}
	}
}

