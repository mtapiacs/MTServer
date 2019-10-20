import java.io.IOException;

public class MTServer {
	// Driver Method To Open Server And Receive Connections
	public static void main(String[] args) {
		Server mtServer = new Server("localhost", 23500);
		try {
			mtServer.initServerSocket();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}