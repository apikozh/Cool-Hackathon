
import java.util.*;

class Game {
    private final static int GAME_MAP_WIDTH = 30;
    private final static int GAME_MAP_HEIGHT = 30;
	private static ArrayList<Unit> units;
	private static ArrayList<Bonus> bonuses;
	private static GameMap map;
	private static ArrayList<Bullet> bullets;
	private static ArrayList<MapObject> mapObjects;
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
                mapObjects.add(wall);
				numberOfElements++;
            }
        }

    }

	private static int findBonusAt(int posX, int posY) {
		for (int index=0; index<bonuses.size(); index++) {
			if (bonuses.get(index).getPositionX() == posX &&
				bonuses.get(index).getPositionY() == posY)
			{
				return index;
			}
		}
		return -1;
	}
	
	private static void processDeadUnit(Unit unit) {
		if (unit.getLivesNumber() > 1) {
			Random random = new Random();
            int posX, posY;
			do {
				posX = random.nextInt(map.getWidth());
				posY = random.nextInt(map.getHeight());
			} while (map.getElement(posX, posY) != null);
			unit.setPosition(posX, posY);
			unit.setHealth(100);
			unit.setLivesNumber(unit.getLivesNumber() - 1);
		}else
			units.remove(unit);
	}
	
	private static void processBullets() {
		Bullet bullet = null;
		for(int index = bullets.size() - 1; index>=0; index--) {
			bullet = bullets.get(index);
			if (bullet.getLeftDelay() > 0) {
				bullet.setLeftDelay(bullet.getLeftDelay() - 1);
			}else{
				bullet.fly();
				int posX = bullet.getPositionX();
				int posY = bullet.getPositionY();
				
				if (posX < 0 && posX >= map.getWidth() &&
					posY < 0 && posY >= map.getHeight())
				{
					bullets.remove(index);
				}
				
				boolean collide = false;
				if ((MapObject obj = map.getElement(posX, posY)) != null) {
					obj.decreaseHealth(bullet.getType().getDemage());
					if (obj.getHealth() == 0) {
						map.setElement(posX, posY, null);
						mapObjects.remove(obj);
					}
					collide = true;
				}
				if ((int bnsId = findBonusAt(posX, posY)) != -1) {
					Bonus bonus = bouses.get(bnsId);
					bonus.decreaseHealth(bullet.getType().getDemage());
					if (bonus.getHealth() == 0) {
						bouses.remove(bnsId);
					}
					collide = true;
				}
				for (int unitId=units.size()-1; unitId>=0; unitId--) {
					Unit unit = units.get(unitId);
					if (unit.getPositionX() == posX && unit.getPositionY() == posY) {
						unit.decreaseHealth(bullet.getType().getDemage());
						if (unit.getHealth() == 0) {
							processDeadUnit(unit);
						}
						collide = true;
					}
				}

				if (collide)
					bullets.remove(index);
			}
		}
	}

	public static void sendMapInfoToPlayers() {
	
	}
	
    public static void main(String args[]) {

		PortListener listener = new PortListener(6123);

        // Create GameMap
        map = new GameMap(GAME_MAP_HEIGHT, GAME_MAP_WIDTH);

        addRandomWalls(100);
        addRandomBonuses(10);
		
		// Main loop
		
		while (true) {
			// Send map data to clients
			listener.sendMapInfoToClients();
			// Sleep for X ms
			Thread.sleep(1000);
			// Do client ACTIONS
			doUnitActions();
			// Calc new bullets position
			processBullets();
		}

    }
	
}