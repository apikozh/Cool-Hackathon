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
			out.flush();
			out.println("map 30 30");
			//out.print("\n");
			out.flush();
			
			BufferedReader in = new BufferedReader(
				 new InputStreamReader(clientSocket.getInputStream())); 

			String inputLine = in.readLine();
			
			System.out.println("CLIENT: " + inputLine);
			if (!inputLine.equals("I wanna play!")) {
				out.println("Bad answer!");
				System.out.println("Bad answer!");
				out.flush();
				clientSocket.close();
				return;
			}
			
			/*while (true) {
				String inputLine = in.readLine();
				System.out.println(inputLine);
			}*/
				
			while (true) {
				//in.ready();
				String line = in.readLine();
				if (line == null)
					continue;
				Scanner cmdScanner = new Scanner(line);
				if (!cmdScanner.hasNext())
					continue;
				String str = cmdScanner.next();
							System.out.print(str);
				switch (str) {
					case "name": 
						System.out.print("Set name: ");
						if (!cmdScanner.hasNext())
							continue;
						String name = cmdScanner.next();
						unit.setName(name);
						System.out.println(name);
						break;
					case "team":
						System.out.print("Set team: ");
						if (!cmdScanner.hasNextInt())
							continue;
						int team = cmdScanner.nextInt();
						if (team < Game.getTeams().size()) {
							//Game.getTeams().get(team).addUnit(unit);
							unit.setTeam(team);
						}
						System.out.println(team);
						break;
					case "ptype":
						System.out.print("Set ptype: ");
						if (!cmdScanner.hasNextInt())
							continue;
						int ptype = cmdScanner.nextInt();
						//unit.setType(ptype);
						System.out.println(ptype);
						break;
					case "play": 
						System.out.println("play");
						Game.addUnit(unit);
						break;
					default: ;
				}
				
				

			}
		} catch (IOException e) { 
			System.err.println("Problem with Communication Server");
			//clientSocket.close();
		} 
	}

}