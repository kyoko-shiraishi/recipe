package com.example.demo;

import jakarta.persistence.Column; //追記
import jakarta.persistence.Entity; //追記
import jakarta.persistence.GeneratedValue; //追記
import jakarta.persistence.GenerationType; //追記
import jakarta.persistence.Id; //追記
import jakarta.persistence.Table; //追記

@Entity
@Table(name="recipes")
public class Recipe {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private long id;
	
	@Column(length=50,nullable=false)
	private String name; //レシピと料理名は一対一なのでリレーションなし
	
	
	
	@Column
	private String comment; //レシピとコメントの記述は一対多なのでdescriptionsテーブルにrecipe_id
	
	@Column
	private int cooking_time; //レシピと料理時間は一対一なのでリレーションなし
	
	@Column
	private int servings; //レシピと人数は一対一なのでリレーションなし
	
	@Column
	private int user_id; //レシピと投稿ユーザーは多対一なのでrecipesテーブルがuser_idをもつ
	
	@Column
	private String main_image; //レシピとメイン画像は一対一なのでリレーションなし
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getCookingTime() {
		return cooking_time;
	}
	public void setCookingTime(int cooking_time) {
		this.cooking_time = cooking_time;
	}
	public int getServings() {
		return servings;
	}
	public void setServings(int servings) {
		this.servings = servings;
	}
	public int getUser() {
		return user_id;
	}
	public void setUser(int user_id) {
		this.user_id = user_id;
	}
	public String getMainImg() {
		return main_image;
	}
	public void setMainImg(String MainImg) {
		this.main_image = MainImg;
	}
}
