package com.example.demo.DTO;
import java.util.List;
import com.example.demo.DTO.StepRequest;
//レシピ名・コメント・手順リスト・画像リストなどフォームからの情報を格納するDTOプロパティ
public class UpdateRequest {
	private Long id;
	private String name;
	private String comment;
	private String mainImg; //画面のフォームからパス（文字列）をうけとる
	private List<StepRequest> steps;
//他のクラスがDTOからDTOプロパティの情報にアクセスできるようにgetter/setterを設定	
	public Long getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public String getComment() {
		return this.comment;
	}
	public String getMainImg() {
		return this.mainImg;
	}
	public List<StepRequest> getSteps(){
		return this.steps;
	}
	
	public void setId(Long id) {
	    this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setMainImg(String mainImg) {
		this.mainImg = mainImg;
	}
	public void setSteps(List<StepRequest> steps) {
		this.steps = steps;
	}
	
}
