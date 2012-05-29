package si.feri.is.poker;

/*
 * PokerClient.java
 *
 * Created on April 18, 2006, 10:26 PM
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * Root for all example Java implementations of the client interface. Basic
 * functionality: sends a version statement, receives state messages and
 * remembers them, can send replies (actions)
 * 
 * The function to overload is handleStateChange(). sendRaise(), sendCall(), and
 * sendFold() can be used to send actions to the server.
 * 
 * currentGameStateString has the most recent state information.
 * 
 * @author Martin Zinkevich
 */
public class PokerClient implements Runnable {
	/**
	 * Socket connecting to the server.
	 */
	Socket socket;
	/**
	 * Stream from the server.
	 */
	InputStream is;
	/**
	 * Stream to the server.
	 */
	OutputStream os;
	/**
	 * Has an ENDGAME signal been received?
	 */
	boolean matchOver;

	/**
	 * Whether the client is verbose (prints messages sent and received to
	 * stdout).
	 */
	boolean verbose = true;

	/**
	 * Sets the verbose flag (if true, prints messages sent and received to
	 * stdout).
	 */
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	/**
	 * Returns the IP address and port number of the client.
	 */
	public String getClientID() {
		return "" + socket.getLocalAddress() + ":" + socket.getLocalPort();
	}

	/**
	 * Print a message to stdout if verbose==true.
	 */
	public void showVerbose(String message) {
		if (verbose) {
			System.out.println(message);
		}
	}

	/**
	 * This is the current game state. It is not changed during a call to
	 * handleStateChange()
	 */
	public String currentGameStateString;

	/**
	 * Override to handle a state change. Observe that a state change does NOT
	 * imply it is your turn.
	 */
	public void handleStateChange() throws IOException, SocketException {
		throw new RuntimeException("Not implemented");
	}

	/**
	 * Creates a new instance of PokerClient. Need to connect(), then run() to
	 * start process.
	 */
	public PokerClient() {
		verbose = true;
	}

	/**
	 * Connects to the server at the given IP address and port number.
	 */
	public void connect(InetAddress iaddr, int port) throws IOException, SocketException {
		socket = new Socket(iaddr, port);
		is = socket.getInputStream();
		os = socket.getOutputStream();
		matchOver = false;
		sendMessage("VERSION:2.0.0");
	}

	/**
	 * Send an action (action should be r, c, or f). Usually called during
	 * handleStateChange. Action will be in response to the state in
	 * currentGameStateString.
	 */
	public void sendAction(char action) throws IOException, SocketException {
		sendAction("" + action);
	}

	/**
	 * Send an action string (action should be r??, c, or f, where ?? is the
	 * final amount in the pot from a player in chips). Usually called during
	 * handleStateChange. Action will be in response to the state in
	 * currentGameStateString.
	 */
	public void sendAction(String action) throws IOException, SocketException {

		sendMessage(currentGameStateString + ":" + action);

	}

	/**
	 * send a raise action.
	 */
	public void sendRaise() throws IOException, SocketException {
		sendAction('r');
	}

	/**
	 * send a raise action. The final in pot is the total YOU want to have put
	 * in the pot after the raise (ie including previous amounts from raises,
	 * calls, and blinds.
	 */
	public void sendRaise(int finalInPot) throws IOException, SocketException {
		sendAction("r" + finalInPot);
	}

	/**
	 * send a call action.
	 */
	public void sendCall() throws IOException, SocketException {
		sendAction('c');
	}

	/**
	 * send a fold action.
	 */
	public void sendFold() throws IOException, SocketException {
		sendAction('f');
	}

	/**
	 * Start the client. Should call connect() before running.
	 */
	public void run() {
		try {
			while (true) {
				String message = receiveMessage();
				if (message.startsWith("MATCHSTATE:")) {
					currentGameStateString = message;
					handleStateChange();
				}
				if (message.contains("" + ((char) 26))) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
			System.exit(0);
		} finally {
			try {
				close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Close the connection. Called in response to an ENDGAME message from the
	 * server.
	 */
	public synchronized void close() throws IOException {
		matchOver = true;
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} finally {
			os.close();
			is.close();
			socket.close();
		}
	}

	/**
	 * Receive a message from the server. Removes a message terminator (period).
	 */
	public String receiveMessage() throws SocketException, IOException {
		String response = "";
		do {
			int c = is.read();
			// char c = (char)(is.read());
			// System.out.println("READ:"+(int)c);
			if (c != -1) {
				response = response + (char) c;
			} else {
				response = response + ((char) 26) + ((char) 13) + ((char) 10);
			}

		} while (!isComplete(response));
		response = response.substring(0, response.length() - ("" + ((char) 13) + ((char) 10)).length());
		showVerbose("CLIENT RECEIVES:" + response);
		return response;
	}

	/**
	 * Test if the message is complete (contains a terminal character)
	 */
	public boolean isComplete(String result) {
		return result.contains("" + ((char) 13) + ((char) 10));
	}

	/**
	 * Send a message to the server. Appends a message terminator (period).
	 */
	public synchronized void sendMessage(String message) throws SocketException, IOException {
		showVerbose("CLIENT SENDS:" + message);
		message = message + "" + ((char) 13) + ((char) 10);
		byte[] messageData = message.getBytes();
		if (!matchOver) {
			os.write(messageData);
		}
	}
}
