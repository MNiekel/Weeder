package net.niekel.weeder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.view.View;

public class Graphics {
	private Bitmap frameBuffer;
	private Canvas canvas;
	private Context context;

	public Graphics(Context c) {
	    context = c;
	}
	
	public void setFrameBuffer() {
		View screen = ((Activity) context).findViewById(R.id.screen);
		frameBuffer = Bitmap.createBitmap(screen.getWidth(), screen.getHeight(), Config.ARGB_8888);
	    canvas = new Canvas(frameBuffer);
	}

	public Bitmap present() {
	    return frameBuffer;
	}
}
