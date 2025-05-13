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
	

	
	//レシピとタグは一対多なのでtagsテーブルにrecipe_id
	
	 //レシピと材料は一対多なのでingridientsテーブルにrecipe_id

	
	@Column
	private long comment; //レシピとコメントの記述は一対多なのでdescriptionsテーブルにrecipe_id
	
	@Column
	private int cookin_time; //レシピと料理時間は一対一なのでリレーションなし
	
	@Column
	private int servings; //レシピと人数は一対一なのでリレーションなし
	
	@Column
	private int user_id; //レシピと投稿ユーザーは多対一なのでrecipesテーブルがuser_idをもつ
	
	@Column
	private String main_image; //レシピとメイン画像は一対一なのでリレーションなし
}
