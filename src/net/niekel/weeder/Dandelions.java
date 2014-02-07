package net.niekel.weeder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Dandelions {
		
	private static Bitmap flower;
	private static Bitmap empty;
	
	public static void setBitmap(Context c, int r) {
		flower = BitmapFactory.decodeResource(c.getResources(), R.drawable.dandelion_flower_32x32);
		empty = BitmapFactory.decodeResource(c.getResources(), R.drawable.dandelion_empty_32x32);
	}
	
	public static Bitmap getBitmap(int state) {
		switch (state) {
			case Dandelion.STATE_GROW:
				//flower
				return empty;
			case Dandelion.STATE_FLOWER:
				//blowing flower
				return flower;
			default:
				return flower;
		}
	}

}
