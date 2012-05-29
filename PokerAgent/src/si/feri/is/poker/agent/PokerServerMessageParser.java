package si.feri.is.poker.agent;

import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import si.feri.is.poker.Defs;

public class PokerServerMessageParser {
	
	private HandStatus hand;
	
	public PokerServerMessageParser(String message) throws Exception {
		
		if(message == null || message.length() < 1) {
			throw new Exception("Message empty");
		}
		
		hand = new HandStatus();
		parse(message);
		analizeHand();
		System.out.println(message);
		System.out.println(hand);
	}

	public HandStatus getHandStatus() {
		return hand;
	}
	
	private void parse(String string) throws Exception {
		
		if(string == null || string.length() < 1) {
			throw new Exception("Message empty");
		}
		
		String [] temp = string.split(":");
		
		
		// 0. MATCHSTATE - ignore it
		
		// 1. dealer status
		if(temp[1].equals("0")) {
			hand.amDealer(Boolean.FALSE);
		} else {
			hand.amDealer(Boolean.TRUE);
		}
		
		// 2. handNumber 
		hand.setHandNumber(Integer.parseInt(temp[2]));
		
		// 3. actions
		parseActions(temp[3]);
		
		// 4. cards
		if(temp[4] != null && temp[4].length() > 0) {
			String[] cardsString = temp[4].split("/");
			
			if(cardsString.length > 0) {
				//1. my and opponents cards
				
				// NOTdealer | dealer
				if(hand.amDealer()) {
					String [] tempCards = (cardsString[0] + " ").split("\\|");
					hand.setOpponentsCards(parseCards(tempCards[0]));
					hand.setHandCards(parseCards(tempCards[1]));
				} else {
					String [] tempCards = (cardsString[0] + " ").split("\\|");
					hand.setHandCards(parseCards(tempCards[0]));
					hand.setOpponentsCards(parseCards(tempCards[1]));
				}
				
				for (int i = 1; i < cardsString.length; i++) {
					Vector<Card> cards = parseCards(cardsString[i]);
					if(i == 1) {
						hand.setFlopCards(cards);
					} else if(i == 2) {
						hand.setTurnCard(cards.get(0));
					} else if(i == 3) {
						hand.setRiverCard(cards.get(0));
					}
				}
			}
		}	
	}
	

	private void parseActions(String string) throws Exception {
		if (string == null || string.trim().length() < 1) {
			return;
		}

		String[] temp = (string + " ").split("/");
		for (String string2 : temp) {
			Vector<Action> actions = new Vector<Action>();
			Pattern p = Pattern.compile(Defs.ACTION_REGEX);
			Matcher m = p.matcher(string2);

			while (m.find()) {
				String actionString = string2.substring(m.start(), m.end());
				if ("c".equalsIgnoreCase(actionString)) {
					actions.add(new Action(true));
				} else if ("f".equalsIgnoreCase(actionString)) {
					actions.add(new Action(false));
					hand.setIsCompleted(Boolean.TRUE);
				} else {
					actions.add(new Action(Integer.parseInt(actionString.replace("r", ""))));
				}
				
			}
			hand.getActions().add(actions);
		}
	}
	
	
	private Vector<Card> parseCards(String string) {
		if(string == null || string.trim().length() < 1) {
			return null;
		}
		string = string.trim();
		Vector<Card> cards = new Vector<Card>();
		
		for(int i = 0; i < string.length(); i=i+2) {
			cards.add(new Card(string.substring(i, i+2)));
		}
		
		return cards.size() == 0 ? null : cards;
	}
	
