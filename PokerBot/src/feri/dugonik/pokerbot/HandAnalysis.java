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
		
		/*for (int i = 0; i < karte.size(); i++)
			System.out.println(karte.get(i).toString());*/
		
		System.out.println("Bubblesort:");
		BubbleSortNarascajoce(karte);
		
		/*for (int i = 0; i < karte.size(); i++)
			System.out.println(karte.get(i).toString());*/
		
		// royal flush
		int barva = 0;
		
		// preverimo barvo
		for (int i = 1; i < karte.size(); i++)
		{
			if ((karte.get(i-1).suit) == (karte.get(i).suit))
				barva++;
			else
				break;
		}
		// preverimo ranke
		if (barva == 4)
		{
			// sortiraj po ranku!
			
		}
		
		
		return 0;
	}
	
	private static void BubbleSortNarascajoce(List<Card> karte)
	{
		//for (int i = 0; i < karte.size() - 1; i++)
		//{
			for (int j = 0; j < karte.size() - 1; j++)
			{
				System.out.println("prva karta:" + karte.get(j).rank);
				System.out.println("druga karta:" + karte.get(j+1).rank);
				
				if (karte.get(j).rank > karte.get(j+1).rank)
				{
					System.out.println("zamenjamo!");
					Card tmp = karte.get(j);
					
					//karte.add(j, karte.get(j+1));
					//karte.remove(j);
				}
			}
		//}
			
			for (int i = 0; i < karte.size(); i++)
				System.out.print(karte.get(i));
			System.out.println("\n");
	}
	
	public static void HandStrength(List<Card> mojeKarte, List<Card> miza)
	{
		int rank = Rank(mojeKarte, miza);
		
		System.out.println("rank: " + rank);
	}
	
	
}