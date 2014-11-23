import java.net.*; 
import java.io.*; 
import java.util.*;

public class ClientSocket extends Thread { 
	protected Socket clientSocket;
	//private Game game;
	PrintWriter out = null;
	Unit unit;
	PortListener listener;
	
	public ClientSocket(Socket clientSocket, PortListener listener/*, Game game*/) {
		this.clientSocket = clientSocket;
		//this.game = game;
		this.listener = listener;
		
		start();
	}
	
	public void sendMapInfoToClient(String mapInfo) {
		if (out != null) {
			// Send map info
			out.print(mapInfo);
			out.flush();
		}
	}

	public void sendPlayerInfoToClient() {
		if (out != null) {
			// Generate player info
			StringBuilder info = new StringBuilder();
			info.append("begin_player_data\n");
			info.append("lives ");
			info.append(unit.getLivesNumber());
			info.append("\n");
			info.append("weapon ");
			info.append(unit.getWeapon());
			info.append("\n");
			info.append("reload ");
			if (unit.getWeapon() == -1)
				info.append(0);
			else
				info.append(unit.getWeapons().get(unit.getWeapon()).getLeftDelayForShot());
			info.append("\n");
			info.append("weapons ");
			info.append(unit.getWeapons().size());
			info.append("\n");
			for (Weapon w : unit.getWeapons()) {
				info.append(w.getName());
				info.append(" ");
				info.append(w.getBulletsNumber());
				info.append("\n");
			}			
			
			info.append("end_player_data\n");

			// Send player info
			out.print(info);
			out.flush();
		}
	}
	
	public void run() {
		System.out.println ("New communication thread started");

		unit = new Unit();
		unit.setClientSocket(this);
		
		try { 
			out = new PrintWriter(clientSocket.getOutputStream(), false);
			out.print("Welcome to Cool Hackathon!\n");
			out.flush();
						
			BufferedReader in = new BufferedReader(
				 new InputStreamReader(clientSocket.getInputStream())); 

			String inputLine = in.readLine();
			System.out.println("CLIENT: " + inputLine);
			if (!inputLine.equals("I wanna play!")) {
				out.print("Bad answer!\n");
				System.out.println("Bad answer!");
				out.flush();
				clientSocket.close();
				return;
			}

			out.print("map " + Game.getMap().getWidth() + " " + Game.getMap().getHeight() + "\n");
			out.print("teams " + Game.getTeams().size() + "\n");
			for (Team team : Game.getTeams()) {
				out.print(team.getName() + "\n");
			}			
			out.print("weapons " + Game.getWeapons().size() + "\n");
			for (Weapon weapon : Game.getWeapons()) {
				out.print(weapon.getName() + " " + weapon.getReloadTime() + " " + 0/*weapon.getBulletType() */+ "\n");
			}			
			
			out.flush();
			listener.addClient(this);
			
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
				String cmd = cmdScanner.next();
                System.out.print(cmd);
                UnitAction nextAction;
                switch (cmd) {
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
						if (team >= -1 && team < Game.getTeams().size()) {
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

					case "up": 
						System.out.println(cmd);
						nextAction = unit.getNextAction();
						nextAction.setMovement(UnitAction.MOVE_UP);
						unit.setNextAction(nextAction);
						break;
					case "down": 
						System.out.println(cmd);
						nextAction = unit.getNextAction();
						nextAction.setMovement(UnitAction.MOVE_DOWN);
						unit.setNextAction(nextAction);
						break;
					case "left": 
						System.out.println(cmd);
						nextAction = unit.getNextAction();
						nextAction.setMovement(UnitAction.MOVE_LEFT);
						unit.setNextAction(nextAction);
						break;
					case "right": 
						System.out.println(cmd);
						nextAction = unit.getNextAction();
						nextAction.setMovement(UnitAction.MOVE_RIGHT);
						unit.setNextAction(nextAction);
						break;
					case "rotleft": 
						System.out.println(cmd);
						nextAction = unit.getNextAction();
						nextAction.setRotation(UnitAction.ROT_LEFT);
						unit.setNextAction(nextAction);
						break;
					case "rotright": 
						System.out.println(cmd);
						nextAction = unit.getNextAction();
						nextAction.setRotation(UnitAction.ROT_RIGHT);
						unit.setNextAction(nextAction);
						break;
					case "shot": 
						System.out.println(cmd);
						nextAction = unit.getNextAction();
						nextAction.setShooting(true);
						unit.setNextAction(nextAction);
						break;
					case "weapon": 
						System.out.println(cmd);
						if (!cmdScanner.hasNextInt())
							continue;
						int weapon = cmdScanner.nextInt();
						if (weapon >= 0 && weapon < unit.getWeapons().size()) {
							nextAction = unit.getNextAction();
							nextAction.setWeapon(weapon);
							unit.setNextAction(nextAction);
						}
						break;
                    case "end":
                        System.out.println("end");
                        Game.removeUnit(unit);
                        break;

                    default: ;
				}

			}
		} catch (IOException e) { 
			System.err.println("Problem with Communication Server");
			//clientSocket.close();
		} finally {
			if (Game.getUnits().contains(unit))
				Game.removeUnit(unit);
			listener.removeClient(this);
		}
	}

}	