import java.io.IOException;

public class MTServer {
	// Driver Method To Open Server And Receive Connections
	public static void main(String[] args) {
		// Create Server Instance
		Server mtServer = new Server("localhost", 23500);
		try {
			// Open Server
			mtServer.initServerSocket();
		} catch (IOException e) {
			// Execution Failed
			e.printStackTrace();
		}
	}
}