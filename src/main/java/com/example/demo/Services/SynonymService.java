package com.example.demo.Services;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.Entities.Synonym;
import com.example.demo.repository.*;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import com.example.demo.DTO.IngInfoDTO;
import com.example.demo.Entities.Ingredient;
@Service
public class SynonymService {
	private final SynonymRepository synonymRepository;
	private final IngredientRepository ing_repository;
	
	public SynonymService(SynonymRepository synonymRepository,IngredientRepository ing_repo) {
		this.synonymRepository = synonymRepository;
		this.ing_repository = ing_repo;
	}
	
	//シノニム⇒正規名変換メソッド
	@Transactional(readOnly = true)
	public String normalize(String keyword) {
		//見つかれば正規名（canonical）に変換して返す
		//見つからなければ入力のまま返す
		Optional<String> name = synonymRepository.findRightIngredientNameByKeyword(keyword);
		return name.orElse(keyword);
		
	}
	@Transactional(readOnly= true)
	public List<Ingredient> all_ingredients(){
		List<Ingredient> ing_list = ing_repository.findAll();
		return ing_list;
	}
	
	//Ingredient/SynonymテーブルのIngInfoDTOへの詰めなおし
	@Transactional
	public List<IngInfoDTO> convertToDTOs(List<Ingredient> ings){
		return ings.stream()
				.map(ing->{
					IngInfoDTO dto = new IngInfoDTO();
					dto.setName(ing.getName());
					dto.setCategory(ing.getCategory().getName());
					//ingredientのidをingredient_idとしてもつシノニムrecordをさがす
					List<String> synonyms = synonymRepository.findByIngredientId(ing.getId());
					dto.setSynonyms(synonyms);
					return dto;
					})
				.toList();
				
	}
	
}
