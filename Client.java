import java.net.*;
import java.io.*;

public class Client {
	public static void main(String[] args) throws IOException, InterruptedException {
		// Make Connection With Socket Server
		Socket s = new Socket("localhost", 23500);
		
		// Send Message To Socket Output Stream
		PrintWriter pr = new PrintWriter(s.getOutputStream());
		pr.println("First Client");
		pr.flush();
		
		// Get Message From SocketServer
		InputStreamReader in = new InputStreamReader(s.getInputStream());
		BufferedReader bf = new BufferedReader(in);
		String str = bf.readLine();
		System.out.println("Server: " + str);
		
		// Test Threading By Running This File With Others Concurrently
		Thread.sleep(5000);
		pr.println("Other");
		pr.flush();
		Thread.sleep(5000);
		pr.println("Whatever");
		pr.flush();
		Thread.sleep(5000);
		pr.println("SSTOP");
		pr.flush();
		s.close();
	}
}

