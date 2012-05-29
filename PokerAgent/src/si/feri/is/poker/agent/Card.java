package si.feri.is.poker.agent;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Card 
{
	private static final int DECKSIZE = 52;
	private static final char ranks[] = { '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A' };
    private static final char suits[] = { 'c', 'd', 'h', 's' };
    
    public int rank;
    public int suit;
    
	public Card(int r, int s)
	{
		rank = r;
		suit = s;
	}
	
	public Card(String card)
	{
		for (int i = 0; i < ranks.length; i++)
		{
			if (ranks[i] == card.charAt(0))
			{	
				rank = i;
				break;
			}
		}
		
		for (int i = 0; i < suits.length; i++)
		{
			if (suits[i] == card.charAt(1))
			{
				suit = i;
				break;
			}
		}
	}
	
	public static List<Card> getAllCards()
	{
		List<Card> deck = new ArrayList<Card>(DECKSIZE);

        for (int i = 0; i < ranks.length; i++)
        {
        	for (int j = 0; j < suits.length; j++)
        		deck.add(new Card(i, j));
        }
        
        return deck;
    }
	
	public static List<Card> dealNewArray(SecureRandom random, int numCardsToDeal)
	{
		List<Card> deck = getAllCards();
		
        for(int i = 0; i < numCardsToDeal; i++)
        {
            int toSwap = random.nextInt(DECKSIZE-i)+i;
            
            Card temp = deck.get(i);
            
            deck.add(i, deck.get(toSwap));
            deck.remove(i+1);
            
            deck.add(toSwap, temp);
            deck.remove(toSwap+1);
        }
        
        return deck;
    }
	
	public static String arrayToString(List<Card> cards)
	{
    	String result = "";
    	
    	for (Card c : cards)
    		result += "" + ranks[c.rank] + suits[c.suit];
    	
    	return result;
    }
	
	public String toString()
	{
		return "" + ranks[this.rank] + suits[this.suit];
	}
}

