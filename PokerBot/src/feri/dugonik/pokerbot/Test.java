package feri.dugonik.pokerbot;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException 
	{
		int index;
		SecureRandom random = new SecureRandom();
		DecimalFormat df = new DecimalFormat("#.##");
		double moc;
		double potencial[] = new double[2];
		
		// ustvarimo naključn deck
		List<Card> deck = Card.dealNewArray(random, 52);
			
		// dobimo 2 karti iz decka
		List<Card> mojeKarte = new ArrayList<Card>(2);
		List<Card> nasprotnikoveKarte = new ArrayList<Card>(2);
		
		// na mizo damo 3 karte iz decka
		List<Card> miza = new ArrayList<Card>(5);
				
		// moja prva karta
		index = random.nextInt(deck.size());
		mojeKarte.add(deck.get(index));
		deck.remove(index);
		
		// nasprotnikova prva karta
		index = random.nextInt(deck.size());
		nasprotnikoveKarte.add(deck.get(index));
		deck.remove(index);
		
		// moja druga karta
		index = random.nextInt(deck.size());
		mojeKarte.add(deck.get(index));
		deck.remove(index);

		// nasprotnikova druga karta
		index = random.nextInt(deck.size());
		nasprotnikoveKarte.add(deck.get(index));
		deck.remove(index);
		
		System.out.print("moje karte: ");
		for (int i = 0; i < 2; i++)
			System.out.print(mojeKarte.get(i).toString() + " ");
		
		// moč kart
		moc = HandAnalysis.HandStrength(mojeKarte, miza);
		System.out.println("\n\nMoč: " + df.format(moc));

		System.out.println("\nFLOP");
		//System.out.println("\n\nPritisni tipko za flop..");
		//System.in.read();
		
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
		
		System.out.println("karte na mizi: ");
		for (int i = 0; i < miza.size(); i++)
			System.out.print(miza.get(i).toString() + " ");
	
		// moč kart
		moc = HandAnalysis.HandStrength(mojeKarte, miza);
		System.out.println("\n\nMoč: " + df.format(moc));
		
		// potencial kart
		potencial = HandAnalysis.HandPotential(mojeKarte, miza);
		System.out.println("Pozitivni potencial: " + df.format(potencial[0]));
		System.out.println("Negativni potencial: " + df.format(potencial[1]));
		
		System.out.println("\nTURN");
		//System.out.println("\n\nPritisni tipko za turn..");
		//System.in.read();
		
		// turn
		index = random.nextInt(deck.size());
		miza.add(deck.get(index));
		System.out.println(deck.get(index).toString());
		deck.remove(index);
		
		// moč kart
		moc = HandAnalysis.HandStrength(mojeKarte, miza);
		System.out.println("\n\nMoč: " + df.format(moc));
		
		// potencial kart
		potencial = HandAnalysis.HandPotential(mojeKarte, miza, miza.get(miza.size()-1));
		System.out.println("Pozitivni potencial: " + df.format(potencial[0]));
		System.out.println("Negativni potencial: " + df.format(potencial[1]));
		
		System.out.println("\nRIVER");
		//System.out.println("\nPritisni tipko za river..");
		//System.in.read();
		
		// river
		index = random.nextInt(deck.size());
		miza.add(deck.get(index));
		System.out.println(deck.get(index).toString());
		deck.remove(index);
		
		// moč kart
		moc = HandAnalysis.HandStrength(mojeKarte, miza);
		System.out.println("\nMoč: " + df.format(moc));
		
		System.out.println("\nKONEC");
		//System.out.println("\nPritisni tipko za konec..");
		//System.in.read();
		
		// izpis mojih kart
		System.out.print("moje karte: ");
		for (int i = 0; i < 2; i++)
			System.out.print(mojeKarte.get(i).toString());
		System.out.println();

		// izpis mojih kart
		System.out.print("nasprotnikove karte: ");
		for (int i = 0; i < 2; i++)
			System.out.print(nasprotnikoveKarte.get(i).toString());
		System.out.println();
		
		int zmagovalec = HandAnalysis.GetWinner(mojeKarte, nasprotnikoveKarte, miza);
		
		System.out.print("Miza: ");
		for (int i = 0; i < miza.size(); i++)
			System.out.print(miza.get(i).toString() + " ");
		System.out.println();
		
		switch (zmagovalec)
		{
			case 0:
			{
				System.out.println("Zmagovalec je igralec!");
				break;
			}
			case 1:
			{
				System.out.println("Izenačeno!");
				break;
			}
			case 2:
			{
				System.out.println("Zmagovalec je nasprotnik!");
				break;
			}
		}
		
		System.out.println("\nKonec!");
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