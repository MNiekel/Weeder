package net.niekel.weeder;

import java.util.Random;

import org.andengine.entity.sprite.AnimatedSprite;

import android.graphics.Rect;

public class DandelionSprite extends AnimatedSprite {
	
	private static final String TAG = "DandelionSprite";
	private static final int ANIMATION_TIME = 350;
	
	private Random mRandom = new Random();
	
	public DandelionSprite(float pX, float pY) {
		super(pX, pY, ResourceManager.getSharedInstance().dandelionRegion, SimpleActivity.getSharedInstance().getVertexBufferObjectManager());
	}

	public DandelionSprite() {
		super(0, 0, ResourceManager.getSharedInstance().dandelionRegion, SimpleActivity.getSharedInstance().getVertexBufferObjectManager());
	}
	
	public Rect getRect() {
		return new Rect((int) mX, (int) mY, (int) (mX + mWidth), (int) (mY + mHeight));
	}
	
	public void setRandomPosition() {
		int width = (int) (SimpleActivity.CAMERA_WIDTH - mWidth);
		int height = (int) (SimpleActivity.CAMERA_HEIGHT - mHeight);
		setPosition(mRandom.nextInt(width), mRandom.nextInt(height));
	}
	
	public void animate(IAnimationListener listener) {
		super.animate(ANIMATION_TIME, false, listener);
	}
}
