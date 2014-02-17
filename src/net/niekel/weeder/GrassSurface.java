package net.niekel.weeder;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class GrassSurface extends View {
	
	public interface OnTouchListener {
		public void onTouch(int x, int y);
    }
	
	private final String TAG = "GrassSurface";
	private final int TEXT_SIZE = 32;
	
	private Bitmap drawing_bitmap;
	private Canvas drawing_canvas = new Canvas();
	private Paint paint;
	private OnTouchListener listener;
	
	private long now;
	private long last;
	private long start;


	public GrassSurface(Context context) {
		super(context);
		init(context);
	}
	
	public GrassSurface(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public GrassSurface(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context) {
		paint = new Paint();
		paint.setTextSize(TEXT_SIZE);
		last = start;
		
		try {
            listener = (OnTouchListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnTouchListener");
        }
		invalidate();
	}
	
	public void resetTime(long time) {
		start = time;
		last = start;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (drawing_bitmap == null) {
			Log.i(TAG, "Init of drawing area");
			drawing_bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Config.ARGB_8888);
			drawing_canvas.setBitmap(drawing_bitmap);
		} else {
			canvas.drawBitmap(drawing_bitmap, 0,  0, null);
		
			long passed = (now - start) / 1000;
			long framerate = 1000 / (now - last);
			last = now;
		
			canvas.drawText("Seconds alive: " +passed, 0, TEXT_SIZE, paint);
			Log.d(TAG, "FPS: " +framerate);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		int action = event.getAction();
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				listener.onTouch(x, y);
				break;
			default:
				break;
		}
		return true;
	}
	
	public void update(List<Dandelion> dandelions, long seconds) {
		if (drawing_bitmap == null) {
			return;
		}
		now = seconds;
		drawing_bitmap.eraseColor(Color.TRANSPARENT);
		for (Dandelion d : dandelions) {
			drawing_canvas.drawBitmap(d.getBitmap(), d.getX(), d.getY(), paint);
		}
	}
}
