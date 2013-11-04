package com.example.skcc_client.common;

import com.example.skcc_client.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class ImageHelper {

	public ImageHelper() {
		super();
	}
	
	public static Bitmap getItemIcon(Context context, Bitmap bitmap, int borderRound, int borderWidth, int state, int progressRate) {
		
		/////////////////////////////////////////////////////////////////////
		// Set values
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		int backColor = context.getResources().getColor(R.color.icon_rounding);
		int stateColor = context.getResources().getColor(R.color.item_nothing);
		
		if(state == Constants.code.ITEM_STATE_PRODUCING) stateColor = context.getResources().getColor(R.color.item_producing);
		else if(state == Constants.code.ITEM_STATE_FINISHED) stateColor = context.getResources().getColor(R.color.item_finished);
		else if(state == Constants.code.ITEM_STATE_ROTTEN) stateColor = context.getResources().getColor(R.color.item_rotten);
		
		
		/////////////////////////////////////////////////////////////////////
		// Set area
		int progressWidth = bitmap.getWidth() * Math.abs(progressRate) / 100;
		
		final Rect rectOrg = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final Rect rectComplete = new Rect(0, 0, progressWidth, bitmap.getHeight());
		final Rect rectIncomplete = new Rect(progressWidth, 0, bitmap.getWidth(), bitmap.getHeight());
		
		final RectF rectF = new RectF(rectOrg);
		
		
		/////////////////////////////////////////////////////////////////////
		// Set paint
		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(backColor);
		paint.setStyle(Paint.Style.FILL);
		
		
		/////////////////////////////////////////////////////////////////////
		// Set color filter
		float[] colorTransformRotten = {
	            0.55f, 0, 0, 0, 0, 
	            0, 0.55f, 0, 0, 0,
	            0, 0, 0.55f, 0, 0, 
	            0, 0, 0, 0.6f, 0};
		float[] colorTransformIncomplete = {
	            0.9f, 0, 0, 0, 0, 
	            0, 0.6f, 0, 0, 0,
	            0, 0, 0.2f, 0, 0, 
	            0, 0, 0, 0.8f, 0};
		ColorMatrix colorMatrix = new ColorMatrix();
		colorMatrix.setSaturation(0f);
		colorMatrix.set(colorTransformRotten);
		ColorMatrixColorFilter colorFilterRotten = new ColorMatrixColorFilter(colorMatrix);
		colorMatrix.set(colorTransformIncomplete);
		ColorMatrixColorFilter colorFilterIncomplete = new ColorMatrixColorFilter(colorMatrix);
		
		
		/////////////////////////////////////////////////////////////////////
		// Draw rounded icon
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawRoundRect(rectF, borderRound, borderRound, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		
		
		/////////////////////////////////////////////////////////////////////
		// Draw complete part
		if(state == Constants.code.ITEM_STATE_FINISHED || state == Constants.code.ITEM_STATE_ROTTEN) {
			paint.setColorFilter(colorFilterRotten);
		}
		canvas.drawBitmap(bitmap, rectComplete, rectComplete, paint);
		paint.setColorFilter(null); // Remove color filter
		
		
		/////////////////////////////////////////////////////////////////////
		// Draw incomplete part
		if(state == Constants.code.ITEM_STATE_PRODUCING) {
			paint.setColorFilter(colorFilterIncomplete);
		}
		canvas.drawBitmap(bitmap, rectIncomplete, rectIncomplete, paint);
		paint.setColorFilter(null); // Remove color filter
		
		
		/////////////////////////////////////////////////////////////////////
		// Draw rounded border
		paint.setColor(stateColor);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth((float) borderWidth);
		canvas.drawRoundRect(rectF, borderRound, borderRound, paint);
		
		return output;
	}
}
