package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

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
        catch(Exception e){
        	e.printStackTrace();
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
        private static SendAndReceive message;

        public ClientHandler(Socket socket, int clientNumber) {
            this.socket =  socket;
            this.clientNumber = clientNumber;
            message = new SendAndReceive(socket);
            log("New connection with client " + clientNumber + " at " + socket);
        }

        /**
         * Services each client by listening for data and sending back data when needed.
         */
        @Override
        public void run() {
        	
            // Convert the streams so we can send characters
            // and not just bytes.  Ensure output is flushed
            // after every newline.
            
            // Send a welcome message to the client.
            message.sendData("Connection with client " + clientNumber + " at " + socket + " complete.");

            (new Thread(message.send)).start();
            (new Thread(message.receive)).start();
            while(!message.getDone()){}
            log("Connection with client " + clientNumber + " closed");
            try {
				socket.close();
			} 
            catch (IOException e) {
				e.printStackTrace();
			}
        }

        private static void log(String message) {
            System.out.println(message);
        }
    }
}