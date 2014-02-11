package net.niekel.weeder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;

public class Dandelion implements Runnable {
	
	public interface AnimationListener {
		public void onAnimationEnded(Dandelion d);
		public void onSpawnSeeds();
	}
	
	private Point pos = new Point();
	private Handler handler = new Handler();
	private int frame = 0;
	
	private AnimationListener listener;
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
		context = c;
		try {
            listener = (AnimationListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement AnimationListener");
        }
		handler.postDelayed(this, Dandelions.STAGE_LENGTH);
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
	
	public Bitmap getBitmap() {
		return Dandelions.getFrame(frame);
	}
	
	public Rect getRect() {
		int w = Dandelions.getFrame(frame).getWidth();
		int h = Dandelions.getFrame(frame).getHeight();
		return new Rect(pos.x, pos.y, pos.x + w, pos.y + h);
	}
	
	public int getFrame() {
		return frame;
	}
	
	public void removeCallbacks() {
		handler.removeCallbacks(this);
	}

	@Override
	public void run() {
		if (frame == (Dandelions.ANIMATION_LENGTH - 1)) {
			listener.onAnimationEnded(this);
			removeCallbacks();
		} else {
			frame++;
			if (frame == Dandelions.FRAME_START_BLOWING) {
				listener.onSpawnSeeds();
			}
			handler.postDelayed(this, Dandelions.STAGE_LENGTH);
		}
	}
}
