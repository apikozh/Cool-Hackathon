
import java.util.*;

class Game {
    private final static int GAME_MAP_WIDTH = 30;
    private final static int GAME_MAP_HEIGHT = 30;
	private static ArrayList<Unit> units;
	private static ArrayList<Bonus> bonuses;
	private static GameMap map;
	private static ArrayList<Bullet> bullets;

    private static ArrayList<Team> teams;

	public static ArrayList<Unit> getUnits() {
		return units;
	}

    public static ArrayList<Bonus> getBonuses() {
		return bonuses;
	}

    public static ArrayList<Team> getTeams() {
        return teams;
    }

    public static GameMap getMap() {
        return map;
    }

    public static void addUnit(Unit unit) {
        int elementPositionY, elementPositionX;
        Random random = new Random();
        boolean unitAdded = false;
        while (!unitAdded) {
            elementPositionY = random.nextInt(GAME_MAP_HEIGHT);
            elementPositionX = random.nextInt(GAME_MAP_WIDTH);
            if (map.getElement(elementPositionX, elementPositionY) == null) {
                Team team = teams.get(unit.getTeam());
                team.addUnit(unit);
                unitAdded = true;
            }
        }
    }

    private static void addRandomBonuses(int quantity) {
        int elementPositionY, elementPositionX;
        int numberOfElements = 0;
        Random random = new Random();
        while (numberOfElements != quantity) {
            elementPositionY = random.nextInt(GAME_MAP_HEIGHT);
            elementPositionX = random.nextInt(GAME_MAP_WIDTH);
            if (map.getElement(elementPositionX, elementPositionY) == null) {
                Bonus bonus = new Bonus();
                bonus.setPosition(elementPositionX, elementPositionY);
                bonuses.add(bonus);
                numberOfElements++;
            }
        }
    }

    private static void addRandomWalls(int quantity) {
        Random random = new Random();
        int elementPositionX, elementPositionY;
        int numberOfElements = 0;
        while (numberOfElements != quantity) {
            elementPositionY = random.nextInt(GAME_MAP_HEIGHT);
            elementPositionX = random.nextInt(GAME_MAP_WIDTH);
            if (map.getElement(elementPositionX, elementPositionY) == null) {
                Wall wall = new Wall();
                map.setElement(elementPositionX, elementPositionY, wall);
                numberOfElements++;
            }
        }

    }

	private static void proccessBullets() {
		Bullet bullet = null;
		for(int index = bullets.size() - 1; index>=0; index--) {
			bullet = bullets.get(index);
			if (bullet.getLeftDelay() > 0) {
				bullet.setLeftDelay(bullet.getLeftDelay() - 1);
			}else{
				bullet.fly();
				if (bullet.getPositionX() < 0 && bullet.getPositionX() >= map.getWidth() &&
					bullet.getPositionY() < 0 && bullet.getPositionY() >= map.getHeight())
				{
					bullets.remove(index);
				}
				if 
				bullet.getType()
				if (map.getElement())
			}
		}
	}

    public static void main(String args[]) {
        Random random = new Random();

        // Create GameMap
        map = new GameMap(GAME_MAP_HEIGHT, GAME_MAP_WIDTH);

        addRandomWalls(100);
        addRandomBonuses(10);
		
		
		// Main loop
		
		while (true) {
			// Send map data to clients
			sendMapInfoToPlayers();
			// Sleep for X ms
			Thread.sleep(1000);
			// Do client ACTIONS
			doUnitActions();
			// Calc new bullets position
			proccessBullets();
		}

    }
	
}