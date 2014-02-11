package net.niekel.weeder;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
	
	private final String TAG = "MainActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		Log.i(TAG, "onCreate()");
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
