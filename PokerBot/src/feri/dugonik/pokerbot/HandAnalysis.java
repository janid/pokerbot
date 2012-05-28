package feri.dugonik.pokerbot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HandAnalysis 
{
	
	public static int Rank(List<Card> ourcards, List<Card> boardcards)
	{
		List<Card> karte = new ArrayList<Card>(5);
		
		for (int i = 0; i < ourcards.size(); i++)
			karte.add(ourcards.get(i));
		
		for (int i = 0; i < boardcards.size(); i++)
			karte.add(boardcards.get(i));
		
		// sortiraj po ranku!
		BubbleSort(karte);
		
		//if (karte.size() > 0)
			//return (int)(Math.random() * 10);
		
		// ----- ROYAL in STRAIGHT FLUSH -------	
		// najprej preverimo barvo
		int barva = 0;
		for (int i = 1; i < karte.size(); i++)
		{
			if ((karte.get(i-1).suit) == (karte.get(i).suit))
				barva++;
			else
				break;
		}
		
		// preverimo ranke
		if (barva >= 4)
		{
			int rank = 0;
			for (int i = 1; i < karte.size(); i++)
			{
				if (karte.get(i-1).rank == karte.get(i).rank+1)
					rank++;
			}
			
			if (rank >= 4)
			{
				// ROYAL FLUSH
				if (karte.get(0).rank == 12)
				{
					System.out.println("Royal flush");
					return 10;
				}
				else // STRAIGHT FLUSH
				{
					System.out.println("Straight flush");
					return 9;
				}
			}
			
		}
		
		// FOUR OF A KIND - popravi!
		// return 8;
		List<Card> seznamKart = new ArrayList<Card>();
		List<Integer> stevecKart = new ArrayList<Integer>();
		boolean obstaja;
		int index;
		for (int i = 0; i < karte.size(); i++)
		{
			obstaja = false;
			index = -1;
			for (int j = 0; j < seznamKart.size(); j++)
			{
				if (seznamKart.get(j).rank == karte.get(i).rank)
				{
					obstaja = true;
					index = j;
					break;
				}
			}
			
			if (!obstaja)
			{
				seznamKart.add(karte.get(i));
				stevecKart.add(1);
			}
			else
			{
				// povečamo števec
				stevecKart.add(index, stevecKart.get(index) + 1);
				stevecKart.remove(index + 1);
			}
		}
		/*System.out.println("število kart: " + seznamKart.size());
		for (int i = 0; i < seznamKart.size(); i++)
			System.out.println(seznamKart.get(i) + ": " + stevecKart.get(i));*/

		for (int i = 0; i < stevecKart.size(); i++)
		{
			// four of a kind
			if (stevecKart.get(i) == 4)
			{
				System.out.println("FOUR OF A KIND");
				return 8;
			}
		}
		
		// full house
		int stevec2 = 0;
		int stevec3 = 0;
		for (int i = 0; i < stevecKart.size(); i++)
		{
			if (stevecKart.get(i) == 2)
			{
				stevec2++;
			}
			if (stevecKart.get(i) == 3)
			{
				stevec3++;
			}
		}
		if (stevec2 >= 1 && stevec3 >= 1)
		{
			System.out.println("FULL HOUSE");
			return 7;
		}
		else if (stevec3 >= 1)
		{
			System.out.println("THREE OF A KIND");
			return 4;
		}
		
		
		// two pair, one pair
		int stevec = 0;
		for (int i = 0; i < stevecKart.size(); i++)
		{
			if (stevecKart.get(i) == 2)
			{
				stevec++;
			}
		}
		if (stevec == 2)
		{
			System.out.println("TWO PAIR");
			return 3;
		}
		else if (stevec == 1)
		{
			System.out.println("ONE PAIR");
			return 2;
		}
		
		// FLUSH
		barva = 0;
		for (int i = 1; i < karte.size(); i++)
		{
			if ((karte.get(i-1).suit) == (karte.get(i).suit))
				barva++;
			else
				break;
		}
		
		if (barva >= 4)
		{
			System.out.println("Flush");
			return 6;
		}
		
		// STRAIGHT
		int rank = 0;
		for (int i = 1; i < karte.size(); i++)
		{
			if (karte.get(i-1).rank == karte.get(i).rank+1)
				rank++;
		}
		
		if (rank >= 4)
		{
			System.out.println("Straight");
			return 5;
		}
		
		// HIGH CARD
		System.out.println("High card " + karte.get(0));
		return 1;
	}
	
	private static void BubbleSort(List<Card> karte)
	{
		for (int i = 0; i < karte.size() - 1; i++)
		{
			for (int j = 0; j < karte.size() - 1; j++)
			{
				if (karte.get(j).rank < karte.get(j+1).rank)
				{
					Card tmp = karte.get(j);
				
					karte.add(j, karte.get(j+1));
					karte.remove(j+1);
					
					karte.add(j+1, tmp);
					karte.remove(j+2);
				}
			}
		}
	}
	
	private static void RemoveCards(List<Card> karte, List<Card> deck)
	{
		
		for (int i = 0; i < karte.size(); i++)
		{
			for (int j = 0; j < deck.size(); j++)
			{
				if (deck.get(j).rank == karte.get(i).rank && deck.get(j).suit == karte.get(i).suit)
				{
					deck.remove(j);
					break;
				}
			}
		}
	}
	
	public static double HandStrength(List<Card> mojeKarte, List<Card> miza)
	{
		int ahead = 0, tied = 0, behind = 0;
		int ourrank = Rank(mojeKarte, miza);
		
		List<Card> deck = Card.getAllCards();
		
		// odstranimo naše karte iz decka
		RemoveCards(mojeKarte, deck);
		RemoveCards(miza, deck);
		
		// za vse kombinacije dveh kart
		for (int i = 0; i < deck.size() - 1; i++)
		{
			List<Card> nasprotnikoveKarte = new ArrayList<Card>(2);
			nasprotnikoveKarte.add(new Card(deck.get(i).toString()));
			nasprotnikoveKarte.add(new Card(deck.get(i+1).toString()));
			
			int opprank = Rank(nasprotnikoveKarte, miza);
			
			if (ourrank > opprank)
				ahead += 1;
			else if (ourrank == opprank)
				tied += 1;
			else
				behind += 1;
			
			System.out.println(ourrank + ":" + opprank);
		}
		
		return ((double)(ahead+tied/2) / (double)(ahead+tied+behind));
	}

	public static void HandPotential(List<Card> mojeKarte, List<Card> miza)
	{
		int HP[][] = new int[3][3];
		int HPTotal[] = new int[3];
		
		int ahead = 0, tied = 1, behind = 2, index;
		int ourrank = Rank(mojeKarte, miza);
		
		List<Card> deck = Card.getAllCards();
		
		// odstranimo naše karte iz decka
		RemoveCards(mojeKarte, deck);
		RemoveCards(miza, deck);
		
		// za vse kombinacije dveh kart
		for (int i = 0; i < deck.size() - 1; i++)
		{
			List<Card> nasprotnikoveKarte = new ArrayList<Card>(2);
			nasprotnikoveKarte.add(new Card(deck.get(i).toString()));
			nasprotnikoveKarte.add(new Card(deck.get(i+1).toString()));
			
			int opprank = Rank(nasprotnikoveKarte, miza);
		
			if (ourrank > opprank)
				index = ahead;
			else if (ourrank == opprank)
				index = tied;
			else
				index = behind;
			
			HPTotal[index] += 3;
			
			// skopiramo list v novi list
			List<Card> noviDeck = new ArrayList<Card>(deck.size());
			for (int j = 0; j < deck.size(); j++)
				noviDeck.add(deck.get(j));
				
			RemoveCards(nasprotnikoveKarte, noviDeck);
			
			for (int j = 0; j < noviDeck.size(); j++)
			{
				Card turn = noviDeck.get(j);
				noviDeck.remove(j);
				
				for (int k = 0; k < noviDeck.size(); k++)
				{
					Card river = noviDeck.get(k);
					
					List<Card> board = new ArrayList<Card>(5);
					
					// dodamo karte z mize
					for (int l = 0; l < miza.size(); l++)
						board.add(miza.get(l));
					
					board.add(turn);
					board.add(river);
					
					//System.out.print("ourbest: ");
					int ourbest = Rank(mojeKarte, board);
					//System.out.print("\noppbest: ");
					int oppbest = Rank(nasprotnikoveKarte, board);
					//System.out.println();
					
					if (ourbest > oppbest)
						HP[index][ahead] += 1;
					else if (ourbest == oppbest)
						HP[index][tied] += 1;
					else
						HP[index][behind] += 1;
				}
				
				noviDeck.add(j, turn);
			}
		}
		
		// positive potencial - možnost da se nam možnosti za zmago poboljšajo
		double PPot = (double)(HP[behind][ahead] + HP[behind][tied]/2 + HP[tied][ahead]/2) / (double)(HPTotal[behind]+ HPTotal[tied]/2);
		
		// negative potencial - možnost da se nam možnosti za zmago poslabšajo
		double NPot = (double)(HP[ahead][behind] + HP[tied][behind]/2 + HP[ahead][tied]/2) / (double)(HPTotal[ahead] + HPTotal[tied]/2);
		
		System.out.println("PPot: " + PPot + "\nNPot: " + NPot);
	}
}
