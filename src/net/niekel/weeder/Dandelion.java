package net.niekel.weeder;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;

public class Dandelion implements Runnable {
	
	private static final String TAG = "Dandelion";
	
	public static final int STATE_GROW = 0;
	public static final int STATE_FLOWER = 1;
	public static final int STATE_BLOW = 2;
	
	private int state;
	private Point pos = new Point();
	private Handler handler;
	
	public Dandelion(Handler h) {
		init(h, 0, 0);
	}
	
	public Dandelion(Handler h, int x, int y) {
		init(h, x, y);
	}
	
	private void init(Handler h, int x, int y) {
		setX(x);
		setY(y);
		state = STATE_GROW;
		handler = h;
		handler.postDelayed(this, 2000);
	}
	
	public Point getPosition() {
		return pos;
	}

	public void setPosition(Point p) {
		setX(p.x);
		setY(p.y);
	}
	
	public int getX() {
		return pos.x;
	}
	
	public int getY() {
		return pos.y;
	}
	
	public void setX(int x) {
		pos.x = x;
	}
	
	public void setY(int y) {
		pos.y = y;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int s) {
		state = s;
	}
	
	public Bitmap getBitmap() {
		return Dandelions.getBitmap(state);
	}
	
	public Rect getRect() {
		int w = Dandelions.getBitmap(state).getWidth();
		int h = Dandelions.getBitmap(state).getHeight();
		return new Rect(pos.x, pos.y, pos.x + w, pos.y + h);
	}

	@Override
	public void run() {
		Log.v(TAG, "run");
		switch (state) {
		case STATE_GROW:
			state = STATE_FLOWER;
			handler.postDelayed(this, 5000);
			break;
		case STATE_FLOWER:
			state = STATE_BLOW;
			handler.postDelayed(this, 2000);
			break;
		default:
			handler.removeCallbacks(this);
			break;
		}
	}
}
