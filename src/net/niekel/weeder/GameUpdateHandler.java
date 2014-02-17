package net.niekel.weeder;

import java.util.Random;

import org.andengine.engine.handler.IUpdateHandler;

public class GameUpdateHandler implements IUpdateHandler {
	
	public static final String TAG = "GameUpdateHandler";
	private final float MAX_SPAWN_INTERVAL = 2f;
	private final float MIN_SPAWN_INTERVAL = 0.5f;
		
	private float mTime = 0;
	private float mInterval;
	private Random mRandom = new Random();

	public GameUpdateHandler() {
		mInterval = MAX_SPAWN_INTERVAL;
	}

	@Override
	public void onUpdate(float pSecondsElapsed) {
		mTime += pSecondsElapsed;
		if (mTime >= mInterval) {
			GameScene scene = (GameScene) SimpleActivity.getSharedInstance().mCurrentScene;
			scene.spawnDandelion();
			mInterval = Math.max(MIN_SPAWN_INTERVAL, mRandom.nextFloat() * MAX_SPAWN_INTERVAL * GameScene.mSpawningFraction);
			mTime = 0;
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
	}
}
