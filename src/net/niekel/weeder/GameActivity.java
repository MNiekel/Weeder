package net.niekel.weeder;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;

public class GameActivity extends Activity implements GrassSurface.OnTouchListener {
	
	private final String TAG = "GameActivity";
	
	private final int desiredFrameRate = 60;
	
	private Handler handler = new Handler();

	private long startTime;
	private long lastTime;

	private GrassSurface surface;
	private List<Dandelion> dandelions = new ArrayList<Dandelion>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_game);
		
		Log.i(TAG, "onCreate()");
		
		surface = (GrassSurface) findViewById(R.id.screen);
		Dandelions.setBitmap(this, R.drawable.dandelion_flower_32x32);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.i(TAG, "onPause()");
		handler.removeCallbacks(updateScreen);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.i(TAG, "onResume()");
		lastTime = System.currentTimeMillis();
		handler.postDelayed(updateScreen, 1000/desiredFrameRate);
	}
	
	private Runnable updateScreen = new Runnable() {
        public void run() {
        	long currentTime = System.currentTimeMillis();
        	long secondsPassed = currentTime - lastTime; 
        	lastTime = currentTime;
        	surface.invalidate();
            handler.postDelayed(updateScreen, 1000/desiredFrameRate);
        }
     };

	@Override
	public void onMove(int x, int y) {
		dandelions.add(new Dandelion(x, y));
		surface.update(dandelions);
	}
	
	@Override
	public void onTouch(int x, int y) {
		for (Dandelion d : dandelions) {
			Rect r = d.getRect();
			if (r.contains(x, y)) {
				dandelions.remove(d);
				break;
			}
		}
		surface.update(dandelions);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Log.i(TAG, "onStart()");
	}
	
	@Override
	public void onRestart() {
		super.onRestart();
		Log.i(TAG, "onRestart()");
	}
	
	@Override
	public void onStop() {
		super.onStop();
		Log.i(TAG, "onStop()");
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "onDestroy()");
	}
}
