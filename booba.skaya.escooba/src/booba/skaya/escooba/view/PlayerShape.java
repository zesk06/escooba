package booba.skaya.escooba.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.view.MotionEvent;
import booba.skaya.escooba.game.EscoobaCard;
import booba.skaya.escooba.game.EscoobaGame;

public class PlayerShape extends ShapeDrawable implements TouchableInterface{
	private static final int CARDS_NB = 3;
	private EscoobaGame _g;
	
	private Paint mBackground;
	private final ArrayList<CardShape> _cards;
	
	PlayerShape(){
		mBackground = new Paint();//brown
		mBackground.setColor(0XFFA64D00);
		_cards = new ArrayList<CardShape>();
		//init cards
		for(int i = 0;i< CARDS_NB; i++){
			_cards.add(new CardShape());
		}
	}

	@Override
	public void draw(Canvas canvas) {
		//draw background
		canvas.drawRect(getBounds(), mBackground);
		//Draw the player's cards.
		//Draw the cards as rounded rectangle
		if(_g != null){
			Iterator<EscoobaCard> it = _g.getPlayerHand(0).iterator();
			for(CardShape c : _cards){
				if(it.hasNext()){
					c.setCard(it.next());
				}else{
					c.setCard(null);
				}
				c.draw(canvas);
			}
		}
	}

	public void setG(EscoobaGame g) {
		_g = g;
		invalidateSelf();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean result = false;
		//player's cards has special behavior, only 1 card can be selected at a time
		CardShape cardTouched = null;
		for(CardShape c : _cards){
			if(c.onTouchEvent(event)){
				cardTouched = c;
				break;
			}
		}
		if(cardTouched != null){
			//we touched at least one card, unselect all, and select the one touched
			for(CardShape c : _cards){
				c.setChosen(c == cardTouched);
			}
		}
		//If we did not touch a card, do nothing.
		return result;
	}
	
	@Override
	public void setBounds(int left, int top, int right, int bottom) {
		super.setBounds(left, top, right, bottom);
		boundsChanged();
	}


	@Override
	public void setBounds(Rect bounds) {
		super.setBounds(bounds);
		boundsChanged();
	}
	
	private void boundsChanged() {
		//update the cards shape
		int row = 0;
		int column = 0;
		int cardwidth = getBounds().width()/CARDS_NB;
		int cardheight =  getBounds().height();
		for(CardShape c : _cards){
			c.setBounds(
					  getBounds().left+column*cardwidth
					, getBounds().top+row*cardheight
					, getBounds().left+column*cardwidth+cardwidth
					, getBounds().top+row*cardheight+cardheight);
			column++;
			
		}
	}

	Collection<CardShape> getCards() {
		return _cards;
	}

}
