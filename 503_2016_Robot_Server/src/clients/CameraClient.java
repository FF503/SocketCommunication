package clients;

import java.io.IOException;


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
    public CameraClient(String address, int port) {
       	super(address, port);
    }
    
    @Override
    public void run(){
    	try{
        	connectToServer();
        	
            // Send a welcome message to the server.
            sendData("Connection with server at " + socket);
            (new Thread(message.send)).start();
            (new Thread(message.receive)).start();
            //(new Thread(send)).start();
            //(new Thread(receive)).start();
            while(!loopDone){}
            sendData("Connection with server closed.");
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
            log("Connection with server closed");
        }
    };
    

    /**
     * Sets up the connection between the offboard processor and the server. Since both the camera
     * algorithm and the server are housed in the same processor, the ip address used is localhost.
     * After establishing a connection(unsecure) the client then initializes its input stream and output
     * stream object
     * @throwsIOException
     */
    @Override
    public void connectToServer() throws IOException {
    	super.connectToServer();

        send = new Runnable(){
        	@Override
        	public void run(){	
        		while(!loopDone){
        			sendData(scan.nextLine());
        		}
        	}
        };
        
        receive = new Runnable(){
        	@Override
        	public void run(){
        		String line = "";
        		while(!loopDone){
        			try {
        				line = receiveData();
    					if(line!=null){
    						log(line);
    						switch(line){
    							case "exit":
    								loopDone = true;
    								break;
    							default:
    								break;
    						}
    					}
    				} 
        			catch (IOException e) {
    					e.printStackTrace();
    				}
        		}
            }
        };
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
    public String receiveData() throws IOException{
    	return super.receiveData();
    }
        
    public static void main(String[] args) throws IOException {
    	log("The client is running");
        CameraClient client = new CameraClient(ADDRESS, PORT);
        client.start();
    }
}

