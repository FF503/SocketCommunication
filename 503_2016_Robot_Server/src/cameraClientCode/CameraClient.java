package cameraClientCode;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *CLient Class for the camera needs to be integrated with actual camera code. 
 *This is just a skeleton that is meant to be copied and pasted and then modified.
 */
public class CameraClient {

    private BufferedReader in;
    private PrintWriter out;
   

    /**
     * Constructs the client by laying out the GUI and registering a
     * listener with the textfield so that pressing Enter in the
     * listener sends the textfield contents to the server.
     */
    public CameraClient() {

    	//init client here
        
    }

    /**
     * Sets up the connection between the camera algorithm and the server. Since both the camera
     * algorithm and the server are housed in the same processor, the ip adress used is localhost.
     * After establishing a connection(unsecure) the client tehn intiliazes its input stream and output
     * stream object
     */
    public void connectToServer() throws IOException {

        // Get the server address from a dialog box.
        String serverAddress = "localhost";

        // Make connection and initialize streams
        Socket socket = new Socket(serverAddress, 9898);
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

    }
    
    /**
     * Example method to show how ot use the in and out objects to send and recieve data.
     */
    public void sendAndRecieve(){
    	//output is in the form of a string that must follow the protocal
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

    /**
     * Runs the client application.
     */
    public static void main(String[] args) throws Exception {
        CameraClient client = new CameraClient();
        client.connectToServer();
        client.sendAndRecieve();
    }
}

