package net.niekel.weeder;

import org.andengine.entity.scene.Scene;
import org.andengine.opengl.font.Font;

public class GameOverScene extends Scene {
	
	private SimpleActivity activity;

	public GameOverScene() {
		activity = SimpleActivity.getSharedInstance();
		
		Font font = ResourceManager.getSharedInstance().defaultFont;
		setBackground(ResourceManager.getSharedInstance().backgroundSprite);
	}

}
