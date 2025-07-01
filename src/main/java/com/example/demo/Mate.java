package com.example.demo;
import jakarta.persistence.Column; 
import java.util.List;
import java.util.ArrayList;
import com.example.demo.*;
import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id; 
import jakarta.persistence.Table; 
import jakarta.persistence.ManyToOne; //追記
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;


@Entity
@Table(name="materials")
public class Mate {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Long id;
	
	@Column
	private String name;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@Column(name="is_temporary")
	private Boolean tempMate = false;
	
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
	public void setCategory(Category cate) {
		this.category = cate;
	}
	public boolean isTempMate() {
		return this.tempMate;
	}
	public void setTempMate(boolean tempMate) {
		this.tempMate = tempMate;
	}
}
