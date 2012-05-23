package feri.dugonik.pokerbot;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Card 
{
	private static final int DECKSIZE = 52;
	private static final String ranks[] = { "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A" };
    private static final String suits[] = { "c", "d", "h", "s" };
    
    public int rank;
    public int suit;
    
	public Card(int r, int s)
	{
		rank = r;
		suit = s;
	}
	
	public Card(String cards)
	{
		
	}
	
	public static Card[] getAllCards()
	{
        Card[] deck = new Card[DECKSIZE];
        int index = 0;

        for (int i = 0; i < ranks.length; i++)
        {
        	for (int j = 0; j < suits.length; j++)
        		deck[index++] = new Card(i, j);
        }
        
        return deck;
    }
	
	public static List<Card> dealNewArray(SecureRandom random, int numCardsToDeal)
	{
        Card[] deck = getAllCards();
        
        for(int i = 0; i < numCardsToDeal; i++)
        {
            int toSwap = random.nextInt(DECKSIZE-i)+i;
            Card temp = deck[i];
            deck[i] = deck[toSwap];
            deck[toSwap] = temp;
        }
        
        List<Card> result = new ArrayList<Card>(numCardsToDeal);
        
        for(int i = 0; i < numCardsToDeal; i++)
        	result.add(deck[i]);
        
        return result;
    }
	
	public static String toString(Card karta)
	{
		return ranks[karta.rank] + suits[karta.suit];
	}
}
