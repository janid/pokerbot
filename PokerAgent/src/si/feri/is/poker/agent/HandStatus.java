/**
 * 
 */
package si.feri.is.poker.agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import si.feri.is.poker.Defs;

/**
 * @author Zganec
 *
 */
public class HandStatus {

	/**
	 * cards in my hand
	 */
	private Vector<Card> handCards;
	
	/**
	 * The first three community cards, put out face up
	 */
	private Vector<Card> flopCards;
	
	/**
	 * The fourth community card. Put out face up, by itself. Also known as "fourth street.
	 */
	private Card turnCard;
	
	/**
	 * The fifth and final community card. 
	 */
	private Card riverCard;
	
	/**
	 * Opponents cards
	 */
	private Vector<Card> opponentsCards;
	
	/**
	 * list of current actions rXX, c, f 
	 */
	private List<List<Action>> actions;
	
	/**
	 * am I dealer?
	 */
	private Boolean amDealer; 
	
	/**
	 * hand number
	 */
	private Integer handNumber;
	
	/**
	 * is hand completed - dont send any new actions to server in this hand - FOLD or 
	 */
	private Boolean isCompleted;
	
	/**
	 * true - send my actions to server
	 */
	private Boolean isMyTurn;
	
	/**
	 * my money on table, my bet
	 */
	private Integer myBet; 
	
	/**
	 * opponents bet
	 */
	private Integer opponentsBet;
	
	/**
	 * constructor
	 */
	public HandStatus() {
		handCards = new Vector<Card>();
		flopCards = new Vector<Card>();
		turnCard = null;
		riverCard = null;
		opponentsCards = null;
		actions = new ArrayList<List<Action>>();
		amDealer = Boolean.FALSE;
		handNumber = 0;
		isCompleted = Boolean.FALSE;
		isMyTurn = Boolean.FALSE;
		
		myBet = 0;
		opponentsBet = 0;
	}
	
	public Vector<Card> getHandCards() {
		return handCards;
	}
	public void setHandCards(Vector<Card> hand) {
		this.handCards = hand;
	}
	public Vector<Card> getFlopCards() {
		return flopCards;
	}

	public void setFlopCards(Vector<Card> flop) {
		this.flopCards = flop;
	}

	public Card getTurnCard() {
		return turnCard;
	}
	public void setTurnCard(Card turn) {
		this.turnCard = turn;
	}

	public Card getRiverCard() {
		return riverCard;
	}

	public void setRiverCard(Card river) {
		this.riverCard = river;
	}
	
	public Vector<Card> getOpponentsCards() {
		return opponentsCards;
	}

	public void setOpponentsCards(Vector<Card> opponentsCards) {
		this.opponentsCards = opponentsCards;
	}

	public List<List<Action>> getActions() {
		return actions;
	}

	public void setActions(List<List<Action>> actions) {
		this.actions = actions;
	}

	public Boolean amDealer() {
		return amDealer;
	}

	public void amDealer(Boolean amDealer) {
		this.amDealer = amDealer;
	}
	
	public Integer getHandNumber() {
		return handNumber;
	}
	
	public void setHandNumber(Integer handNumber) {
		this.handNumber = handNumber;
	}
	
	public Boolean isCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public Boolean isMyTurn() {
		return isMyTurn;
	}

	public void setIsMyTurn(Boolean isMyTurn) {
		this.isMyTurn = isMyTurn;
	}
	
	public Integer getMyBet() {
		return myBet;
	}

	public void setMyBet(Integer myBet) {
		this.myBet = myBet;
	}

	public Integer getOpponentsBet() {
		return opponentsBet;
	}

	public void setOpponentsBet(Integer opponentsBet) {
		this.opponentsBet = opponentsBet;
	}
	

	public String toString() {
		StringBuffer buff = new StringBuffer();
		
		buff.append("HAND: ");
		buff.append(handNumber);
		buff.append(", dealer: ");
		buff.append(amDealer ? "me" : "server");
		buff.append(", isCompleted: ");
		buff.append(isCompleted);
		buff.append(", isMyTurn: ");
		buff.append(isMyTurn);
		buff.append(Defs.NEWLINE);
		
		buff.append("HAND CARDS: ");
		buff.append(handCards);
		buff.append(Defs.NEWLINE);

		buff.append("CARDS ON TABLE: ");
		buff.append(flopCards);
		buff.append(" | ");
		buff.append(turnCard);
		buff.append(" | ");
		buff.append(riverCard);
		buff.append(Defs.NEWLINE);
		
//		buff.append("TURN CARD:  ");
//		buff.append(turnCard);
//		buff.append(Defs.NEWLINE);
//
//		buff.append("RIVER CARD: ");
//		buff.append(riverCard);
//		buff.append(Defs.NEWLINE);
		
		buff.append("OPPONENTS CARDS: ");
		buff.append(opponentsCards);
		buff.append(Defs.NEWLINE);

		buff.append("ACTIONS: ");
		buff.append(actions);
		buff.append(Defs.NEWLINE);

		buff.append("MONEY on table:   ");
		buff.append("me: ");
		buff.append(myBet);
		buff.append(", opponent: ");
		buff.append(opponentsBet);
		buff.append(Defs.NEWLINE);

		buff.append("-------------------");
		
		return buff.toString();
	}
	
}
