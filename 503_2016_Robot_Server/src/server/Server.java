package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import sendRecieve.*;

/**
 * Server program that will live in the offboard processor on the robot rio. Accepts and sends messages to clients.
 * Protocol will be specified in documentation but just for reference should also be listed up her
 * 
 * Protocol:
 * Data Sent in form "ID" : "data"
 * Input is split to give ID value and data.
 * 
 * Switch(ID) {
 * Case ID: 
 * New case for each type of data
 * Interpret and do something with data
 * }
 * 
 * Separate file with all IDs and meanings for reference
 */
public class Server {
	
	private static final int PORT = 9898;
	
    /**
     * Application method to run the server runs in an infinite loop
     * listening a port.  When a connection is requested, it
     * spawns a new thread to do the servicing and immediately returns
     * to listening.  Server creates a new Client number for each client probably can be modified for client name.
     */
    public static void main(String[] args) throws IOException {
    	ClientHandler.log("The server is running");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true) { 
                new ClientHandler(listener.accept(), clientNumber++).start();;
            }
        }
        finally {
            listener.close();
        }
    }

    /**
     * Private thread that is spawned every time a new client joins the server.
     * 
     */
    private static class ClientHandler extends Thread {
    	
    	//Identification information about the client
        private Socket socket;
        private int clientNumber;
        private BufferedReader in;
       // private RecieveData in;
        private PrintWriter out;

        public ClientHandler(Socket socket, int clientNumber) {
            this.socket =  socket;
            this.clientNumber = clientNumber;
            log("New connection with client " + clientNumber + " at " + socket);
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
            	//in = new RecieveData(socket);
                out = new PrintWriter(socket.getOutputStream(), true);
                Scanner scan = new Scanner(System.in);
                
                //boolean done = false;
               /* while(true) {
                	in.start();
                }*/
                // Send a welcome message to the client.
                out.println("Connection with client " + clientNumber + " at " + socket + " complete.");

                boolean done = false;                
                String input = "",  output = "", identifier="";
                String[] tokens;
           
                // Get messages from the client enter switch statement to decide what to do
                while(!done) {
                	out.println(scan.nextLine());
                	input = in.readLine();
                    if(input != null){ 
                    	log(input);

                        //Handles all inputs based off of protocol.
                    	//Finds identifier of data
                        tokens = input.toLowerCase().split(":");
                        identifier = tokens[0];
                        
                    	switch (identifier){
                        	case "exit":
                        		done = true;
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
                log("Connection with client " + clientNumber + " closed");
            }
        }

        private static void log(String message) {
            System.out.println(message);
        }
    }
}