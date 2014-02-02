package booba.skaya.escooba.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.view.MotionEvent;
import booba.skaya.escooba.game.EscoobaCard;
import booba.skaya.escooba.game.EscoobaGame;

public class TableShape extends ShapeDrawable implements TouchableInterface{

	private static final int CARDS_NB     = 16;
	private static final int CARDS_BY_ROW = 4;
	private EscoobaGame _g;
	private final ArrayList<CardShape> _cards;

	public TableShape(){
		getPaint().setColor(0xFF269926);
		_cards = new ArrayList<CardShape>();
		//init cards
		for(int i = 0;i< CARDS_NB; i++){
			_cards.add(new CardShape());
		}
	}
	
	
	public void setG(EscoobaGame g) {
		_g = g;
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
		int cardwidth = getBounds().width()/CARDS_BY_ROW;
		int cardheight = Math.min(getBounds().height()/4, cardwidth*3/2);
		for(CardShape c : _cards){
			c.setBounds(
					  getBounds().left+column*cardwidth
					, getBounds().top+row*cardheight
					, getBounds().left+column*cardwidth+cardwidth
					, getBounds().top+row*cardheight+cardheight);
			column++;
			if(column == CARDS_BY_ROW){
				row++;
				column = 0;
			}
		}
	}


	@Override
	public void draw(Canvas canvas) {
		canvas.drawRect(getBounds(), getPaint());
		//Draw the cards as rounded rectangle
		if(_g != null){
			Iterator<EscoobaCard> it = _g.getTableCards().iterator();
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


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean result = false;
		for(CardShape c : _cards){
			result = result || c.onTouchEvent(event);
		}
		return result;
	}
	
	Collection<CardShape> getCards() {
		return _cards;
	}

}
