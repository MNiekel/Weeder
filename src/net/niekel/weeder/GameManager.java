package net.niekel.weeder;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.app.Activity;

public class GameManager implements Runnable {
	
	Graphics graphics;
	List<Object> objects = new ArrayList<Object>();
	Context context;
	Activity activity;
	
	public GameManager(Context c) {
		context = c;
		graphics = new Graphics(context);
	}

	@Override
	public void run() {
	}
}
