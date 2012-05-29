package si.feri.is.poker.agent;

import java.util.ArrayList;
import java.util.List;

public class HandAnalysis 
{
	/**
     * method return action
     * @param hand all info about hand
     * @return return action c, f, rXX
     */
    public static Action getHandAction(HandStatus hand) {
         
        //tak dobis vse podatke:
         
        // pozicija za mizo
        //boolean semDelilec = hand.amDealer();
     
        //karte
        List<Card> flopKarte = hand.getFlopCards(); //lahko je prazno
        Card turnCard = hand.getTurnCard();
        Card riverCard = hand.getRiverCard();
         
        //denar
        Integer mojVlozek = hand.getMyBet();
        Integer nasprotnikovVlozek = hand.getOponentsBet();
         
        //akcije
        //List<List<Action>> akcijePoVrsti = hand.getActions();
        List<List<Action>> akcijePoVrsti = hand.getActions();
         
 
         
        //TODO: Jani vrne nekaj od tega spodaj!
//      return new Action(true);    // CALL
//      return new Action(false);   // FOLD
//      return new Action(1000);    // RAISE
         
        return null;
    }
    
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
		}
		
		// preverimo ranke
		if (barva >= 4)
		{
			int rank = 0;
			for (int i = 1; i < karte.size(); i++)
			{
				if (karte.get(i-1).rank == karte.get(i).rank+1)
					rank++;
				else if (i > 2)
					break;
			}
			
			if (rank >= 4)
			{
				// ROYAL FLUSH
				if (karte.get(0).rank == 12)
				{
					//System.out.println("Royal flush");
					return 10;
				}
				else // STRAIGHT FLUSH
				{
					//System.out.println("Straight flush");
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
				//System.out.println("FOUR OF A KIND");
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
			//System.out.println("FULL HOUSE");
			return 7;
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
			//System.out.println("Flush");
			return 6;
		}
		
		// STRAIGHT
		int rank = 0;
		for (int i = 1; i < karte.size(); i++)
		{
			if (karte.get(i-1).rank == karte.get(i).rank+1)
				rank++;
			else if (i > 2)
				break;
		}
		
		if (rank >= 4)
		{
			//System.out.println("Straight");
			return 5;
		}
		
		// three of a kind
		stevec3 = 0;
		for (int i = 0; i < stevecKart.size(); i++)
		{
			if (stevecKart.get(i) == 3)
			{
				stevec3++;
			}
		}
		if (stevec3 >= 1)
		{
			//System.out.println("THREE OF A KIND");
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
			//System.out.println("TWO PAIR");
			return 3;
		}
		else if (stevec == 1)
		{
			//System.out.println("ONE PAIR");
			return 2;
		}
		
		// HIGH CARD
		//System.out.println("High card " + karte.get(0));
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
	
	private static void RemoveCards(Card karta, List<Card> deck)
	{
		for (int j = 0; j < deck.size(); j++)
		{
			if (deck.get(j).rank == karta.rank && deck.get(j).suit == karta.suit)
			{
				deck.remove(j);
				break;
			}
		}
	}
	
	// 0 - jaz
	// 1 - izenačeno
	// 2 - nasprotnik
	public static int GetWinner(List<Card> mojeKarte, List<Card> nasprotnikoveKarte, List<Card> miza)
	{
		int ourrank = Rank(mojeKarte, miza);
		int opprank = Rank(nasprotnikoveKarte, miza);
		
		System.out.println("moj rank: " + ourrank);
		System.out.println("nasprotnikov rank: " + opprank);
		
		if (ourrank > opprank)
			return 0;
		else if (ourrank == opprank)
			return 1;
		else 
			return 2;
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
			
			//System.out.println(ourrank + ":" + opprank);
		}
		
		//System.out.println("\nmoč: " + ((double)(ahead+tied/2) / (double)(ahead+tied+behind))*100);
		
		return ((double)(ahead+tied/2) / (double)(ahead+tied+behind)) * 100;
	}

	public static double[] HandPotential(List<Card> mojeKarte, List<Card> miza)
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
			
			// turn
			for (int j = 0; j < noviDeck.size(); j++)
			{
				Card turn = noviDeck.get(j);
				noviDeck.remove(j);
				
				// river
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
		
		//System.out.println("PPot: " + PPot + "\nNPot: " + NPot);
		double rez[] = new double[2];
		rez[0] = PPot;
		rez[1] = NPot;
		
		return rez;
	}
	
	public static double[] HandPotential(List<Card> mojeKarte, List<Card> miza, Card turn)
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
			RemoveCards(turn, noviDeck);
			
			// river
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
		}
		
		// positive potencial - možnost da se nam možnosti za zmago poboljšajo
		double PPot = (double)(HP[behind][ahead] + HP[behind][tied]/2 + HP[tied][ahead]/2) / (double)(HPTotal[behind]+ HPTotal[tied]/2);
		
		// negative potencial - možnost da se nam možnosti za zmago poslabšajo
		double NPot = (double)(HP[ahead][behind] + HP[tied][behind]/2 + HP[ahead][tied]/2) / (double)(HPTotal[ahead] + HPTotal[tied]/2);
		
		//System.out.println("PPot: " + PPot + "\nNPot: " + NPot);
		double rez[] = new double[2];
		rez[0] = PPot;
		rez[1] = NPot;
		
		return rez;
	}	
}

