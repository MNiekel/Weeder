package net.niekel.weeder;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Dandelions {
	
	public static final int ANIMATION_LENGTH = 8;
	public static final int STAGE_LENGTH = 350;
	public static final int FRAME_START_BLOWING = 5;
	
	private static Bitmap[] frames = new Bitmap[ANIMATION_LENGTH];

	public static void loadBitmaps(Context c) {
		int size = c.getResources().getDimensionPixelSize(R.dimen.bitmap);
		AssetManager assets = c.getAssets();
		for (int i = 0; i < ANIMATION_LENGTH; i++) {
			String file = "frame" + i + ".png";
			try {
			    InputStream stream = assets.open(file);
			    frames[i] = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(stream), size, size, false);
			    stream.close();
			} catch (IOException e) {
			    e.printStackTrace();
			}
		}
	}
	
	public static Bitmap getFrame(int framenr) {
		return frames[framenr];
	}

}
