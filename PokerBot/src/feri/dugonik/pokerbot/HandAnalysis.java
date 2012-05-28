package feri.dugonik.pokerbot;

import java.util.ArrayList;
import java.util.List;

public class HandAnalysis 
{
	
	private static int Rank(List<Card> ourcards, List<Card> boardcards)
	{
		List<Card> karte = new ArrayList<Card>(5);
		
		for (int i = 0; i < ourcards.size(); i++)
			karte.add(ourcards.get(i));
		
		for (int i = 0; i < boardcards.size(); i++)
			karte.add(boardcards.get(i));

		// sortiraj po ranku!
		BubbleSort(karte);
		
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
				if (karte.get(i-1).rank > karte.get(i).rank)
					rank++;
				else
					break;
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
		int iste = 0;
		for (int i = 1; i < karte.size(); i++)
		{
			if (karte.get(i-1).rank == karte.get(i).rank)
				iste++;
		}
		
		if (iste == 3)
		{
			System.out.println("Four of a kind");
			return 8;
		}
		
		// FULL HOUSE - popravi!
		// return 7;
		
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
			if (karte.get(i-1).rank > karte.get(i).rank)
				rank++;
			else
				break;
		}
		
		if (rank >= 4)
		{
			System.out.println("Straight");
			return 5;
		}
		
		// THREE OF A KIND - popravi!
		iste = 0;
		for (int i = 1; i < karte.size(); i++)
		{
			if (karte.get(i-1).rank == karte.get(i).rank)
				iste++;
		}
		
		if (iste == 2)
		{
			System.out.println("Three of a kind");
			return 4;
		}
		
		
		// TWO PAIR - popravi!
		// return 3;
		
		// ONE PAIR - popravi!
		// return 2;
		
		// HIGH CARD
		System.out.println("High card");
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
		
		/*for (int i = 0; i < karte.size(); i++)
			System.out.print(karte.get(i));
		System.out.println("\n");*/
	}
	
	public static void HandStrength(List<Card> mojeKarte, List<Card> miza)
	{
		int rank = Rank(mojeKarte, miza);
		
		System.out.println("rank: " + rank);
	}
	
	
}
