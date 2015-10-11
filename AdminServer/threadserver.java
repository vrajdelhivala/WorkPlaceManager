import java.io.*;
import java.net.*;
import java.util.Date;

public class threadserver{
	private ServerSocket serversocket;
	private int port;
	public threadserver(int port){
		this.port=port;
		
	}
	public void start() throws IOException
	{
	System.out.println("Starting the socket at port : "+port);	
	serversocket=new ServerSocket(port);
	Socket client=null;
	
	while(true)
	{
		System.out.println("Waiting for clients...");
    	client = serversocket.accept();
    	System.out.println("The following client has connected:"+client.getInetAddress().getCanonicalHostName());
        Thread thread = new Thread(new SocketClientHandler(client));
        thread.start();
	}
	}
	public static void main(String[] args) {
        // Setting a default port number.
        int portNumber = 9991;
       
        try {
            // initializing the Socket Server
            threadserver socketServer = new threadserver(portNumber);
            socketServer.start();
            
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
	}
