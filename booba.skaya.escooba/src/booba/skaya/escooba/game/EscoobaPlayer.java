package booba.skaya.escooba.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class EscoobaPlayer implements Serializable {

	private static final long serialVersionUID = 4760743226102640472L;

	final private int _id;
	
	/**The current player's hand*/
	final private ArrayList<EscoobaCard> _hand;
	
	/**The cards accumulated during current game*/
	final private ArrayList<EscoobaCard> _tricks;
	
	private int _escobaNb;
	
	public EscoobaPlayer(int id) {
		_id = id;
		_hand = new ArrayList<EscoobaCard>();
		_tricks = new ArrayList<EscoobaCard>();
		_escobaNb = 0;
	}
	
	public void receiveCardToHand(EscoobaCard newCard){
		_hand.add(newCard);
	}
	
	public void receiveCardsToStock(Collection<EscoobaCard> cards){
		_tricks.addAll(cards);
	}

	public void newGame() {
		_hand.clear();
		_tricks.clear();
		_escobaNb = 0;
	}

	public Collection<EscoobaCard> getHand() {
		return _hand;
	}

	public int getId() {
		return _id;
	}

	public void addTrick(ArrayList<EscoobaCard> playedCards, boolean escoba) {
		_tricks.addAll(playedCards);
		if(escoba){ 
			_escobaNb++;
		}
	}

	/**
	 * Remove a card from hand
	 * @param playerCard	The card to be removed
	 */
	public void removeCard(EscoobaCard playerCard) {
		_hand.remove(playerCard);
	}

	public int getEscobaNumber() {
		return _escobaNb;
	}

	public int getTrickSize() {
		return _tricks.size();
	}

	public int getOrosNb() {
		int orosNb = 0;
		for(EscoobaCard c : _tricks){
			if(c.getColor() == EscoobaColor.ORO){
				orosNb +=1;
			}
		}
		return orosNb;
	}
	
	public boolean has7deOro(){
		for(EscoobaCard c : _tricks){
			if(c.getColor() == EscoobaColor.ORO && c.getValue() == 7){
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "player "+getId();
	}
}
