package feri.dugonik.pokerbot;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		/*int index;
		SecureRandom random = new SecureRandom();
		
		// ustvarimo naključn deck
		List<Card> deck = Card.dealNewArray(random, 52);
		
		for (int i = 0; i < deck.size(); i++)
		{
			Card karta = deck.get(i);
			System.out.println("rank: " + karta.rank + " suit: " + karta.suit);
		}
		
		// dobimo 2 karti iz decka
		List<Card> mojeKarte = new ArrayList<Card>(2);
		
		// prva karta
		index = random.nextInt(deck.size());
		mojeKarte.add(deck.get(index));
		deck.remove(index);
		
		// druga karta
		index = random.nextInt(deck.size());
		mojeKarte.add(deck.get(index));
		deck.remove(index);
		
		System.out.println("\n\nmoje karte: ");
		for (int i = 0; i < 2; i++)
			System.out.println(Card.toString(mojeKarte.get(i)));
		
		// na mizo damo 3 karte iz decka
		List<Card> miza = new ArrayList<Card>(5);
		
		// prva karta
		index = random.nextInt(deck.size());
		miza.add(deck.get(index));
		deck.remove(index);
		
		// druga karta
		index = random.nextInt(deck.size());
		miza.add(deck.get(index));
		deck.remove(index);
		
		// tretja karta
		index = random.nextInt(deck.size());
		miza.add(deck.get(index));
		deck.remove(index);
		
		System.out.println("\n\nkarte na mizi: ");
		for (int i = 0; i < miza.size(); i++)
			System.out.println(Card.toString(miza.get(i)));*/
		
		// hand strength
		List<Card> mojeKarte = new ArrayList<Card>(2);
		List<Card> miza = new ArrayList<Card>(5);
		
		Card karta = new Card("2c");
		mojeKarte.add(karta);
		karta = new Card("3c");
		mojeKarte.add(karta);
		System.out.println("moje karte: " + Card.arrayToString(mojeKarte));
		
		karta = new Card("6c");
		miza.add(karta);
		karta = new Card("4c");
		miza.add(karta);
		karta = new Card("5c");
		miza.add(karta);
		System.out.println("miza: " + Card.arrayToString(miza));
		
		HandAnalysis.HandStrength(mojeKarte, miza);
		
		// hand potential
		
		// testiranje izpisov
		/*Card k = new Card("Qs");
		System.out.println("\n\n" + k.rank + "," + k.suit + "\n" + Card.toString(k));
		
		System.out.println(Card.arrayToString(miza));
		System.out.println(Card.arrayToString(mojeKarte));*/
	}
	
	
	
	
	
	/*psevodkod:
	HandStrength(ourcards, boardcards)
	{
		ahead = tied = behind = 0
		ourrank = Rank(ourcards, boardcards)
		
		// za vse kombinacije dveh kart izmed kart, ki so nam ostale
		foreach oppcards
		{
			opprank = Rank(oppcards, boardcards)
			if (ourrank > opprank) ahead += 1
			else if (ourrank == opprank) tied += 1
			else behind += 1
		}
		
		handstrength = (ahead+tied/2) / (ahead+tied+behind)
		return handstrength
	}*/
	
	//psevdokod:
	/*HandPotential(ourcards, boardcards)
	{
		// vsak indeks predstavlja ahead, tied, behind
		int HP[3][3]
		int HPTotal[3][3]
				
		ourrank = Rank(ourcards, boardcards)		
		
		// za vse kombinacije dveh kart izmed kart, ki so nam ostale
		foreach oppcards
		{
			opprank = Rank(oppcards, boardcards)
			if (ourrank > opprank) index = ahead
			else if (ourrank == opprank) index = tied
			else index = behind
			
			HPTotal[index] += 1
			
			// vse možne karte, ki lahko pridejo na mizo
			foreach turn
			{
				foreach river
				{	// final 5-card board
					board = [boardcards, turn, river]
					ourbest = Rank(ourcards, board)
					oppbest = Rank(oppcards, board)
					if (ourbest > oppbest)	HP[index][ahead] += 1
					else if (ourbest == oppbest) HP[index][tied] += 1
					else HP[index][behind] += 1
				}
			}
		}
		
		// smo bli behind ampak smo se premaknili ahead
		PPot = (HP[behind][ahead] + HP[behind][tied]/2 + HP[tied][ahead]/2) /(HPTotal[behind] + HPTotal[tied]/2)
		
		// smo bli ahead ampak smo padli behind
		NPot = (HP[ahead][behind] + HP[tied][behind]/2 + HP[ahead][tied]/2) / (HPTotal[ahead]+HPTotal[tied]/2)
		
		return (PPot, NPot)
	}*/
}