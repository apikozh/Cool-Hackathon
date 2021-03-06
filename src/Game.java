
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
    private static ArrayList<BulletType> bulletTypes = new ArrayList<>();
	private static ArrayList<Weapon> weapons = new ArrayList<>();
	
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

    public static ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public static void setWeapons(ArrayList<Weapon> weapons) {
        Game.weapons = weapons;
    }

    public static void addUnit(Unit unit) {
		int elementPositionY, elementPositionX;
        Random random = new Random();
        boolean unitAdded = false;
        while (!unitAdded) {
            elementPositionY = random.nextInt(GAME_MAP_HEIGHT);
            elementPositionX = random.nextInt(GAME_MAP_WIDTH);
            if (map.getElement(elementPositionX, elementPositionY) == null) {
                unit.setPosition(elementPositionX, elementPositionY);
                int unitTeam = unit.getTeam();
                synchronized (units) {
					unit.getWeapons().addAll(weapons);
					unit.setWeapon(unit.getWeapons().size() > 0 ? 0 : -1);
					if (unitTeam != -1) {
						Team team = teams.get(unit.getTeam());
						team.addUnit(unit);
					}
                    units.add(unit);
                }
                unitAdded = true;
            }
        }
    }

    public static void removeUnit(Unit unit) {
        synchronized (units) {
			if (unit.getTeam() != -1) {
				teams.get(unit.getTeam()).removeUnit(unit);
			}
			units.remove(unit);
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
                Bonus bonus = new BonusMedikit();
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
                wall.setPosition(elementPositionX, elementPositionY);
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

	private static int findBulletAt(int posX, int posY) {
		for (int index=0; index<bullets.size(); index++) {
			if (bullets.get(index).getPositionX() == posX &&
				bullets.get(index).getPositionY() == posY)
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
		}else{
			if (unit.getTeam() != -1) {
				teams.get(unit.getTeam()).removeUnit(unit);
			}
			units.remove(unit);
		}
	}
	
	private static void processBullets() {
		Bullet bullet = null;
		for(int index = bullets.size() - 1; index>=0; index--) {
			bullet = bullets.get(index);
			if (bullet.fly()) {
				int posX = bullet.getPositionX();
				int posY = bullet.getPositionY();
				
				if (posX < 0 || posX >= map.getWidth() ||
					posY < 0 || posY >= map.getHeight())
				{
					bullets.remove(index);
					continue;
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
                        int health = unit.getHealth();
						unit.decreaseHealth(bullet.getType().getDamage());
                        bullet.getOwner().setScore(bullet.getOwner().getScore() + health - unit.getHealth());
						if (unit.getHealth() == 0) {
							processDeadUnit(unit);
                            bullet.getOwner().setScore(bullet.getOwner().getScore() + 100);
						}
						collide = true;
					}
				}

				if (collide)
					bullets.remove(index);
			}
		}
	}

    private static void handleUnitActions(Unit unit, int newPosX, int newPosY) {
        try {
			if (map.getElement(newPosX, newPosY) == null) { //No collision with wall
				int bonusIndex = findBonusAt(newPosX, newPosY);
				if (bonusIndex != -1) {
					Bonus bonus = bonuses.get(bonusIndex);
					bonus.apply(unit);
					bonuses.remove(bonus);
				}
				boolean dead = false;
				int bulletIndex = findBulletAt(newPosX, newPosY);
				if (bulletIndex != -1) {
					int health = unit.getHealth();
					Bullet bullet = bullets.get(bulletIndex);
					unit.decreaseHealth(bullet.getType().getDamage());
					bullet.getOwner().setScore(bullet.getOwner().getScore() + health - unit.getHealth());
					if (unit.getHealth() == 0) {
						dead = true;
						bullet.getOwner().setScore(bullet.getOwner().getScore() + 100);
					}
					bullets.remove(bulletIndex);
				}

				if (dead)
					processDeadUnit(unit);
				else
					unit.setPosition(newPosX, newPosY);
			}
		}catch(IndexOutOfBoundsException e) {
		}
    }

    public static void doShot(Unit unit) {
        if (unit.getWeapon() != -1) {
            Weapon weapon = unit.getWeapons().get(unit.getWeapon());
            Bullet bullet = weapon.shot();
            if (bullet != null) {
                bullet.setOwner(unit);
                bullet.setPositionX(unit.getPositionX());
                bullet.setPositionY(unit.getPositionY());
                bullet.setAngle(unit.getAngle());
                bullets.add(bullet);
            }
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
			unit.setNextAction(new UnitAction());
			
            //Handling moves
			if (unit.getLeftDelayForMovement() == 0) {
				if (unitAction.getMovement() != UnitAction.MOVE_NONE) {
					unit.setLeftDelayForMovement(10);
					int curPosX = unit.getPositionX();
					int curPosY = unit.getPositionY();
					int newPosX, newPosY;
					switch (unitAction.getMovement()) {
						case UnitAction.MOVE_DOWN:
							newPosX = curPosX;
							newPosY = curPosY + 1;
							handleUnitActions(unit, newPosX, newPosY);
							break;
						case UnitAction.MOVE_LEFT:
							newPosX = curPosX - 1;
							newPosY = curPosY;
							handleUnitActions(unit, newPosX, newPosY);
							break;
						case UnitAction.MOVE_RIGHT:
							newPosX = curPosX + 1;
							newPosY = curPosY;
							handleUnitActions(unit, newPosX, newPosY);
							break;
						case UnitAction.MOVE_UP:
							newPosX = curPosX;
							newPosY = curPosY - 1;
							handleUnitActions(unit, newPosX, newPosY);
							break;
					}
				}
			} else {
				unit.setLeftDelayForMovement(unit.getLeftDelayForMovement()-1);
			}


            //Handling rotations
            unit.setAngle((unit.getAngle() + unitAction.getRotation() + 4) % 4);

            //Handling weapon change
			Weapon weapon = null;
			if (unit.getWeapon() != -1) {
				weapon = unit.getWeapons().get(unit.getWeapon());
				int leftDelayForShot = weapon.getLeftDelayForShot();
				if (leftDelayForShot > 0) {
					weapon.setLeftDelayForShot(leftDelayForShot - 1);
				}
			}
            if (unitAction.getWeapon() == -1) {
                if (unitAction.isShooting()) {
                    if (weapon != null && weapon.getLeftDelayForShot() == 0)
                        doShot(unit);
                }
            } else {
                unit.setWeapon(unitAction.getWeapon());
            }

        }
    }

    public static void main(String args[]) {

		PortListener listener = new PortListener(6123);

        // Create GameMap
        map = new GameMap(GAME_MAP_HEIGHT, GAME_MAP_WIDTH);

        // Add some type of bullets
        BulletType bulletType = new BulletType();
        bulletType.setDamage(20);
        bulletType.setDelay(1);
        bulletType.setRadius(1);
        bulletTypes.add(bulletType);

        // Add some type of weapons
        Weapon weapon = new Weapon();
        weapon.setName("Gun");
        weapon.setBulletsNumber(-1);
        weapon.setBulletType(bulletType);
        weapon.setReloadTime(10);

        Weapon weapon2 = new Weapon();
        weapon2.setName("Super_Gun!");
        weapon2.setBulletsNumber(-1);
        weapon2.setBulletType(bulletType);
        weapon2.setReloadTime(1);

        weapons.add(weapon);
        weapons.add(weapon2);

        addRandomWalls(100);
        addRandomBonuses(10);

        // Add teams
        Team team1 = new Team();
        team1.setName("Team1");
        Team team2 = new Team();
        team2.setName("Team2");
        teams.add(team1);
        teams.add(team2);

		// Main loop
		
		while (true) {
			// Send map data to clients
            synchronized (units) {
				listener.sendMapInfoToClients();
				for (Unit unit : units) {
					unit.getClientSocket().sendPlayerInfoToClient();
				}
			}
			// Sleep for X ms
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (units) {
				// Do client ACTIONS
				doUnitActions();
				// Calc new bullets position
				processBullets();
			}
		}

    }
	
}