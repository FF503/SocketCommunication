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
	
	private BufferedReader in;
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
	
	protected void sendData(String data) throws IOException{
		out.print(data);
	}
	
	protected ArrayList<String> receiveData() throws IOException{
		String line = null;
		ArrayList<String> data = new ArrayList<String>();
		while((line=in.readLine()) != null){
			data.add(line);
		}
		//Not sure which method we want to use.
		allData.addAll(data);
		//allData = data;
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
