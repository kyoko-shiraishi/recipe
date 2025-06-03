package com.example.demo.Logic;

public abstract class Character {
//フィールド
	protected String name;
	
//メソッド
//すべてのキャラが必ず持つ「セリフ」や「動作」の枠
	//コンストラクタで初期化
	public Character(String name) {
		this.name = name;
	}
	
	//getter/setter
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	//発話させる
	public abstract void speak(String message);
	//個別の語尾付ける
	public abstract String getEnd();
	//動かす
	public abstract void act();
	
	@Override
	public String toString() {
		return "Character{name='" + name + "'}"; //toString()のオーバーライド
	}
}
