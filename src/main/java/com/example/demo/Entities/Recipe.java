package com.example.demo.Entities;

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
@Table(name="recipes")
public class Recipe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column                         
	private Long id;
	
	@Column(length=50,nullable=false)
	                  //未入力を禁止するバリデーション追記
	private String name; 

	@Column
	private String comment; //レシピとコメントの記述は一対多なのでdescriptionsテーブルにrecipe_id
	
	@OneToOne
	@JoinColumn(name = "main_img_id",referencedColumnName = "id")//@JoinColumn はデフォルトで相手エンティティの主キーを参照
	//recipes テーブルに main_img_id というカラムを作りそこに Img（images テーブル）の主キー id を入れる指定
	private Img mainImg; 
	
	//「このエンティティ（Recipe）には複数のStepやAmountが関連している」ことを表すプロパティであって、
	//データベースの1つのカラムではない
	//mappedBy=子の側に recipe というフィールドがあってそこに外部キーがある
	@OneToMany(mappedBy="recipe",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<Step> steps = new ArrayList<>();
	
	@OneToMany(mappedBy="recipe",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<Recipe_Ingredient> amos = new ArrayList<>();
	
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
		return this.mainImg;
	}
	public void setMainImg(Img mainImg) {
		this.mainImg = mainImg;
	}
	public List<Step> getSteps(){
		return this.steps;
	}
	public void setSteps(List<Step> steps) {
		this.steps=steps;
	}
	public List<Recipe_Ingredient> getAmos() {
		return amos; 
	}
    public void setAmos(List<Recipe_Ingredient> amounts) { 
    	this.amos = amounts;
    }
}
