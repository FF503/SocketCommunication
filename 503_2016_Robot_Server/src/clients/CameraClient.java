package clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *CLient Class for the camera needs to be integrated with actual camera code. 
 *This is just a skeleton that is meant to be copied and pasted and then modified.
 */
public class CameraClient extends Client{
	
	//private static final String CAMERA_IP = "169.254.49.179";
	
    private static final String ADDRESS = "localhost";
    private static final int PORT = 9898;
   

    /**
     * Constructs the client by laying out the GUI and registering a
     * listener with the textfield so that pressing Enter in the
     * listener sends the textfield contents to the server.
     */
	//init client here

    public CameraClient(String address, int port) {
       	super(address, port);
    }

    /**
     * Sets up the connection between the camera algorithm and the server. Since both the camera
     * algorithm and the server are housed in the same processor, the ip address used is localhost.
     * After establishing a connection(unsecure) the client then initializes its input stream and output
     * stream object
     */
    @Override
    public boolean connectToServer() throws IOException {
    	return super.connectToServer();
    }
    
    /**
     * Sends data to the server by using the super class's method..
     */
    @Override
    public void sendData(Object data){
    	super.sendData(data);
    }
    
    /**
     * Receives data from the server and adds it to the object's private 
     * String ArrayList by using the super class's method.
     */
    @Override
    public ArrayList<String> receiveData() throws IOException{
    	return super.receiveData();
    }
    
    
/*    //**
     * Example method to show how to use the in and out objects to send and receive data.
     *//*
    public void sendAndRecieve(){
    	//output is in the form of a string that must follow the protocol
    	String output = "";
    	out.println(output);
    	String response;
    	try {
            response = in.readLine();
            if (response == null || response.equals("")) {
                  System.exit(0);
              }
        } catch (IOException ex) {
               response = "Error: " + ex;
        }
    	System.out.println(response);
    }
*/
    /**
     * Runs the client application.
     */
    public static void main(String[] args) throws Exception {
        CameraClient client = new CameraClient(ADDRESS, PORT);
        client.connectToServer();
        Scanner scan = new Scanner(System.in); 	//For testing purposes
        System.out.println(client.connectToServer());
        while(socket!=null && socket.isConnected()){
    		//System.out.println(client.receiveData());	//For testing purposes
        	client.sendData(scan.nextLine());
        }
        client.socket.close();
    }
}

