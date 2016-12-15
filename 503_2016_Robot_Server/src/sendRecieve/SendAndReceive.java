package sendRecieve;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SendAndReceive extends Thread {
	private Socket socket;
	private BufferedReader in;
	
	public SendAndReceive(Socket socket) {
		this.socket = socket;
	}
	@Override
	public void run() {
       try {
    	   in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    	   boolean done = false;
    	   
    	   String input = "";
    	   while(!done) {
               if(input != null){ 
            	   input = in.readLine();
                   log(input);
                   //Handles all inputs based off of protocol.
                   //Finds identifier of data
                   String[] brokenInput = input.toLowerCase().split(":");
                   String identifier = brokenInput[0];
                   
               	   switch (identifier){
               	   		case "exit":
               	   			done=true;
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
       finally {
           try {
				socket.close();
			} 
           catch (IOException e) {
				e.printStackTrace();
			}
       }
   }

   private static void log(String message) {
       System.out.println(message);
   }
	
	
}
