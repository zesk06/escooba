package booba.skaya.escooba.view;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.util.Log;
import android.view.MotionEvent;
import booba.skaya.escooba.R;
import booba.skaya.escooba.game.EscoobaCard;
import booba.skaya.escooba.util.Rects;

public class CardShape extends ShapeDrawable implements TouchableInterface{
	
	private final Paint mPaintOutline;
	private final Paint mPaintInsideNoCard;
	private final Paint mPaintInside;
	private boolean mChosen;
	private EscoobaCard mCard;
	private Paint mPaintText;
	private static Bitmap mCardBitmap;



	public CardShape() {
		mPaintInsideNoCard = new Paint();
		mPaintInsideNoCard.setStyle(Style.FILL_AND_STROKE);
		mPaintInsideNoCard.setColor(0xFFBBBBBB);
		mPaintInside = new Paint();
		mPaintInside.setStyle(Style.FILL_AND_STROKE);
		mPaintInside.setColor(0xFF333333);
		mPaintOutline=  new  Paint();
		mPaintOutline.setStyle(Style.STROKE);
		mPaintOutline.setStrokeWidth(6);
		mPaintText = new Paint();
		mPaintText.setTextSize(30);
		mPaintText.setColor(0xFFFFFFFF);
		mCard = null;
		setChosen(false);
	}
	
	public static Bitmap initCardBitMap(Resources r){
		if(mCardBitmap == null){
			mCardBitmap = BitmapFactory.decodeResource(r, R.drawable.cards);
		}
		return mCardBitmap;
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
			//canvas.drawRoundRect(new RectF(r), (float) r.width()/10, (float)r.height()/10, mPaintInsideNoCard);	
		}else{
			Rect src = new Rect();
			src.top   = mCardBitmap.getHeight()/4 * mCard.getColor().ordinal();
			src.left  = mCardBitmap.getWidth()/10 * (mCard.getValue() -1);// minus 1 because cards starts at 0
			src.right = mCardBitmap.getWidth()/10 * (mCard.getValue() - 1 +1);
			src.bottom = mCardBitmap.getHeight()/4* (mCard.getColor().ordinal()+1);
			Log.i("ESCOOBA", "image: "+mCardBitmap.getWidth()+"/"+mCardBitmap.getHeight());
			canvas.drawBitmap(mCardBitmap, src, r, getPaint());
			
			//canvas.drawRoundRect(new RectF(r), (float) r.width()/10, (float)r.height()/10, mPaintInside);
			//draw card value
			//canvas.drawText(mCard.toString(), r.centerX(), r.centerY(), mPaintText);
			canvas.drawRoundRect(new RectF(r), (float) r.width()/15, (float)r.height()/15, mPaintOutline);
		}
		
		
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
		mPaintOutline.setColor(mChosen?0xFF44FF44:0xFF444444);
		
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
