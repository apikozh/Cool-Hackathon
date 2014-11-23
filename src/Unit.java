/**
 * Created by Victor Dichko on 22.11.14.
 */
public class Unit extends DynamicObject {
    private String name;

    private int livesNumber;

    private Weapon weapon;

    private int team;
	
    private UnitAction nextAction = new UnitAction();

	private ClientSocket clientSocket;

    public String getName() {
        return name;
    }

    public Weapon getWeapon() {
        return weapon;
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

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public synchronized void setNextAction(UnitAction nextAction) {
        this.weapon = weapon;
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
}
