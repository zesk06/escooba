package booba.skaya.escooba;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import booba.skaya.escooba.game.EscoobaCard;
import booba.skaya.escooba.game.EscoobaGame;
import booba.skaya.escooba.game.EscoobaPlayer;
import booba.skaya.escooba.game.EscoobagameListener;
import booba.skaya.escooba.view.CardView;

public class Escooba extends Activity {

	private final static int TABLE_CARDS_NB = 8;
	private final static int HAND_CARDS_NB = 3;
	private final CardView[] _tableCards;
	private final CardView[] _playerCards;

	private final EscobaOnTouchListener _touchListener;
	private EscoobaGame _g;
	private transient TextView _headerText;
	private transient TextView _bottomText;
	
	public Escooba(){
		_tableCards   = new CardView[TABLE_CARDS_NB];
		_playerCards  = new CardView[HAND_CARDS_NB];
		_touchListener = new EscobaOnTouchListener();
		setEscoobaGame(new EscoobaGame());
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_escoba);
		//
		_headerText = (TextView) findViewById(R.id.header_text);
		_bottomText = (TextView) findViewById(R.id.bottom_text);
		//
		_tableCards[0] = (CardView) findViewById(R.id.table_card_1);
		_tableCards[1] = (CardView) findViewById(R.id.table_card_2);
		_tableCards[2] = (CardView) findViewById(R.id.table_card_3);
		_tableCards[3] = (CardView) findViewById(R.id.table_card_4);
		_tableCards[4] = (CardView) findViewById(R.id.table_card_5);
		_tableCards[5] = (CardView) findViewById(R.id.table_card_6);
		_tableCards[6] = (CardView) findViewById(R.id.table_card_7);
		_tableCards[7] = (CardView) findViewById(R.id.table_card_8);
		//
		_playerCards[0] = (CardView) findViewById(R.id.player_card_1);
		_playerCards[1] = (CardView) findViewById(R.id.player_card_2);
		_playerCards[2] = (CardView) findViewById(R.id.player_card_3);
		//
		
		registerCardsListeners();

	}
	
	private void setEscoobaGame(EscoobaGame g){
		_g = g;
		_g.registerGameListener(new EscoobagameListener() {
			@Override
			public void somethingHappen() {
				refreshGame();
			}
		});
		//first refresh
		refreshGame();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.escoba, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_new_game:
	            newGame();
	            setStatusText("New Game started");
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable("GAME", _g);
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		setEscoobaGame((EscoobaGame) savedInstanceState.getSerializable("GAME"));
		super.onRestoreInstanceState(savedInstanceState);
	}

	private void registerCardsListeners() {
		for(ImageView card : _tableCards){
			card.setOnClickListener(_touchListener);
		}
		for(ImageView card : _playerCards){
			card.setOnClickListener(_touchListener);
		}
	}
	
	void setStatusText(String msg){
        _bottomText.setText(msg);
	}
	
	public void play(View view){
		//get selected cards
		ArrayList<EscoobaCard> playedCard = new ArrayList<EscoobaCard>();
		for(CardView c : _playerCards){
			if(c.isChosen()){
				playedCard.add(c.getCard());
				break;
			}
		}
		for(CardView c : _tableCards){
			if(c.isChosen() && c.getCard() != null){
				playedCard.add(c.getCard());
			}
		}
		//play them if playable
		if(_g.isPossible(playedCard)){
			_g.play(playedCard);
			//unselect cards
			unselectAllCards();
			//play the other player as IA
			while(_g.getCurrentPlayer().getId() != 0){
				_g.autoplay();
			}
			//
			refreshGame();
		}else{
			_bottomText.setText("This move is not possible - try again");
		}
	}
	
	private void unselectAllCards() {
		for(CardView c : _playerCards){
			c.setChosen(false);
		}
		for(CardView c : _tableCards){
			c.setChosen(false);
		}
	}

	private void newGame() {
		//start a new game
		Toast.makeText(this, "New Game", Toast.LENGTH_SHORT).show();
		_g.newGame();
		_g.newRound();
	}
	
	private void refreshGame(){
		//Something happen in the game model, refresh the view
		if(_g != null && _headerText != null){
			int playerId = _g.getCurrentPlayer().getId();
			_headerText.setText("Player: "+playerId+" Round "+_g.getRound()+"("+_g.getRoundScore()[playerId]+"pts)");
		}
		//void the cards  -- 
		for(CardView cv : _tableCards){
			if(cv != null) cv.setCard(null);
		}
		for(CardView cv : _playerCards){
			if(cv != null) cv.setCard(null);
		}
		//get the table content :
		int index = 0;
		for(EscoobaCard c : _g.getTableCards()){
			if(_tableCards[index] != null) _tableCards[index++].setCard(c);
		}
		
		index = 0;
		for(EscoobaCard c : _g.getCurrentPlayer().getHand()){
			if(_playerCards[index] != null) _playerCards[index++].setCard(c);
		}
		String bottom = "";
		for(EscoobaPlayer p : _g.getPlayers()){
			bottom += "P"+p.getId()+" "+p.getTrickSize()+"c | "+p.getOrosNb()+"o | "+p.getEscobaNumber()+"e "+(p.has7deOro()?"7o":"")+" = "+_g.getRoundScore()[p.getId()]+"pts\n";
		}
		if(_bottomText != null) _bottomText.setText(bottom);
	}
}
