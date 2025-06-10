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
