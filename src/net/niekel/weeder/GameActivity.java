package net.niekel.weeder;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class GameActivity extends Activity {
	
	private final String TAG = "GameActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_game);
		
		Log.i(TAG, "onCreate()");
		
		//GrassSurface surface = new GrassSurface(this);
		//setContentView(surface);
		//surface.requestFocusFromTouch();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
	public void onResume() {
		super.onResume();
		Log.i(TAG, "onResume()");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		Log.i(TAG, "onPause()");
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
