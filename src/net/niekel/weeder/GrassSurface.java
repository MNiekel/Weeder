package net.niekel.weeder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;


public class GrassSurface extends SurfaceView implements Runnable {
	
	private final String TAG = "GrassSurface";
	
	private Bitmap image;
	private int xpos = 0;
	private int ypos = 0;

	public GrassSurface(Context context) {
		super(context);
		
		//image = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
		//setWillNotDraw(false);
		//setFocusableInTouchMode(true);
	}
	
	public GrassSurface(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public GrassSurface(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Log.d(TAG, "onDraw");
		//canvas.drawBitmap(image, xpos, ypos, new Paint());
		super.onDraw(canvas);

	}

	/*
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.d(TAG, "surfaceChanged");
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "surfaceCreated");
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "surfaceDestroyed");
	}
	*/

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d(TAG, "onTouchEvent");
		xpos = (int) event.getX();
		ypos = (int) event.getY();
		//invalidate();
		return super.onTouchEvent(event);
	}

	@Override
	public void run() {
		Log.d(TAG, "run");
	}
}
