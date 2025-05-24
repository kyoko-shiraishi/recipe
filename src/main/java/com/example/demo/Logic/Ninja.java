package com.example.demo.Logic;

public class Ninja extends Character{
	//コンストラクタ
	public Ninja() {
		super("尊奈門"); //親クラスである Character のコンストラクタを呼び出している。new Ninjaされるときに呼ばれる
	}
	@Override
	public void speak() {
		System.out.println("諸泉尊奈門だ！！");
	}
	
	@Override
	public void act() {
		System.out.println("苦無を投げる");
	}
		public static void main(String[] args) {
        Character ninja = new Ninja();
        ninja.act();  // 動作確認
    }
}
