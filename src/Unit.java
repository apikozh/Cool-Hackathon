import java.util.ArrayList;

/**
 * Created by Victor Dichko on 22.11.14.
 */
public class Unit extends DynamicObject {
    private String name;

    private int livesNumber;

    private int weapon;

    private int team;

    private int leftDelayForMovement;

    private int score;

    private ArrayList<Weapon> weapons = new ArrayList<>();
	
    private UnitAction nextAction = new UnitAction();

	private ClientSocket clientSocket;

    public String getName() {
        return name;
    }

    public int getWeapon() {
        return weapon;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public synchronized UnitAction getNextAction() {
        return nextAction;
    }

    public int getTeam() {
        return team;
    }
	
    public void setName(String name) {
        this.name = name;
    }

    public void setWeapon(int weapon) {
        this.weapon = weapon;
    }

    public synchronized void setNextAction(UnitAction nextAction) {
        this.nextAction = nextAction;
    }
	
    public void setTeam(int team) {
        this.team = team;
    }

    public ClientSocket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public int getLivesNumber() {
        return livesNumber;
    }

    public void setLivesNumber(int livesNumber) {
        this.livesNumber = livesNumber;
    }

    public int getLeftDelayForMovement() {
        return leftDelayForMovement;
    }

    public void setLeftDelayForMovement(int leftDelayForMovement) {
        this.leftDelayForMovement = leftDelayForMovement;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Unit() {
        this.team = -1;
        this.livesNumber = 1;
		this.setMaxHealth(100);
        this.setHealth(100);
        this.weapon = -1;
    }

    public void go(int angle) {
        int currentPositionX = this.getPositionX();
        int currentPositionY = this.getPositionY();
        switch (angle) {
            case ANGLE_DOWN:
                this.setPositionY(currentPositionY + 1);
                break;
            case ANGLE_LEFT:
                this.setPositionX(currentPositionX - 1);
                break;
            case ANGLE_RIGHT:
                this.setPositionX(currentPositionX + 1);
                break;
            case ANGLE_UP:
                this.setPositionY(currentPositionY - 1);
                break;
        }
    }
}
