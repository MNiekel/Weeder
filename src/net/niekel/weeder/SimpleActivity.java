package net.niekel.weeder;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.util.DisplayMetrics;
import android.util.Log;

public class SimpleActivity extends SimpleBaseGameActivity {
	
	static int CAMERA_WIDTH = 800;
	static int CAMERA_HEIGHT = 600;
	 
	public Camera mCamera;
	public Scene mCurrentScene;
	
	public static SimpleActivity instance;

	@Override
	public EngineOptions onCreateEngineOptions() {
		instance = this;
		final DisplayMetrics metrics = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(metrics);
	    int screenWidth = metrics.widthPixels;
	    int screenHeight = metrics.heightPixels;
	    CAMERA_HEIGHT = (int) (screenHeight * ((float) CAMERA_WIDTH / (float) screenWidth));
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
	    return engineOptions;
	}

	@Override
	protected void onCreateResources() {
		ResourceManager.getSharedInstance().loadGameResources();
	}

	@Override
	protected Scene onCreateScene() {
		mEngine.registerUpdateHandler(new FPSLogger());
	    mCurrentScene = new MainMenuScene(mCamera);
	    
	    return mCurrentScene;
	}

	public static SimpleActivity getSharedInstance() {
	    return instance;
	}
	 
	public void setCurrentScene(Scene scene) {
	    mCurrentScene = scene;
	    getEngine().setScene(mCurrentScene);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if (mCurrentScene instanceof GameScene)
	    {
	        ((GameScene) mCurrentScene).detach();
	    	DandelionPool.instance = null;
	        setCurrentScene(new MainMenuScene(mCamera));
	    }
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
}
