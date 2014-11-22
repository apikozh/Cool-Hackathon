import java.net.*; 
import java.io.*; 

public class PortListener extends Thread { 
	protected int port;
	private boolean terminated;
	private Game game;
	
	public PortListener(int port, Game game) {
		this.port = port;
		this.game = game;
		start();
	}
	
	public void run() {
		ServerSocket serverSocket = null; 

		try { 
			serverSocket = new ServerSocket(port);
			System.out.println("Connection socket created at port " + port);
			try { 
				while (!terminated) {
					//serverSocket.setSoTimeout(10000);
					System.out.println("Waiting for Connection");
					try {
                       new ClientSocket(serverSocket.accept(), game); 
					} catch (SocketTimeoutException ste) {
						System.out.println ("Timeout Occurred");
					}
				}
			} catch (IOException e) {
				System.err.println("Accept failed."); 
				System.exit(1); 
			}
        } catch (IOException e) {
			System.err.println("Could not listen on port: " + port); 
			System.exit(1); 
		} finally {
			try {
				System.out.println ("Closing Server Connection Socket");
				serverSocket.close(); 
			} catch (IOException e) {
				System.err.println("Could not close port: " + port); 
				System.exit(1); 
			} 
		}
	}
}