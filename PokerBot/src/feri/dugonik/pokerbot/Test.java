package feri.dugonik.pokerbot;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{

		/*
		 * 
		 * after the flop - trije koraki:
		 * 1. hand strength, hand potential
		 * 2. pravila za stavljanje
		 * 3. random številka med 0 in 1 in jo uporabimo da izberemo fold, call, raise
		 * 
		 */
		
		
		
		

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