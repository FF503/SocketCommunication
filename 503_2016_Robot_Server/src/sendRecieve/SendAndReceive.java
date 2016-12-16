package sendRecieve;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SendAndReceive extends Thread {
	private static Socket socket;
	private static BufferedReader in;
	private static PrintWriter out;
	private static Scanner scan;
	private static boolean loopDone;
	
	public SendAndReceive(Socket socket) {
		this.socket = socket;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()), true);
			scan = new Scanner(System.in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    	   
		loopDone = false;
	}
	
	public boolean getDone(){
		return loopDone;
	}
	
	public Runnable receive = new Runnable(){
		String[] tokens;
		String identifier, input = null;
		@Override
		public void run() {
	       try {
	    	   while(!loopDone) {
	        	   input = in.readLine();
	               if(input != null){ 
	                   log(input);
	                   //Handles all inputs based off of protocol.
	                   //Finds identifier of data
	                   tokens = input.toLowerCase().split(":");
	                   identifier = tokens[0];
	                   
	               	   switch (identifier){
	               	   		case "exit":
	               	   			loopDone=true;
	                   			break;
	               	   		case "0" :
	                   			log(tokens[1]);
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
	};

	public Runnable send = new Runnable(){
		@Override
		public void run(){
			while(!loopDone){
				sendData(scan.nextLine());
			}
		}
	};
	
	public void sendData(Object data){
		out.println(data);
	}
	
	public String receiveData() throws IOException{
		return in.readLine();
	}
   private static void log(String message) {
       System.out.println(message);
   }
	
	
}
