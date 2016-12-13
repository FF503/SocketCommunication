package server;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
    	System.out.println("The Server is Running");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true) { 
                new ClientHandler(listener.accept(), clientNumber++).start();
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
        private BufferedReader br;
        private DataInputStream in;
        private DataOutputStream out;
        //private PrintWriter out;

        public ClientHandler(Socket socket, int clientNumber) {
            this.socket =  socket;
            this.clientNumber = clientNumber;
            log("New connection with client " + clientNumber + " at " + socket);
        }

        /**
         * Services each client by listening for data and sending back data when needed.
         */
        public void run() {
            try {

                // Convert the streams so we can send characters
                // and not just bytes.  Ensure output is flushed
                // after every newline.
                //in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            	
            	in = new DataInputStream(socket.getInputStream());
               // out = new PrintWriter(socket.getOutputStream(), true);
            	out = new DataOutputStream(socket.getOutputStream());
                br = new BufferedReader(new InputStreamReader(System.in));
                
                // Send a welcome message to the client.
                log("Connection with client " + clientNumber + " at " + socket + " complete.");

                String input = "",  output = "";
    			
    			System.out.println("Enter \"Exit\" to Quit");

                while (!input.equals("Exit")) {
    				input = in.readUTF();
    				log(input); // print client message
    				output = br.readLine();
    				out.writeUTF(output);
    				out.flush();
    			}
                /*
                // Get messages from the client enter switch statement to decide what to do
                while (!input.equals("exit")) {
                    if (input != null){ 
                	//Handles all inputs based off of protocol.
                    	//Finds identifier of data
                        String[] brokenInput = input.split(":");
                        String identifier = brokenInput[0];
                        
                        //  Handles all inputs based off of protocol.
                        switch (identifier){
                        
                   
                        }
                        input = in.readLine();
                    }                    
                }*/
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

        //Dumbest method ever but too lazy to change it
        private void log(String message) {
            System.out.println(message);
        }
    }
}