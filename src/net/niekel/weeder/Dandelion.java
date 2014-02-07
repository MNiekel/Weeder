package net.niekel.weeder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;

public class Dandelion implements Runnable {
	
	public interface StateListener {
		public void onStateChanged(Dandelion d, int state);
	}
	
	private static final String TAG = "Dandelion";
	
	public static final int STATE_GROW = 0;
	public static final int STATE_FLOWER = 1;
	public static final int STATE_BLOW = 2;
	public static final int STATE_EXPLODE = 3;
	
	private final int GROWING_TIME = 2500;
	private final int BLOSSOMING_TIME = 2000;
	private final int ENDING_TIME = 2000;
	
	private int state;
	private Point pos = new Point();
	private Handler handler = new Handler(); //make new handler???
	
	private StateListener listener;
	private Context context;
	
	public Dandelion(Context c) {
		init(c, 0, 0);
	}
	
	public Dandelion(Context c, int x, int y) {
		init(c, x, y);
	}
	
	private void init(Context c, int x, int y) {
		setX(x);
		setY(y);
		state = STATE_GROW;
		context = c;
		try {
            listener = (StateListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement StateListener");
        }
		handler.postDelayed(this, GROWING_TIME);
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
	
	public void removeCallbacks() {
		handler.removeCallbacks(this);
	}

	@Override
	public void run() {
		Log.v(TAG, "run");
		switch (state) {
		case STATE_GROW:
			state = STATE_FLOWER;
			handler.postDelayed(this, BLOSSOMING_TIME);
			break;
		case STATE_FLOWER:
			state = STATE_BLOW;
			handler.postDelayed(this, ENDING_TIME);
			break;
		case STATE_BLOW:
			state = STATE_EXPLODE;
			break;
		default:
			handler.removeCallbacks(this);
			break;
		}
		listener.onStateChanged(this, state);
	}
}
