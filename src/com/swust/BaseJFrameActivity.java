package com.swust;

import javax.swing.JFrame;

public abstract class BaseJFrameActivity extends JFrame {

	private static final long serialVersionUID = 1L;

	public BaseJFrameActivity() {
		// 打印类名
		logClassName();
	}

	public void logClassName() {
		String className = getClass().getSimpleName();
		Log.sop("--->" + className);
	}

}
