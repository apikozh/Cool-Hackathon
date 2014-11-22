import java.net.*; 
import java.io.*; 
import java.util.*;

public class ClientSocket extends Thread { 
	protected Socket clientSocket;
	private Game game;
	
	public ClientSocket(Socket clientSocket, Game game) {
		this.clientSocket = clientSocket;
		this.game = game;
		
		start();
	}
	
	public void run() {
		System.out.println ("New communication thread started");

		Unit unit = new Unit();
		
		try { 
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), false);
			out.println("Welcome to Cool Hackathon!");
			out.println("map 30 30");
			out.flush();
			
			BufferedReader in = new BufferedReader(
				 new InputStreamReader(clientSocket.getInputStream())); 

			String inputLine = in.readLine();
			
			if (!inputLine.equals("I wanna play!")) {
				return;
			}
			
			/*while (true) {
				String inputLine = in.readLine();
				System.out.println(inputLine);
			}*/
				
			while (true) {
				String line = in.readLine();
				Scanner cmdScanner = new Scanner(line);
				String cmd = cmdScanner.next();
				switch (cmd) {
					case "name": ;
					default: ;
				}
				
				//game.addUnit(unit);
				

			}*/
		} catch (IOException e) { 
			System.err.println("Problem with Communication Server");
			System.exit(1); 
		} 
	}

}