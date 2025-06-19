package com.example.demo;

import jakarta.persistence.Column; 

import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id; 
import jakarta.persistence.Table; 
import jakarta.persistence.ManyToOne; //追記
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;


@Entity
@Table(name="recipes")
public class Recipe {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column                         //Nullを禁止するバリデーション追記
	
	private long id;
	
	@Column(length=50,nullable=false)
	                  //未入力を禁止するバリデーション追記
	private String name; 

	@Column
	private String comment; //レシピとコメントの記述は一対多なのでdescriptionsテーブルにrecipe_id
	
	@OneToOne
	@JoinColumn(name = "main_img_id")
	private Img mainImg; //画像に関する情報
	
	
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
	public Img getMainImg() {
		return mainImg;
	}
	public void setMainImg(Img mainImg) {
		this.mainImg = mainImg;
	}
	
}
