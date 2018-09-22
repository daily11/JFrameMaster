package com.swust;

public class MainActivity {

	public static void main(String[] args) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				JFrameActivity mainFrame = new JFrameActivity();
			}
		});
		thread.start();
	}
}
