package net.niekel.weeder;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;

public class GameActivity extends Activity implements GrassSurface.OnTouchListener {
	
	private final String TAG = "GameActivity";
	
	private final int desiredFrameRate = 60;
	
	private Handler handler = new Handler();

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
		handler.removeCallbacks(addDandelion);
		for (Dandelion d : dandelions) {
			handler.removeCallbacks(d);
		}
		dandelions.clear();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.i(TAG, "onResume()");
		handler.postDelayed(updateScreen, 1000/desiredFrameRate);
		handler.postDelayed(addDandelion, 5000);
	}
	
	private Runnable updateScreen = new Runnable() {
		public void run() {
			surface.invalidate();
			handler.postDelayed(updateScreen, 1000/desiredFrameRate);
			surface.update(dandelions);
		}
	};
	
	private Runnable addDandelion = new Runnable() {
		public void run() {
			if (dandelions.size() > 10) {
				Log.v(TAG, "Too many dandelions => creating no new ones");
			} else {
				Dandelion d = new Dandelion(handler);
				d.setX((int) (Math.random() * (surface.getWidth() - d.getBitmap().getWidth())));
				d.setY((int) (Math.random() * (surface.getHeight() - d.getBitmap().getHeight())));
			
				dandelions.add(d);
			}
			handler.postDelayed(addDandelion, 5000);
		}
	};
     
	@Override
	public void onMove(int x, int y) {
	}
	
	@Override
	public void onTouch(int x, int y) {
		for (Dandelion d : dandelions) {
			Rect r = d.getRect();
			if (r.contains(x, y)) {
				handler.removeCallbacks(d);
				dandelions.remove(d);
				break;
			}
		}
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
