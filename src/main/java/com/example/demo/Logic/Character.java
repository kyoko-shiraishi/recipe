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
	
	//spreak()
	public abstract void speak();
	//act()
	public abstract void act();
	
}
