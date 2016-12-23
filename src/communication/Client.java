package communication;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public abstract class Client extends Thread {
	
	private static int port;
	private static String address;
	protected static ArrayList<String> allData;
	protected static Socket socket;
	protected static SendAndReceive message;
	
	public Client(String address, int port){
		Client.port = port;
		Client.address = address;
		allData = new ArrayList<String>();
	}
	
    /**
     * Services each client by listening for data and sending back data when needed.
     */
	@Override
    public abstract void run();
	
	protected void connectToServer() throws IOException{
		socket = new Socket(address, port);
		message = new SendAndReceive(socket);
	}
	
	@Override
	public String toString(){
		return "Socket at port " + port + " and address at " + address;
	}
	
	protected static void log(String message) {
        System.out.println(message);
    }
	
}
