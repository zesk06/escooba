package booba.skaya.escooba;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import booba.skaya.escooba.game.EscoobaCard;
import booba.skaya.escooba.game.EscoobaEvent;
import booba.skaya.escooba.game.EscoobaGame;
import booba.skaya.escooba.game.EscoobagameListener;
import booba.skaya.escooba.view.EscoobaTableView;

public class Escooba extends Activity implements EscoobagameListener {

	private static final int SCORE_ACTIVITY_RETURN = 1234;
	
	private EscoobaGame _g;
	private transient Button _playButton;
	private transient EscoobaTableView _tableView;
	
	public Escooba(){
		setEscoobaGame(new EscoobaGame());
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_escoba);
		_playButton = (Button) findViewById(R.id.play_button);
		_tableView = (EscoobaTableView) findViewById(R.id.escoobaTableView1);
		_tableView.setEscoobaGame(_g);
		_tableView.setEscooba(this);
	}
	
	private void setEscoobaGame(EscoobaGame g){
		_g = g;
		_g.registerGameListener(this);
		if(_tableView != null) _tableView.setEscoobaGame(_g);
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
		}
	}
	
	private void unselectAllCards() {
		_tableView.unselectAllCards();
	}

	private void newGame() {
		//start a new game
		Toast.makeText(this, "New Game", Toast.LENGTH_SHORT).show();
		_g.newGame();
		_g.newRound();
	}
	
	private void refreshGame(){
		if(_tableView != null) _tableView.invalidate();
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
		if(_g != null && _playButton != null){
			_playButton.setEnabled(_g.isPossible(getSelectedCards()));
		}
	}
	
	private ArrayList<EscoobaCard> getSelectedCards() {
		return _tableView.getSelectedCards();
	}
}