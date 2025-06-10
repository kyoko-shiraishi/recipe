package com.example.demo.Logic;

import java.util.Objects;

public abstract class Character implements Cloneable{
//フィールド
	protected String name;
	
//メソッド
//すべてのキャラが必ず持つ「セリフ」や「動作」の枠
	//コンストラクタで初期化
	public Character(String name) {
		this.name = name;
	}
	
	//getter/setter

	public String getNmae() {

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
	@Override
	public boolean equals(Object o) {
		if(o == this)return true; //もし2つのオブジェクトが**まったく同じ実体（参照）**なら、中身が同じに決まっているので true を返す
		//自分のクラス情報と相手のクラス情報が一致しなければ、別のクラスのインスタンスなので false
		if(o == null||getClass() != o.getClass())return false;
		//Object 型なので、キャストして使えるようにする
		Character other  = (Character) o;
		//「自分と相手の name が同じ文字列なら、等しいキャラとみなす
		return this.name.equals(other.name); //これ自体戻り値が boolean 値なのでそのままreturnできる
	}
	@Override
	public int hashCode() {
		return Objects.hash(this.name);
	}
	@Override
	public Character clone() {
		try {

			//親クラス (Object) の clone() メソッドを呼び出し
			//→現在のオブジェクトのフィールド内容をコピーした新しいオブジェクト(Object型)を生成
	        return (Character) super.clone(); 

	    } catch (CloneNotSupportedException e) {
	        throw new AssertionError(); // 通常は起きない例外
	    }
	}
}
