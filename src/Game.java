
import java.util.*;

class Game {
<<<<<<< HEAD
    private final static int GAME_MAP_WIDTH = 30;
    private final static int GAME_MAP_HEIGHT = 30;
	private static ArrayList<Unit> units;
	private static ArrayList<Bonus> bonuses;
	private static GameMap map;
	private static ArrayList<Bullet> bullets;

    private static ArrayList<Team> teams;
=======
	ArrayList<Unit> units;
	ArrayList<Bonus> bonuses;
	GameMap map;
	ArrayList<Bullet> bullets;
>>>>>>> origin/master

	public static ArrayList<Unit> getUnits() {
		return units;
	}

    public static ArrayList<Bonus> getBonuses() {
		return bonuses;
	}

<<<<<<< HEAD
    public static ArrayList<Team> getTeams() {
        return teams;
    }

//    public static GameMap getGameMap(int height, int width) {
//		return new GameMap(height, width);
//	}

    public static GameMap getGameMap() {
        return map;
    }

    private static void addRandomUnit(Unit unit) {
        //random coordinates
        //int getTeam
        //
        //addUnit
        int elementPositionY, elementPositionX;
        Random random = new Random();
        boolean unitAdded = false;
        while (!unitAdded) {

        }
            elementPositionY = random.nextInt(GAME_MAP_HEIGHT);
            elementPositionX = random.nextInt(GAME_MAP_WIDTH);
//            if (gameMap.getElement(elementPositionX, elementPositionY) == null) {
//                Unit unit = new Unit();
//                unit.setPosition(elementPositionX, elementPositionY);
//                units.add(unit);
//            }

    }



    private static void addRandomBonuses(int quantity, GameMap gameMap) {
        int elementPositionY, elementPositionX;
        Random random = new Random();
        for (int i = 0; i < quantity; i++) {
            elementPositionY = random.nextInt(GAME_MAP_HEIGHT);
            elementPositionX = random.nextInt(GAME_MAP_WIDTH);
            if (gameMap.getElement(elementPositionX, elementPositionY) == null) {
                Bonus bonus = new Bonus();
                bonus.setPosition(elementPositionX, elementPositionY);
                bonuses.add(bonus);
            }
        }
    }

    private static void addRandomWalls(int quantity, GameMap gameMap) {
        Random random = new Random();
        int elementPositionX;
        int elementPositionY;
        for (int i = 0; i < quantity; i++) {
            elementPositionY = random.nextInt(GAME_MAP_HEIGHT);
            elementPositionX = random.nextInt(GAME_MAP_WIDTH);
            if (gameMap.getElement(elementPositionX, elementPositionY) == null) {
                Wall wall = new Wall();
                gameMap.setElement(elementPositionX, elementPositionY, wall);
            }
        }

    }
=======
	GameMap getMap() {
		return map;
	}
>>>>>>> origin/master

    public static void main(String args[]) {
        Random random = new Random();

        // Create GameMap
        map = new GameMap(GAME_MAP_HEIGHT, GAME_MAP_WIDTH);

        addRandomWalls(100, map);
        addRandomBonuses(10, map);


        //+instance of map
        //+instances of wall
        //+put in different places
        //+bonuses randomly on map
        //+add method addUnit(Unit), which adds unit on random place


    }
	
}