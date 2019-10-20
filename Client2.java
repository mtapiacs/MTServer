import java.net.*;
import java.io.*;

public class Client2 {
	public static void main(String[] args) throws IOException {
		Socket s = new Socket("localhost", 23500);
		
		PrintWriter pr = new PrintWriter(s.getOutputStream());
	
		pr.println("Second Client");
		pr.flush();
		
		InputStreamReader in = new InputStreamReader(s.getInputStream());
		BufferedReader bf = new BufferedReader(in);
		
		String str = bf.readLine();
		
		System.out.println("Server: " + str);
		
		s.close();
	}
}

