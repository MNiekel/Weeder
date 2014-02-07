package net.niekel.weeder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.view.View;

public class Graphics {
	private Bitmap frameBuffer;
	private Canvas canvas;
	private Context context;

	public Graphics(Context context) {
	    this.context = context;
	}
	
	public void setFrameBuffer(View v) {
		frameBuffer = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Config.ARGB_8888);
	    canvas = new Canvas(frameBuffer);
	}

	public Bitmap present() {
	    return frameBuffer;
	}
}
