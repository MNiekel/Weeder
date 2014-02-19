package net.niekel.weeder;

import java.util.ArrayList;
import java.util.List;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.input.touch.TouchEvent;
import android.graphics.Rect;
import android.util.Log;


public class GameScene extends Scene implements IAnimationListener, IOnSceneTouchListener {
	
	public static final String TAG = "GameScene";
	
	public static float mSpawningFraction;
	private long startTime;
	public long totalTime;
	public int score;
		
	private List<DandelionSprite> dandelions = new ArrayList<DandelionSprite>();

	public GameScene() {
		Log.d(TAG, "Create GameScene ");
		
		setBackground(ResourceManager.getSharedInstance().backgroundSprite);
	    
	    setOnSceneTouchListener(this);
	    	    
	    resetValues();
	}
	
	public void spawnDandelion() {
		if (dandelions.size() > 10) {
			Log.d(TAG, "Game Over");
			gameOver();
		}
		DandelionSprite dandelion = DandelionPool.getSharedInstance().obtainPoolItem();
		dandelion.setRandomPosition();

		attachChild(dandelion);
		dandelion.animate(this);
	
		dandelions.add(dandelion);
	}
	
	private void gameOver() {
		totalTime = (System.currentTimeMillis() - startTime) / 1000;
		setChildScene((Scene) (new GameOverScene(SimpleActivity.getSharedInstance().mCamera, this)));
		clearUpdateHandlers();
	}
	
	public void detach() {
	    clearUpdateHandlers();
	    for (DandelionSprite d : dandelions) {
	        DandelionPool.getSharedInstance().recyclePoolItem(d);
	    }
	    dandelions.clear();
	    detachChildren();
	}
	
	public void resetValues() {
		clearChildScene();
		for (DandelionSprite d : dandelions) {
	        DandelionPool.getSharedInstance().recyclePoolItem(d);
	    }
	    dandelions.clear();
	    mSpawningFraction = 1f;
	    startTime = System.currentTimeMillis();
	    totalTime = 0;
	    score = 0;
	    registerUpdateHandler(new GameUpdateHandler());
	}

	@Override
	public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite, int pOldFrameIndex, int pNewFrameIndex) {
		if (pNewFrameIndex == 5) {
			Log.i(TAG, "Started spreading seeds");
			mSpawningFraction *= 0.85;
		}
	}	

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		synchronized (this) {
			if (pSceneTouchEvent.isActionDown()) {
				for (DandelionSprite d : dandelions) {
					Rect r = d.getRect();
					if (r.contains((int) pSceneTouchEvent.getX(), (int) pSceneTouchEvent.getY())) {
						dandelions.remove(d);
						DandelionPool.getSharedInstance().recyclePoolItem(d);
						score++;
						break;
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
			int pInitialLoopCount) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
			int pRemainingLoopCount, int pInitialLoopCount) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		// TODO Auto-generated method stub
	}
}
