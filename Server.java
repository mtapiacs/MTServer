/* Optional TODO:
 * - Report Number Of Threads
 * - Do I have to set as null after append?
 * - Are the sockets different or same?
 */

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Server {
	// Server Attributes
	List<Socket> sockets;
	String server;
	int port; 
	int currentSocket = 0; // Allows Iteration Over Socket List
	boolean running; // Attribute Controls Server Listen
	
	// Server Constructor
	public Server (String server, int port) {
		this.server = server;
		this.port = port;
		this.sockets = new ArrayList<Socket>();
	}
	
	// Running Thread Class
	public class ThreadRunnable implements Runnable {
		// Instance Of Socket
		Socket s; 
		
		// Thread Constructor Adds Socket S
		public ThreadRunnable(Socket s) {
			this.s = s;
		}
		
		// Executed On Start
		@Override
		public void run() {
			InputStreamReader in;
			try {
				while (true) {
					// Incoming Message Stream From Client
					in = new InputStreamReader(s.getInputStream());
					BufferedReader bf = new BufferedReader(in);
					String str = bf.readLine();
					
					// If Client Sends "SSTOP" Stop ServerSocket And Close Socket
					if (str.indexOf("SSTOP") >= 0) {
						System.out.println("Stop called");
						s.close();
						running = false;
						break;
					}
					
					// If Client Send "SALL" Stop Server Socket
					
					// Client Message Output
					System.out.println("Client: " + str);
					
					// Send Message Stream To Client
					PrintWriter pr = new PrintWriter(s.getOutputStream());
					pr.println("Got your connection");
					pr.flush();
				}
			} catch (IOException e) {
				try {
					s.close();
					running = false;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.println("Connection Ended");
			}
		}
		
		
	}
	
	// CreateAndManage() - Creates A New Thread That Manages That Particular Connection
	public void createAndManage() {
		// Create And Start New Thread With ThreadRunnable And Current Socket Socket
		Thread thread = new Thread(new ThreadRunnable(sockets.get(currentSocket)));
		thread.start();
		currentSocket++; // Increment Counter So List Is Up To Date
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
			
			// Append Socket To List Of Sockets
			sockets.add(s);
			
			// Creates A Thread To Process Request
			createAndManage();	
		}
		// Close Connection
		ss.close();
	}
}
