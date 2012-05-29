package si.feri.is.poker.agent;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.security.SecureRandom;

import si.feri.is.poker.Defs;
import si.feri.is.poker.PokerClient;

public class NoLimitPokerClient extends PokerClient {

	@SuppressWarnings("unused")
	private HandStatus handStatus;

	SecureRandom random = new SecureRandom();

	/**
	 * Creates a new instance of RandomPokerClient
	 */
	public NoLimitPokerClient() {
		super();
	}

	/**
	 * Handles all received messages from the server and sends the decided
	 * response
	 * @throws IOException
	 * @throws SocketException
	 */
	@Override
	public void handleStateChange() throws IOException, SocketException {

		try {
			
			PokerServerMessageParser parser = new PokerServerMessageParser(currentGameStateString);
			handStatus = parser.getHandStatus();
			
			

			/*if (random.nextDouble() > 0.33) {
				sendFold();
			} else if (random.nextDouble() > 0.66) {
				sendCall();
			} else {
				sendRaise(random.nextInt(20000));
			}*/

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * @param args
	 *            --the command line parameters (IP and port)
	 */
	public static void main(String[] args) throws Exception {
		NoLimitPokerClient mpc = new NoLimitPokerClient();
		System.out.println("Attempting to connect to ...");

		mpc.connect(InetAddress.getByName(Defs.SERVER_ADDRESS), Defs.SERVER_PORT2);
		System.out.println("Successful connection!");
		mpc.run();

	}

}
