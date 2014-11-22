import java.net.*; 
import java.io.*; 

public class ClientSocket extends Thread { 
	protected Socket clientSocket;
	
	public ClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
		start();
	}
	
	public void run() {
		while (true) {
		}
	}

}