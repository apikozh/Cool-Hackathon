/**
 * Created by Victor Dichko on 23.11.14.
 */
public class UnitAction {
    private int movement;
    private int rotation;
    private boolean shoot;
    private int weapon;

    public int getWeapon() {
        return weapon;
    }

    public void setWeapon(int weapon) {
        this.weapon = weapon;
    }

    public boolean isShooting() {
        return shoot;
    }

    public void setShooting(boolean value) {
        this.shoot = value;
    }

    public int getMovement() {
        return movement;
    }

    public int getRotation() {
        return rotation;
    }

    public void setMovement(int movement) {
        this.movement = movement;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
}
