package net.niekel.weeder;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class GameActivity extends Activity implements GrassSurface.OnTouchListener, Dandelion.AnimationListener {
	
	private final String TAG = "GameActivity";
		
	private final int desiredFrameRate = 60;
	private int maxSpawningTime = 2000;
	
	private Handler handler = new Handler();

	private GrassSurface surface;
	private List<Dandelion> dandelions = new ArrayList<Dandelion>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_game);
		
		Log.i(TAG, "onCreate()");
		
		surface = (GrassSurface) findViewById(R.id.screen);
		Dandelions.loadBitmaps(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.i(TAG, "onPause()");
		handler.removeCallbacks(updateScreen);
		handler.removeCallbacks(addDandelion);
		for (Dandelion d : dandelions) {
			d.removeCallbacks();
		}
		dandelions.clear();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.i(TAG, "onResume()");
		handler.postDelayed(updateScreen, 1000/desiredFrameRate);
		handler.postDelayed(addDandelion, (int) (Math.random() * maxSpawningTime));
	}
	
	private Runnable updateScreen = new Runnable() {
		public void run() {
			surface.invalidate();
			surface.update(dandelions);
			handler.postDelayed(updateScreen, 1000/desiredFrameRate);
		}
	};
	
	private Runnable addDandelion = new Runnable() {
		public void run() {
			if (dandelions.size() > 10) {
				Log.d(TAG, "Too many dandelions => creating no new ones");
				handler.removeCallbacks(updateScreen);
				handler.removeCallbacks(addDandelion);
			} else {
				spawnDandelion();
				handler.postDelayed(addDandelion, (int) (Math.random() * maxSpawningTime));
			}
		}
	};

	
	private void spawnDandelion() {
		Dandelion d = new Dandelion(this);
		d.setX((int) (Math.random() * (surface.getWidth() - d.getBitmap().getWidth())));
		d.setY((int) (Math.random() * (surface.getHeight() - d.getBitmap().getHeight())));
	
		dandelions.add(d);
	}
     
	@Override
	public void onMove(int x, int y) {
	}
	
	@Override
	public void onTouch(int x, int y) {
		for (Dandelion d : dandelions) {
			Rect r = d.getRect();
			if (r.contains(x, y)) {
				d.removeCallbacks();
				dandelions.remove(d);
				surface.update(dandelions);
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

	@Override
	public void onAnimationEnded(Dandelion d) {
		surface.updateBackground(d);
		d.removeCallbacks();
		dandelions.remove(d);
	}
	
	@Override
	public void onSpawnSeeds() {
		Log.v(TAG, "dandelion started spawning seeds => change spawning time dandelion");
		maxSpawningTime = (int) (maxSpawningTime * 0.9);
	}
}
