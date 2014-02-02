package booba.skaya.escooba.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import booba.skaya.escooba.game.EscoobaCard;
import booba.skaya.escooba.game.EscoobaGame;

public class EscoobaTableView extends View {
	private Paint mTextPaint;
//	private Bitmap mCardBitmap;
	private String _status;
	
	private int _lastX;
	private int _lastY;
	private Paint _clickPaint;
	
	//the shapes to display
	PlayerShape _playerShape = new PlayerShape();
	TableShape _tableShape   = new TableShape();

	public EscoobaTableView(Context context) {
		super(context);
		init();
	}

	public void setEscoobaGame(EscoobaGame g) {
		_playerShape.setG(g);
		_tableShape.setG(g);
		invalidate();
	}

	public EscoobaTableView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public EscoobaTableView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setColor(0xBBFFFFFF);
		//mCardBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.basto_1);
		_status = "status text";
		_clickPaint = new Paint();
		_clickPaint.setColor(0xBBFFFFFF);
		_clickPaint.setStyle(Paint.Style.STROKE);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		double playerZoneRatio = 0.2;
		_tableShape.setBounds(0, 0, w,  (int) ((1.0 - playerZoneRatio)*h));
		_playerShape.setBounds(0, (int) ((1.0 - playerZoneRatio)*h), w, h);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//Draw player cards
		_tableShape.draw(canvas);
		_playerShape.draw(canvas);
		// Draw the label text
		canvas.drawText(_status, 10, 10, mTextPaint);
		canvas.drawCircle(_lastX, _lastY, 10, _clickPaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean result = super.onTouchEvent(event);
		_status = "New on touch event "+event.getX()+" "+event.getY();
		_lastX = (int) event.getX();
		_lastY = (int) event.getY();
		_playerShape.onTouchEvent(event);
		_tableShape.onTouchEvent(event);
		invalidate();
		return result;
	}

	public ArrayList<EscoobaCard> getSelectedCards() {
		ArrayList<EscoobaCard> playedCard = new ArrayList<EscoobaCard>();
		for(CardShape c : _playerShape.getCards()){
			if(c.isChosen()){
				playedCard.add(c.getCard());
			}
		}
		for(CardShape c : _tableShape.getCards()){
			if(c.isChosen() && c.getCard() != null){
				playedCard.add(c.getCard());
			}
		}
		return playedCard;
	}

	public void unselectAllCards() {
		for(CardShape c : _playerShape.getCards()){
			c.setChosen(false);
		}
		for(CardShape c : _tableShape.getCards()){
			c.setChosen(false);
		}
	}
}
