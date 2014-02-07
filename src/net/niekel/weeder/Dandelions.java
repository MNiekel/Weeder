package net.niekel.weeder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Dandelions {
		
	private static Bitmap bitmap;
	
	public static void setBitmap(Context c, int r) {
		bitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.dandelion_flower_32x32);
	}
	
	public static Bitmap getBitmap(int state) {
		switch (state) {
			case 0:
				//flower
				return bitmap;
			default:
				return null;
		}
	}

}