	/**
	 * sets attributes: isCompleted (if false), isMyTurn, money
	 */
	private void analizeHand() {

//		//calculate if is my turn
//		if(hand.getOpponentsCards() != null) {
//			hand.setIsCompleted(Boolean.TRUE);
//			hand.setIsMyTurn(Boolean.FALSE);
//		} else if(hand.isCompleted()) {
//			hand.setIsMyTurn(Boolean.FALSE);
//		} else {
//			
//			int actionsSize = hand.getActions().size();
//			int lastActionsSize = 0;
//			try {
//				lastActionsSize = hand.getActions().lastElement().size();
//			} catch (Exception e) {}
//			
//			if(actionsSize == 0) {
//				actionsSize = 1;
//			}
//			
//			if(hand.amDealer()) {
//				if(actionsSize == 1) {
//					if(lastActionsSize % 2 == 0) {						
//						hand.setIsMyTurn(Boolean.TRUE);
//					} else {
//						hand.setIsMyTurn(Boolean.FALSE);
//					}
//				} else {
//					if(lastActionsSize % 2 == 0) {						
//						hand.setIsMyTurn(Boolean.FALSE);
//					} else {
//						hand.setIsMyTurn(Boolean.TRUE);
//					}
//				}
//			} else {
//				if(actionsSize == 1) {
//					if(lastActionsSize % 2 == 0) {						
//						hand.setIsMyTurn(Boolean.FALSE);
//					} else {
//						hand.setIsMyTurn(Boolean.TRUE);
//					}
//				} else {
//					if(lastActionsSize % 2 == 0) {						
//						hand.setIsMyTurn(Boolean.TRUE);
//					} else {
//						hand.setIsMyTurn(Boolean.FALSE);
//					}
//				}
//			}
//		}
		
		// calculate money
		
		// set blinds
		int dealersBet = Defs.SMALL_BLIND;
		int playersBet = Defs.BIG_BLIND;
		
		boolean reversBlinds = true;
		boolean dealersAction = true;
		for (int i = 0; i < hand.getActions().size(); i++) {
			List<Action> actions = hand.getActions().get(i);
			reversBlinds = i < 1;
			dealersAction = reversBlinds;
			
			for (Action action : actions) {
				if(action.isCall()) {
					dealersBet = Math.max(dealersBet, playersBet);
					playersBet = Math.max(dealersBet, playersBet);
				} else if(action.isRaise()) {
					if(dealersAction) {
						dealersBet = action.getRaiseValue();
					} else {
						playersBet = action.getRaiseValue();
					}
				}
				dealersAction = !dealersAction;
			}
		}
		
		if(hand.amDealer()) {
			hand.setMyBet(dealersBet);
			hand.setOpponentsBet(playersBet);
		} else {
			hand.setMyBet(playersBet);
			hand.setOpponentsBet(dealersBet);
			
			//negiram ker nisem dealer, torej ce je on na vrsti, jaz nisem
			dealersAction = !dealersAction;
		}
		
		
		if(hand.getOpponentsCards() != null) {
			hand.setIsCompleted(Boolean.TRUE);
			hand.setIsMyTurn(Boolean.FALSE);
		} else if(hand.isCompleted()) {
			hand.setIsMyTurn(Boolean.FALSE);
		} else {
			hand.setIsMyTurn(dealersAction);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String [] args) throws Exception {
		new PokerServerMessageParser("MATCHSTATE:0:30::9s8h|");
		new PokerServerMessageParser("MATCHSTATE:0:30:c:9s8h|");
		new PokerServerMessageParser("MATCHSTATE:0:30:cc/:9s8h|/8c8d5c");
		new PokerServerMessageParser("MATCHSTATE:0:30:cc/r250:9s8h|/8c8d5c");
		new PokerServerMessageParser("MATCHSTATE:0:30:cc/r250c/:9s8h|/8c8d5c/6s");
		new PokerServerMessageParser("MATCHSTATE:0:30:cc/r250c/r500:9s8h|/8c8d5c/6s");
		new PokerServerMessageParser("MATCHSTATE:0:30:cc/r250c/r500c/:9s8h|/8c8d5c/6s/2d");
		new PokerServerMessageParser("MATCHSTATE:0:30:cc/r250c/r500c/r1250:9s8h|/8c8d5c/6s/2d");
		new PokerServerMessageParser("MATCHSTATE:0:30:cc/r250c/r500c/r1250c:9s8h|9c6h/8c8d5c/6s/2d");
		new PokerServerMessageParser("MATCHSTATE:1:31::|JdTc");
		new PokerServerMessageParser("MATCHSTATE:1:31:r300:|JdTc");
		new PokerServerMessageParser("MATCHSTATE:1:31:r300r900:|JdTc");
		new PokerServerMessageParser("MATCHSTATE:1:31:r300r900c/:|JdTc/6dJc9c");
		new PokerServerMessageParser("MATCHSTATE:1:31:r300r900c/r1800:|JdTc/6dJc9c");
		new PokerServerMessageParser("MATCHSTATE:1:31:r300r900c/r1800r3600:|JdTc/6dJc9c");
		new PokerServerMessageParser("MATCHSTATE:1:31:r300r900c/r1800r3600r9000:|JdTc/6dJc9c");
		new PokerServerMessageParser("MATCHSTATE:1:31:r300r900c/r1800r3600r9000c/:|JdTc/6dJc9c/Kh");
		new PokerServerMessageParser("MATCHSTATE:1:31:r300r900c/r1800r3600r9000c/r20000:|JdTc/6dJc9c/Kh");
		new PokerServerMessageParser("MATCHSTATE:1:31:r300r900c/r1800r3600r9000c/r20000c/:KsJs|JdTc/6dJc9c/Kh/Qc");

	}

}
