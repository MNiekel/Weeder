package net.niekel.weeder;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import android.graphics.Typeface;
import android.util.Log;

public class ResourceManager {
	private final static String TAG = "ResourceManager";
	
	public ITextureRegion backgroundRegion;
	public ITiledTextureRegion dandelionRegion;
	public SpriteBackground backgroundSprite;
	
	public Font defaultFont;

	
				
	private static ResourceManager instance;
	
	public static ResourceManager getSharedInstance() {
		if (instance == null) {
			Log.i(TAG, "Creating new ResourceManager");
			instance = new ResourceManager();
		}
		return instance;
	}
	
	public void loadGameResources() {
		SimpleActivity activity = SimpleActivity.getSharedInstance();
		defaultFont = FontFactory.create(activity.getFontManager(), activity.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
	    defaultFont.load();
	    
	    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	    BitmapTextureAtlas backgroundTexture = new BitmapTextureAtlas(activity.getTextureManager(), 400, 400, TextureOptions.DEFAULT);
	    backgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTexture, activity, "grass.png", 0, 0);
	    backgroundTexture.load();
	    backgroundSprite = new SpriteBackground(new Sprite(0, 0, SimpleActivity.CAMERA_WIDTH, SimpleActivity.CAMERA_HEIGHT,
	    		backgroundRegion, activity.getVertexBufferObjectManager()));
	    
	    BitmapTextureAtlas dandelionTexture = new BitmapTextureAtlas(activity.getTextureManager(), 400, 400, TextureOptions.DEFAULT);
	    dandelionRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(dandelionTexture, activity, "dandelion_atlas.png", 0, 0, 4, 2);
	    dandelionTexture.load();
	}
}
