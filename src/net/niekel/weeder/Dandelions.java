package net.niekel.weeder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Dandelions {
		
	private static Bitmap flower;
	private static Bitmap empty;
	private static Bitmap blow;
	
	public static void setBitmap(Context c) {
		flower = BitmapFactory.decodeResource(c.getResources(), R.drawable.dandelion_flower);
		empty = BitmapFactory.decodeResource(c.getResources(), R.drawable.dandelion_empty);
		blow = BitmapFactory.decodeResource(c.getResources(), R.drawable.dandelion_blow);
	}
	
	public static Bitmap getBitmap(int state) {
		switch (state) {
			case Dandelion.STATE_GROW:
				return empty;
			case Dandelion.STATE_FLOWER:
				return flower;
			case Dandelion.STATE_BLOW:
				return blow;
			default:
				return flower;
		}
	}

}
