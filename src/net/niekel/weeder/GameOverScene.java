package net.niekel.weeder;

import org.andengine.entity.scene.Scene;
import org.andengine.util.HorizontalAlign;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;

public class GameOverScene extends CameraScene implements IOnSceneTouchListener {

	boolean done;
	SimpleActivity activity;
	GameScene scene;

	public GameOverScene(Camera mCamera, GameScene gameScene) {
		super(mCamera);
		scene = gameScene;
		activity = SimpleActivity.getSharedInstance();
		setBackgroundEnabled(false);
		
		final String resultString = activity.getString(R.string.message_gameover_1) + scene.totalTime +
				activity.getString(R.string.message_gameover_2) + scene.score +
				activity.getString(R.string.message_gameover_3) +
				activity.getString(R.string.message_gameover_4);
		
		final TextOptions options = new TextOptions();
		options.setAutoWrap(AutoWrap.WORDS);
		options.setAutoWrapWidth(SimpleActivity.CAMERA_WIDTH);
		options.setHorizontalAlign(HorizontalAlign.CENTER);
		

		Text result = new Text(0f, 0f, ResourceManager.getSharedInstance().defaultFont, resultString, 400, options, activity.getVertexBufferObjectManager());

		final int y = (int) (SimpleActivity.CAMERA_HEIGHT / 2 - result.getHeight() / 2);

		done = false;
		result.setY(mCamera.getHeight() + result.getHeight());
		MoveYModifier mod = new MoveYModifier(5, result.getY(), y) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				done = true;
			}
		};
		attachChild(result);
		result.registerEntityModifier(mod);
		setOnSceneTouchListener(this);
	}

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
		if (!done)
			return true;
		scene.resetValues();
		return false;
	}

}
