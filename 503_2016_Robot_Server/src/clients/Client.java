package clients;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client extends Thread {
	
	protected static BufferedReader in;
	private static PrintWriter out;
	private static int port;
	private static String address;
	protected static ArrayList<String> allData;
	protected static Socket socket;
	
	public Client(String address, int port){
		Client.port = port;
		Client.address = address;
	}
	
    /**
     * Services each client by listening for data and sending back data when needed.
     */
    @Override
    public void run() {
        try {
            // Convert the streams so we can send characters
            // and not just bytes.  Ensure output is flushed
            // after every newline.
        	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scan = new Scanner(System.in);
            
            // Send a welcome message to the client.
            out.println("Connection with server at " + socket);

            boolean done = false;                
            String input = "",  output = "";
       
            // Get messages from the client enter switch statement to decide what to do
            while(!done) {
            	//out.println(scan.nextLine());
                if(input != null){ 
                    input = in.readLine();
                	log(input);
                    //Handles all inputs based off of protocol.
                	//Finds identifier of data
                    String[] brokenInput = input.toLowerCase().split(":");
                    String identifier = brokenInput[0];
                    
                	switch (identifier){
                    	case "exit":
                    		done = true;
                    		break;
                    	case "0" :
                    		log(brokenInput[1]);
                    		break;
                    	default:
                    		break;                  
                	}
                }
            }
        }  
        catch (IOException e) {
        	e.printStackTrace();
        }
        /*finally {
            try {
				socket.close();
			} 
            catch (IOException e) {
				e.printStackTrace();
			}
            log("Connection with server closed");
        }*/
    }
	

	
	protected boolean connectToServer() throws IOException{
		socket = new Socket(address, port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()), true);
		return socket.isConnected();
	}
	
	protected void sendData(Object data) throws IOException {
		out.println(data);
	}
	
	protected ArrayList<String> receiveData() throws IOException {
		String line = null;
		ArrayList<String> data = new ArrayList<String>();
		boolean done = false;
		while((line = in.readLine()).equalsIgnoreCase("exit")){
			
		}
		allData.addAll(data);
		return data;
	}
	
	@Override
	public String toString(){
		return "Socket at port " + port + " and address at " + address;
	}
	
	protected static void log(String message) {
        System.out.println(message);
    }
	
}
