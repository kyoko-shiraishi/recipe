package com.example.demo;
import jakarta.persistence.Column; 
import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id; 
import jakarta.persistence.Table;

@Entity
@Table(name="images")

public class Img {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column                         
	private long id;
	@Column
	private String path;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path=path;
	}
}
