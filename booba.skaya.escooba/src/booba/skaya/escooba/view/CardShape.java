package booba.skaya.escooba.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.util.Log;
import android.view.MotionEvent;
import booba.skaya.escooba.game.EscoobaCard;
import booba.skaya.escooba.util.Rects;

public class CardShape extends ShapeDrawable implements TouchableInterface{
	
	private final Paint mPaintOutline;
	private final Paint mPaintInsideNoCard;
	private final Paint mPaintInside;
	private boolean mChosen;
	private EscoobaCard mCard;
	private Paint mPaintText;
	



	public CardShape() {
		mPaintInsideNoCard = new Paint();
		mPaintInsideNoCard.setStyle(Style.FILL_AND_STROKE);
		mPaintInsideNoCard.setColor(0xFFBBBBBB);
		mPaintInside = new Paint();
		mPaintInside.setStyle(Style.FILL_AND_STROKE);
		mPaintInside.setColor(0xFF333333);
		mPaintOutline=  new  Paint();
		mPaintOutline.setStyle(Style.STROKE);
		mPaintOutline.setStrokeWidth(4);
		mPaintText = new Paint();
		mPaintText.setTextSize(30);
		mPaintText.setColor(0xFFFFFFFF);
		
		mCard = null;
		setChosen(false);
	}
	
	
	@Override
	public void setBounds(int left, int top, int right, int bottom) {
		Rect bounds = new Rect(left, top, right, bottom);
		//Shrink to card ratio 2/3
		Rects.insetsToRatio(bounds, 2f/3f);
		super.setBounds(bounds.left, bounds.top, bounds.right, bounds.bottom);
	}

	@Override
	public void setBounds(Rect bounds) {
		Rects.insetsToRatio(bounds, 2f/3f);
		super.setBounds(bounds);
		
	}

	@Override
	public void draw(Canvas canvas) {
		Rect r = copyBounds();
		r.inset(3, 3);
		//draw card value
		if(mCard == null){
			canvas.drawRoundRect(new RectF(r), (float) r.width()/10, (float)r.height()/10, mPaintInsideNoCard);	
		}else{
			canvas.drawRoundRect(new RectF(r), (float) r.width()/10, (float)r.height()/10, mPaintInside);
			//draw card value
			canvas.drawText(mCard.toString(), r.centerX(), r.centerY(), mPaintText);
		}
		//paint the border
		canvas.drawRoundRect(new RectF(r), (float) r.width()/10, (float)r.height()/10, mPaintOutline);
	}

	public boolean onTouchEvent(MotionEvent event) {
		if(getBounds().contains((int) event.getX(), (int) event.getY())){
			Log.i("ESCOOBA", "Clicked on card");
			if(mCard != null || mChosen){
				setChosen(!mChosen);
			}
			return true;
		}
		return false;
	}

	void setChosen(boolean choose) {
		mChosen = choose;
		mPaintOutline.setColor(mChosen?0xFF44FF44:0xFFBBBBBB);
		
		invalidateSelf();
	}
	
	EscoobaCard getCard() {
		return mCard;
	}


	void setCard(EscoobaCard mCard) {
		this.mCard = mCard;
	}


	public boolean isChosen() {
		return mChosen;
	}

}
