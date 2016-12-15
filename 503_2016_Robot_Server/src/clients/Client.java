package clients;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import sendRecieve.*;

public abstract class Client extends Thread {
	
	private static BufferedReader in;
	private static PrintWriter out;
	private static int port;
	private static String address;
	protected static ArrayList<String> allData;
	protected static Socket socket;
	protected static Scanner scan;
	protected static boolean loopDone;
	protected static Runnable send, receive;
	
	public Client(String address, int port){
		this.port = port;
		this.address = address;
	}
	
    /**
     * Services each client by listening for data and sending back data when needed.
     */
	@Override
    public abstract void run();
	
	protected void connectToServer() throws IOException{
		socket = new Socket(address, port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()), true);
		scan = new Scanner(System.in);
		loopDone = false;
	}
	
	protected void sendData(Object data) {
		out.println(data);
	}
	
	protected String receiveData() throws IOException {
		String data = in.readLine();
		allData.add(data);
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
