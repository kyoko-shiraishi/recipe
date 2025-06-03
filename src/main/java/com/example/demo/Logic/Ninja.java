package com.example.demo.Logic;

public class Ninja extends Character{
	//コンストラクタ
	public Ninja(String name) {
		super(name); //親クラスである Character のコンストラクタを呼び出している。new Ninjaされるときに呼ばれる
	}
	
	@Override
	public void speak(String message) {
		System.out.println(message+this.getEnd());
	}
	
	@Override
	public void act() {
		System.out.println("苦無を投げる");
	}
	
	@Override
	public String getEnd() {
		return "ぞ";
	}
	@Override
	public boolean equals(Object o) {
		if(o == this)return true; //もし2つのオブジェクトが**まったく同じ実体（参照）**なら、中身が同じに決まっているので true を返す
		//自分のクラス情報と相手のクラス情報が一致しなければ、別のクラスのインスタンスなので false
		if(o == null||getClass() != o.getClass())return false;
		//Object 型なので、Ninja 型にキャストして使えるようにする
		Ninja other  = (Ninja) o;
		//「自分と相手の name が同じ文字列なら、等しいキャラとみなす
		return this.name.equals(other.name); //これ自体戻り値が boolean 値なのでそのままreturnできる
	}
	
		public static void main(String[] args) {
        Character c = new Ninja("そんなもん");
        Character fake = new Ninja("くみがしら");
        c.act();  // 動作確認
        System.out.println(c.equals(fake));
        AlermTimer timer = new AlermTimer();
        int timePassed = 1;
        timer.startTimer(timePassed, c);
    }
}
