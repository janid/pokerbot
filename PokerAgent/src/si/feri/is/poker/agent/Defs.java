package si.feri.is.poker.agent;

public class Defs {

	/**
	 * number of hands
	 */
	public static final int NUMBER_OF_HANDS = 10;
	
	
	/**
	 * money definitions
	 */	
	public static final int BANKROLL = 6000000;
	public static final int HAND_BANKROLL = 20000;
	public static final int SMALL_BLIND = 50;
	public static final int BIG_BLIND = 100;

	/**
	 * server definitions
	 */
	public static final String SERVER_ADDRESS = "kivi.shell.la";
	public static final int SERVER_PORT1 = 18791;
	public static final int SERVER_PORT2 = 18374;
	
	
	public static final String ACTION_REGEX = "r[\\d]*|c|f";
	public static final String NEWLINE = System.getProperty("line.separator");

}
