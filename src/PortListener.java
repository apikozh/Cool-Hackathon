import java.net.*; 
import java.io.*; 
import java.utils.*; 

public class PortListener extends Thread { 
	protected int port;
	private boolean terminated;
	//private Game game;
	private ArrayList<ClientSocket> clients;
	
	public PortListener(int port/*, Game game*/) {
		this.port = port;
		//this.game = game;
		start();
	}
	
	public synchronized void addClient(ClientSocket client) {
		clients.add(client); 
	}

	public synchronized void removeClient(ClientSocket client) {
		clients.remove(client); 
	}
	public synchronized void sendMapInfoToClients(/*Game game*/) {
		// Generate map info for all
		StringBuilder mapInfo = new StringBuilder();
		mapInfo.append("begin_map_data");
		
		// Static map objects
		mapInfo.append("walls ");
		mapInfo.append(Game.mapObjects.size());
		mapInfo.append("\r\n");
		for (MapObject obj : Game.mapObjects) {
			mapInfo.append(obj.getPositionX());
			mapInfo.append(" ");
			mapInfo.append(obj.getPositionY());
			mapInfo.append(" ");
			mapInfo.append(obj.getHealth());
			mapInfo.append("\r\n");
		}

		// Units info
		mapInfo.append("players ");
		mapInfo.append(Game.units.size());
		mapInfo.append("\r\n");
		for (Unit obj : Game.units) {
			mapInfo.append(obj.getPositionX());
			mapInfo.append(" ");
			mapInfo.append(obj.getPositionY());
			mapInfo.append(" ");
			mapInfo.append(obj.getAngle());
			mapInfo.append(" ");
			mapInfo.append(obj.getHealth());
			mapInfo.append(" ");
			mapInfo.append(0);
			mapInfo.append(" ");
			mapInfo.append(obj.getName());
			mapInfo.append(" ");
			mapInfo.append(obj.getTeam());
			mapInfo.append("\r\n");
		}

		// Shells/bullets info
		mapInfo.append("shells ");
		mapInfo.append(Game.bullets.size());
		mapInfo.append("\r\n");
		for (Bullet obj : Game.bullets) {
			mapInfo.append(obj.getPositionX());
			mapInfo.append(" ");
			mapInfo.append(obj.getPositionY());
			mapInfo.append(" ");
			mapInfo.append(obj.getAngle());
			mapInfo.append(" ");
			mapInfo.append(obj.getType());
			mapInfo.append("\r\n");
		}

		// Bonuses info
		mapInfo.append("bonuses ");
		mapInfo.append(Game.bonuses.size());
		mapInfo.append("\r\n");
		for (Bonus obj : Game.bonuses) {
			mapInfo.append(obj.getPositionX());
			mapInfo.append(" ");
			mapInfo.append(obj.getPositionY());
			mapInfo.append(" ");
			mapInfo.append(obj.getType());
			mapInfo.append("\r\n");
		}
		
		mapInfo.append("end_map_data");
		
		String rawMapInfo = mapInfo.toString();
		
		for (ClientSocket client : clients) {
			client.sendMapInfoToClient(rawMapInfo);
		}
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
						clients.add(new ClientSocket(serverSocket.accept())); 
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