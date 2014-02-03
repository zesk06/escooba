package booba.skaya.escooba.util;

import android.graphics.Rect;

public class Rects {
	
	/**
	 * Shrink the given rect to respect the given ratio W/H
	 * 
	 * The dimension are only reduced.
	 */
	public static void insetsToRatio(Rect rect, float ratio) {
		float currentRatio = (float) rect.width()/(float) rect.height();
		if( currentRatio > ratio){
			//width is too big
			//compute expected width
			float expectedWidth = ratio * rect.height();
			float dx = (float) (rect.width() - expectedWidth) / 2;
			rect.inset( (int) dx, 0);
		}else if(((float) rect.width()/(float) rect.height()) < 2/3){
			//width is too big
			//compute expected width
			float expectedHeight = (float) rect.width() / ratio;
			float dy = (float) (rect.height() - expectedHeight) / 2;
			rect.inset( 0, (int) dy);
		}
	}
}
