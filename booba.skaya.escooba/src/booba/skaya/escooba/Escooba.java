package booba.skaya.escooba;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import booba.skaya.escooba.game.EscoobaCard;
import booba.skaya.escooba.game.EscoobaEvent;
import booba.skaya.escooba.game.EscoobaGame;
import booba.skaya.escooba.game.EscoobaPlayer;
import booba.skaya.escooba.game.EscoobagameListener;
import booba.skaya.escooba.view.CardView;

public class Escooba extends Activity implements EscoobagameListener {

	private static final int SCORE_ACTIVITY_RETURN = 1234;
	
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
		_touchListener = new EscobaOnTouchListener(this);
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
		_g.registerGameListener(this);
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
	        case R.id.action_end_game:
	        	//for purpose test, end the current game
	            //calling _g.endGame() instead of this.endGame() is the correct thing to do.
	        	//Because _g.endGame() perform stuff, and this is only the viewer
	        	_g.endGame();
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
		ArrayList<EscoobaCard> playedCard = getSelectedCards();
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
			_headerText.setText("Player: "+playerId+" Round "+_g.getRound()+"("+_g.getCurrentGameScore()[playerId]+"pts)");
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
			if(index < _tableCards.length && _tableCards[index] != null) _tableCards[index++].setCard(c);
		}
		
		index = 0;
		for(EscoobaCard c : _g.getCurrentPlayer().getHand()){
			if(_playerCards[index] != null) _playerCards[index++].setCard(c);
		}
		String bottom = "";
		for(EscoobaPlayer p : _g.getPlayers()){
			bottom += "P"+p.getId()+" "+p.getTrickSize()+"c\t| "+p.getOrosNb()+"o | "+p.getEscobaNumber()+"e | "+p.get7Nb()+"7"+(p.has7deOro()?"+":"")+" = "+_g.getCurrentGameScore()[p.getId()]+"pts\n";
		}
		if(_bottomText != null) _bottomText.setText(bottom);
	}

	@Override
	public void somethingHappen(EscoobaEvent event) {
		switch (event) {
		case GAME_NEW:
		case ROUND_NEW:
			refreshGame();
			break;
		case GAME_END:
			endGame();
			break;
		}
	}

	private void endGame() {
		//launch intent that display the score
		Intent intent = new Intent(this, EscoobaScoreActivity.class);
		//retrieve the score to be displayed
		intent.putExtra(EscoobaScoreActivity.SCORE_EXTRA, _g.getScores());
	    startActivityForResult(intent, SCORE_ACTIVITY_RETURN);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK && requestCode == SCORE_ACTIVITY_RETURN){
			newGame();
		}
	}

	public void cardToggled() {
		if(_g != null){
			Button button = (Button) findViewById(R.id.play_button);
			button.setEnabled(_g.isPossible(getSelectedCards()));
		}
	}
	
	private ArrayList<EscoobaCard> getSelectedCards() {
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
		return playedCard;
	}
}