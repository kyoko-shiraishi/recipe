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
	
	public Ingredient findOrCreate(String normalizedName,String rawName) {
		return ing_repository.findByName(normalizedName)
		.orElseGet(()->{
			//新規材料は仮材料としてあたらしく材料オブジェクトを作る
            Ingredient tempIng = new Ingredient();
            tempIng.setTempMate(true);
            tempIng.setName(rawName);
            return ing_repository.save(tempIng);
		});
			
           
		}
	}

