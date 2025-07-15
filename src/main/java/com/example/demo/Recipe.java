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
@Table(name="recipes")
public class Recipe {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column                         
	private long id;
	
	@Column(length=50,nullable=false)
	                  //未入力を禁止するバリデーション追記
	private String name; 

	@Column
	private String comment; //レシピとコメントの記述は一対多なのでdescriptionsテーブルにrecipe_id
	
	@OneToOne(cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name = "main_img_id")
	private Img mainImg; //画像に関する情報
	
	//「このエンティティ（Recipe）には複数のStepやAmountが関連している」ことを表すプロパティであって、
	//データベースの1つのカラムではない
	//mappedBy=子の側に recipe というフィールドがあってそこに外部キーがある
	@OneToMany(mappedBy="recipe",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<Step> steps = new ArrayList<>();
	
	@OneToMany(mappedBy="recipe",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<Amount> amos = new ArrayList<>();
	
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
	public List<Amount> getAmos() {
		return amos; 
	}
    public void setAmos(List<Amount> amounts) { 
    	this.amos = amounts;
    }
}
