/**
 * Created by Victor Dichko on 22.11.14.
 */
public class Unit extends DynamicObject {
    private String name;

    private int angle;

    private Weapon weapon;

    private ClientSocket clientSocket;

    public String getName() {
        return name;
    }

    public int getAngle() {
        return angle;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public ClientSocket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }
}
