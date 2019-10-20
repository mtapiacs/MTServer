/* TODO:
 * - Report Number Of Threads
 */

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Server {
	List<Socket> sockets;
	String server;
	int port;
	int currentSocket = 0;
	boolean running;
	
	// Server Constructor
	public Server (String server, int port) {
		this.server = server;
		this.port = port; // 23500
		this.sockets = new ArrayList<Socket>();
	}
	
	public class ThreadRunnable implements Runnable {
		Socket s;
		
		@Override
		public void run() {
			InputStreamReader in;
			try {
			in = new InputStreamReader(s.getInputStream());
			
			BufferedReader bf = new BufferedReader(in);
			
			String str = bf.readLine();
			
			if (str.indexOf("SSTOP") >= 0) {
				System.out.println("Stop called");
				s.close();
				running = false;
			}
			
			System.out.println("Client: " + str);
			
			PrintWriter pr = new PrintWriter(s.getOutputStream());
			
			pr.println("Got your connection");
			pr.flush();
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();			
			}
		}
		
		public ThreadRunnable(Socket s) {
			this.s = s;
		}
	}
	
	// Creates A New Thread That Manages That Particular Connection
	public void createAndManage() {
		Thread thread = new Thread(new ThreadRunnable(sockets.get(currentSocket)));
		thread.start();
		currentSocket++;
	}
	
	// InitServerSocket() - Opens And Handles Socket Connections
	public void initServerSocket () throws IOException {		
		// Opens Server Socket For Connections
		ServerSocket ss = new ServerSocket(port); 
		
		// Running Variable Continues Execution Of Loop
		running = true;
		
		System.out.println("Server has started at port: " + port);
		
		while (running) {
			// Accept Connection And Add To List Of Sockets
			Socket s = ss.accept();
			sockets.add(s);
			
			// Creates A Thread To Process Request
			createAndManage();	
		}
		
		ss.close();
	}
}
