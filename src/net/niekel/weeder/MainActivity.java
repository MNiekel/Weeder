package net.niekel.weeder;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	
	private final String TAG = "MainActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		Log.i(TAG, "onCreate()");
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

	public void onClick(View v) {
		int id = v.getId();
		Intent intent;
		
		switch (id) {
		case R.id.button_play:
			Log.i(TAG, "Play button clicked");
			intent = new Intent(this, GameActivity.class);
			startActivity(intent);
			break;
		case R.id.button_help:
			Log.i(TAG, "Help button clicked");
			break;
		default:
			Log.e(TAG, "Unknown button clicked");
			break;
		}
	}
}
