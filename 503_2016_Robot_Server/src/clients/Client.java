package clients;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public abstract class Client {
	
	protected static BufferedReader in;
	private static PrintWriter out;
	private static int port;
	private static String address;
	protected static ArrayList<String> allData;
	protected static Socket socket;
	
	public Client(String address, int port){
		Client.port = port;
		Client.address = address;
		allData = new ArrayList<String>();
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
		line = in.readLine();
		if(line!=null){
			if(line.equalsIgnoreCase("exit")){
				done = true;
			}
			else{
				data.add(line);
			}
		}
		allData.addAll(data);
		return data;
	}
	
	@Override
	public String toString(){
		return "Socket at port " + port + " and address at " + address;
	}
	
	private static void log(String message) {
        System.out.println(message);
    }
	
}
