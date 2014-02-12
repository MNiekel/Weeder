package net.niekel.weeder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class GameActivity extends Activity implements GrassSurface.OnTouchListener, Dandelion.AnimationListener {
	
	private final String TAG = "GameActivity";
	
	public final String KEY_SCORE = "score";
	public final String KEY_FINISHED = "finished";
	public final String KEY_HISCORE = "hiscore";
		
	private final int desiredFrameRate = 60;
	private final int maxDandelions = 10;
	private final int maxSpawningTime = 1200;
	private final float fraction = 0.85f;
	
	private Handler handler = new Handler();
	private Random random = new Random();
	
	private GrassSurface surface;
	private List<Dandelion> dandelions = new ArrayList<Dandelion>();
	private MediaPlayer music;
	
	private long start;
	private long score;
	private int spawningTime;
	private SharedPreferences prefs;
	private long hiscore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_game);
				
		surface = (GrassSurface) findViewById(R.id.screen);
		Dandelions.loadBitmaps(this);
		prefs = getPreferences(MODE_PRIVATE);
		hiscore = prefs.getLong(KEY_HISCORE, 0);
	}

	@Override
	public void onPause() {
		super.onPause();
		stopMusic();
		stop();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		startMusic();
		start();
	}

	@Override
	public void onBackPressed() {
		music.pause();
		stop();
		buildDialog("Quit?", false);
	}

	private void buildDialog(String tag, boolean finished) {
		DialogFragment end = new Alert();
		Bundle args = new Bundle();
		args.putBoolean(KEY_FINISHED, finished);
		args.putInt(KEY_SCORE, (int) score);
		args.putInt(KEY_HISCORE, (int) hiscore);
		end.setArguments(args);
		end.setCancelable(false);
		end.show(getFragmentManager(), tag);
	}
	
	private Runnable updateScreen = new Runnable() {
		public void run() {
			surface.update(dandelions, getSeconds());
			surface.invalidate();
			handler.postDelayed(updateScreen, 1000/desiredFrameRate);
		}
	};
	
	private Runnable addDandelion = new Runnable() {
		public void run() {
			if (dandelions.size() > maxDandelions) {
				Log.i(TAG, "too many dandelions => end game");
				endGame();
			} else {
				spawnDandelion();
				handler.postDelayed(addDandelion, random.nextInt(spawningTime));
			}
		}
	};

	private void spawnDandelion() {
		Dandelion d = new Dandelion(this);
		d.setX(random.nextInt(surface.getWidth() - d.getBitmap().getWidth()));
		d.setY(random.nextInt(surface.getHeight() - d.getBitmap().getHeight()));
	
		dandelions.add(d);
	}
	
	private void stopMusic() {
		music.stop();
		music.release();
	}
	
	private void stop() {
		handler.removeCallbacks(updateScreen);
		handler.removeCallbacks(addDandelion);
		for (Dandelion d : dandelions) {
			d.removeCallbacks();
		}
		dandelions.clear();
	}
	
	private void startMusic() {
		music = MediaPlayer.create(this, R.raw.music);
		music.setLooping(true);
		music.start();
	}

	private void start() {
		start = System.currentTimeMillis();
		score = 0;
		spawningTime = maxSpawningTime;
		handler.post(updateScreen);
		handler.postDelayed(addDandelion, 2000);
	}
	
	private void endGame() {
		score = getSeconds();
		if (score >= hiscore) {
			hiscore = score;
			prefs.edit().putLong(KEY_HISCORE, hiscore).commit();
		}
				
		stop();
		music.pause();
		
		buildDialog("Game Over", true);
	}
	
	public void restartGame() {
		music.seekTo(0);
		music.start();
		start();
	}
	
	private long getSeconds() {
		return (System.currentTimeMillis() - start) / 1000;
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
		Log.i(TAG, "dandelion overblown");
	}
	
	@Override
	public void onSpawnSeeds() {
		Log.i(TAG, "dandelion started spreading seeds => decrease spawning time");
		spawningTime = (int) (spawningTime * fraction);
	}
}
