
import java.util.*;

class Game {
    private final static int GAME_MAP_WIDTH = 30;
    private final static int GAME_MAP_HEIGHT = 30;
	private static ArrayList<Unit> units = new ArrayList<>();
	private static ArrayList<Bonus> bonuses = new ArrayList<>();
	private static GameMap map;
	private static ArrayList<Bullet> bullets = new ArrayList<>();
    private static ArrayList<MapObject> mapObjects = new ArrayList<>();
    private static ArrayList<Team> teams = new ArrayList<>();

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


    public static ArrayList<MapObject> getMapObjects() {
        return mapObjects;
    }

    public static ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public static void addUnit(Unit unit) {
        int elementPositionY, elementPositionX;
        Random random = new Random();
        boolean unitAdded = false;
        while (!unitAdded) {
            elementPositionY = random.nextInt(GAME_MAP_HEIGHT);
            elementPositionX = random.nextInt(GAME_MAP_WIDTH);
            if (map.getElement(elementPositionX, elementPositionY) == null) {
                int unitTeam = unit.getTeam();
                if (unitTeam != -1) {
                    Team team = teams.get(unit.getTeam());
                    team.addUnit(unit);
                }
                synchronized (units) {
                    units.add(unit);
                }
                unitAdded = true;
            }
        }
    }

    public static void removeUnit(Unit unit) {
        if (unit.getTeam() != -1) {
            teams.get(unit.getTeam()).removeUnit(unit);
        }
        units.remove(unit);
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
                //TODO: There might be bug! :) Several bonuses at one place
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
                MapObject obj;
				if ((obj = map.getElement(posX, posY)) != null) {
					obj.decreaseHealth(bullet.getType().getDamage());
					if (obj.getHealth() == 0) {
						map.setElement(posX, posY, null);
						mapObjects.remove(obj);
					}
					collide = true;
				}
                int bnsId;
				if ((bnsId = findBonusAt(posX, posY)) != -1) {
					Bonus bonus = bonuses.get(bnsId);
					bonus.decreaseHealth(bullet.getType().getDamage());
					if (bonus.getHealth() == 0) {
						bonuses.remove(bnsId);
					}
					collide = true;
				}
				for (int unitId=units.size()-1; unitId>=0; unitId--) {
					Unit unit = units.get(unitId);
					if (unit.getPositionX() == posX && unit.getPositionY() == posY) {
						unit.decreaseHealth(bullet.getType().getDamage());
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

    private static void handleUnitActions(Unit unit, int newPositionX, int newPositionY) {
        if (map.getElement(newPositionX, newPositionY) == null) { //No collision with wall
            int bonusIndex = findBonusAt(newPositionX, newPositionY);
            if (bonusIndex != -1) {
                Bonus bonus = bonuses.get(bonusIndex);
                //TODO: Assign bonus to unit
            }
            for (Bullet bullet : bullets) {
                if (bullet.getPositionX() == newPositionX && bullet.getPositionY() == newPositionY) {
                    //TODO: Make unit dead
                }
            }
            unit.setPosition(newPositionX, newPositionY);
        }
    }

    public static void doUnitActions() {
        //перебрать все юниты,
        //обработать nextActiona
        //1.move
        //walls, bonuses, bullets,
        //2.rotation
        //3.change weapon (getWeapon = -1 - the same) or fire
        //New bullet,

        for (Unit unit : units) {
            UnitAction unitAction = unit.getNextAction();
            //Handling moves

            int currentPositionX = unit.getPositionX();
            int currentPositionY = unit.getPositionY();
            int newPositionX, newPositionY;
            switch (unitAction.getMovement()) {
                case Unit.ANGLE_DOWN:
                    newPositionX = currentPositionX;
                    newPositionY = currentPositionY + 1;
                    handleUnitActions(unit, newPositionX, newPositionY);
//                    if (map.getElement(newPositionX, newPositionY) == null) { //No collision with wall
//                        int bonusIndex = findBonusAt(newPositionX, newPositionY);
//                        if (bonusIndex != -1) {
//                            Bonus bonus = bonuses.get(bonusIndex);
//                            //TODO: Assign bonus to unit
//                        }
//                        for (Bullet bullet : bullets) {
//                            if (bullet.getPositionX() == newPositionX && bullet.getPositionY() == newPositionY) {
//                                //TODO: Make unit dead
//                            }
//                        }
//                        unit.setPosition(newPositionX, newPositionY);
//                    }

                    //Else - collision with wall. Move is impossible
                    break;
                case Unit.ANGLE_LEFT:
                    //setPositionX(currentPositionX - 1);
                    break;
                case Unit.ANGLE_RIGHT:
                    //setPositionX(currentPositionX + 1);
                    break;
                case Unit.ANGLE_UP:
                    //setPositionY(currentPositionY - 1);
                    break;
            }


            //Handling rotations
            unit.setAngle(unitAction.getRotation());

            //Handling weapon change

                //TODO: do something to check if it is possible to change weapon
                //unit.setWeapon(unitAction.getWeapon());

        }
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
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Do client ACTIONS
			//doUnitActions();
			// Calc new bullets position
			processBullets();
		}

    }
	
}