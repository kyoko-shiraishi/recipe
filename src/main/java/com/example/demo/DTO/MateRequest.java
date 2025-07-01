package com.example.demo.DTO;

import com.example.demo.Category;

public class MateRequest {
private Long id;
private String name;
private Category category;
public Long getId() {
	return this.id;
}
public void setId(Long id) {
	this.id=id;
}
public String getName() {
	return this.name;
}
public void setName(String name) {
	this.name=name;
}
public Category getCategory() {
	return this.category;
}
public void setCategory(Category category) {
	this.category=category;
}
}
