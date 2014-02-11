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
		public void onMove(int x, int y);
    }
	
	private final String TAG = "GrassSurface";
	private final int TEXT_SIZE = 32;
	
	private Bitmap background_bitmap;
	private Canvas background_canvas = new Canvas();
	private Bitmap drawing_bitmap;
	private Canvas drawing_canvas = new Canvas();
	private Paint paint;
	private OnTouchListener listener;
	
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
		start = System.currentTimeMillis();
		
		try {
            listener = (OnTouchListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnTouchListener");
        }
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if ((drawing_bitmap == null) || (background_bitmap == null)) {
			Log.i(TAG, "Init of drawing area");
			drawing_bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Config.ARGB_8888);
			drawing_canvas.setBitmap(drawing_bitmap);
			background_bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Config.ARGB_8888);
			background_canvas.setBitmap(background_bitmap);
		}
		canvas.drawBitmap(background_bitmap, 0,  0, null);
		canvas.drawBitmap(drawing_bitmap, 0,  0, null);
		
		long passed = (System.currentTimeMillis() - start) / 1000;
		canvas.drawText("Seconds alive: " + passed, 0, TEXT_SIZE, paint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		//Log.d(TAG, "onTouchEvent: " +x+":"+y);
		int action = event.getAction();
		switch (action) {
			case MotionEvent.ACTION_MOVE:
				listener.onMove(x, y);
				break;
			case MotionEvent.ACTION_DOWN:
				listener.onTouch(x, y);
				break;
			default:
				break;
		}
		return true;
	}
	
	public void update(List<Dandelion> dandelions) {
		if ((drawing_bitmap == null) || (background_bitmap == null)) {
			return;
		}
		drawing_bitmap.eraseColor(Color.TRANSPARENT);
		for (Dandelion d : dandelions) {
			drawing_canvas.drawBitmap(d.getBitmap(), d.getX(), d.getY(), paint);
		}
	}
	
	public void updateBackground(Dandelion d) {
		background_canvas.drawBitmap(d.getBitmap(), d.getX(), d.getY(), paint);
	}
}
