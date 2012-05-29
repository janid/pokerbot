package si.feri.is.poker;

/*
 * RandomPokerClient.java
 *
 * Created on April 19, 2006, 2:04 PM
 */

import java.io.*;
import java.net.*;
import java.security.*;

public class NoLimitRandomPokerClient extends PokerClient {
    SecureRandom random;
    
    /**
     * Handles all received messages from the server and 
     * sends the decided response 
     * @throws IOException
     * @throws SocketException      
     */
    @Override public void handleStateChange() throws IOException, SocketException{
       if (random.nextDouble() > 0.33)
       {
           sendFold();
       }
       else if (random.nextDouble() > 0.66)
       {
           sendCall();
       }
       else 
       {
           sendRaise(random.nextInt(20000));
       }
    }
    
    /** 
     * Creates a new instance of RandomPokerClient 
     */
    public NoLimitRandomPokerClient(){
      super();
      random = new SecureRandom();  
    }
    
    /**
     * @param args the command line parameters (IP and port)
     */
    public static void main(String[] args) throws Exception{
        NoLimitRandomPokerClient rpc = new NoLimitRandomPokerClient();
        System.out.println("Attempting to connect to ...");

        rpc.connect(InetAddress.getByName(Defs.SERVER_ADDRESS), Defs.SERVER_PORT1);
        System.out.println("Successful connection!");
        rpc.run();
    }
    
}
