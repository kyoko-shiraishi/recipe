package com.example.demo.Logic;
import java.util.Timer;
import java.util.TimerTask;

public class AlermTimer {
	//タイマー起動させる
	public void startTimer(int minutes,Character c) {
		Timer timer = new Timer();
		long delay = minutes * 60 * 1000L;
	
	//時間がたったら
	timer.schedule(new TimerTask() {
		@Override
		public void run() {
			String message = minutes+"分経った";
			c.speak(message);
		}
	},delay);
	}
}
