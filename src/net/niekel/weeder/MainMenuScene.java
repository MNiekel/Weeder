package net.niekel.weeder;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.opengl.font.Font;


public class MainMenuScene extends MenuScene implements IOnMenuItemClickListener {
	
	SimpleActivity activity;
	final int MENU_START = 0;
	final int MENU_HELP = 1;
	
	public MainMenuScene(Camera pCamera) {
		super(pCamera);
		activity = SimpleActivity.getSharedInstance();
		
		Font font = ResourceManager.getSharedInstance().defaultFont;
		setBackground(ResourceManager.getSharedInstance().backgroundSprite);
		 
		IMenuItem startButton = new TextMenuItem(MENU_START, font, activity.getString(R.string.start_game), activity.getVertexBufferObjectManager());
		startButton.setPosition(mCamera.getWidth() / 2 - startButton.getWidth() / 2, mCamera.getHeight() / 2 - startButton.getHeight() / 2);
		addMenuItem(startButton);
		 
		setOnMenuItemClickListener(this);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
        	case MENU_START:
        		activity.setCurrentScene(new GameScene());
        		return true;
        	default:
        		break;
		}
		return false;
	}

}
