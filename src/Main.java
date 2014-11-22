

class Main {

	public static void main(String args[]) {
		
		Game game = new Game();
		
		PortListener listener = new PortListener(6123, game);
	
	}
}