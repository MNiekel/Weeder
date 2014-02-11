package net.niekel.weeder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class GameActivity extends Activity implements GrassSurface.OnTouchListener, Dandelion.AnimationListener {
	
	private final String TAG = "GameActivity";
		
	private final int desiredFrameRate = 60;
	private final int maxDandelions = 10;
	
	
	private Handler handler = new Handler();
	private int maxSpawningTime = 1024;
	private Random random = new Random();
	
	private GrassSurface surface;
	private List<Dandelion> dandelions = new ArrayList<Dandelion>();
	private MediaPlayer music;
	
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
		music.stop();
		music.release();
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
		music = MediaPlayer.create(this, R.raw.music);
		music.setLooping(true);
		music.start();
		handler.postDelayed(updateScreen, 1000/desiredFrameRate);
		handler.postDelayed(addDandelion, maxSpawningTime);
	}
	
	private Runnable updateScreen = new Runnable() {
		public void run() {
			surface.update(dandelions);
			surface.invalidate();
			handler.postDelayed(updateScreen, 1000/desiredFrameRate);
		}
	};
	
	private Runnable addDandelion = new Runnable() {
		public void run() {
			if (dandelions.size() > maxDandelions) {
				Log.i(TAG, "too many dandelions => end game");
				handler.removeCallbacks(updateScreen);
				handler.removeCallbacks(addDandelion);
				for (Dandelion d : dandelions) {
					d.removeCallbacks();
				}
				dandelions.clear();
			} else {
				spawnDandelion();
				handler.postDelayed(addDandelion, random.nextInt(maxSpawningTime));
			}
		}
	};

	private void spawnDandelion() {
		Dandelion d = new Dandelion(this);
		d.setX(random.nextInt(surface.getWidth() - d.getBitmap().getWidth()));
		d.setY(random.nextInt(surface.getHeight() - d.getBitmap().getHeight()));
	
		dandelions.add(d);
	}

	@Override
	public void onTouch(int x, int y) {
		for (Dandelion d : dandelions) {
			Rect r = d.getRect();
			if (r.contains(x, y)) {
				d.removeCallbacks();
				dandelions.remove(d);
				break;
			}
		}
	}
	
	@Override
	public void onAnimationEnded(Dandelion d) {
		Log.i(TAG, "dandelion overblown => stick last frame on background and remove from list");
		surface.updateBackground(d);
		d.removeCallbacks();
		dandelions.remove(d);
	}
	
	@Override
	public void onSpawnSeeds() {
		Log.i(TAG, "dandelion started spreading seeds => decrease spawning time");
		maxSpawningTime = (int) (maxSpawningTime * 0.9);
	}
}
