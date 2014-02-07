package net.niekel.weeder;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

public class Dandelion {
	
	private int state;
	private Point pos = new Point();
	
	public Dandelion() {
		setX(0);
		setY(0);
		state = 0;
	}
	
	public Dandelion(int x, int y) {
		setX(x);
		setY(y);
		state = 0;
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
}
