package booba.skaya.escooba.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import booba.skaya.escooba.R;
import booba.skaya.escooba.game.EscoobaCard;
import booba.skaya.escooba.game.EscoobaColor;

public class CardView extends ImageView {

	private boolean _chosen;
	private EscoobaCard _card;
	
	public CardView(Context context) {
		super(context);
		init();
	}

	public CardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init(){
		setChosen(false);
		
	}
	
	public void toggle(){
		setChosen(!_chosen);
	}

	public void setCard(EscoobaCard card) {
		_card = card;
		int rs = R.drawable.empty;
		if(card != null){
			if(card.getColor() == EscoobaColor.ORO){
				switch(card.getValue()){
				case 1:		rs = R.drawable.oro_1; break;
				case 2:		rs = R.drawable.oro_2; break;
				case 3:		rs = R.drawable.oro_3; break;
				case 4:		rs = R.drawable.oro_4; break;
				case 5:		rs = R.drawable.oro_5; break;
				case 6:		rs = R.drawable.oro_6; break;
				case 7:		rs = R.drawable.oro_7; break;
				case 8:		rs = R.drawable.oro_8; break;
				case 9:		rs = R.drawable.oro_9; break;
				case 10:	rs = R.drawable.oro_10; break;
				}
			}else if(card.getColor() == EscoobaColor.BASTOS){
				switch(card.getValue()){
				case 1:		rs = R.drawable.basto_1; break;
				case 2:		rs = R.drawable.basto_2; break;
				case 3:		rs = R.drawable.basto_3; break;
				case 4:		rs = R.drawable.basto_4; break;
				case 5:		rs = R.drawable.basto_5; break;
				case 6:		rs = R.drawable.basto_6; break;
				case 7:		rs = R.drawable.basto_7; break;
				case 8:		rs = R.drawable.basto_8; break;
				case 9:		rs = R.drawable.basto_9; break;
				case 10:	rs = R.drawable.basto_10; break;
				}
			}else if(card.getColor() == EscoobaColor.COPAS){
				switch(card.getValue()){
				case 1:		rs = R.drawable.copa_1; break;
				case 2:		rs = R.drawable.copa_2; break;
				case 3:		rs = R.drawable.copa_3; break;
				case 4:		rs = R.drawable.copa_4; break;
				case 5:		rs = R.drawable.copa_5; break;
				case 6:		rs = R.drawable.copa_6; break;
				case 7:		rs = R.drawable.copa_7; break;
				case 8:		rs = R.drawable.copa_8; break;
				case 9:		rs = R.drawable.copa_9; break;
				case 10:	rs = R.drawable.copa_10; break;
				}
			}else if(card.getColor() == EscoobaColor.ESPADAS){
				switch(card.getValue()){
				case 1:		rs = R.drawable.espada_1; break;
				case 2:		rs = R.drawable.espada_2; break;
				case 3:		rs = R.drawable.espada_3; break;
				case 4:		rs = R.drawable.espada_4; break;
				case 5:		rs = R.drawable.espada_5; break;
				case 6:		rs = R.drawable.espada_6; break;
				case 7:		rs = R.drawable.espada_7; break;
				case 8:		rs = R.drawable.espada_8; break;
				case 9:		rs = R.drawable.espada_9; break;
				case 10:	rs = R.drawable.espada_10; break;
				}
			}
		}
		setImageResource(rs);
	}
	
	public boolean isChosen(){
		return _chosen;
	}
	
	public void setChosen(boolean chosen){
		_chosen = chosen;
		setImageAlpha(_chosen?0x66:0xFF);
	}

	public EscoobaCard getCard() {
		return _card;
	}
	
}
