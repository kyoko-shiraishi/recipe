package com.example.demo.DTO;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngInfoDTO {
	private String name;
	private List<String> synonyms;
	private String category;
}
