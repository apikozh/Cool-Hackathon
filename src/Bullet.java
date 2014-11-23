/**
 * Created by Victor Dichko on 22.11.14.
 */
public class Bullet extends DynamicObject {

    private BulletType type;

    private int leftDelay;

    public BulletType getType() {
        return type;
    }

    public int getLeftDelay() {
        return leftDelay;
    }

    public void setType(BulletType type) {
        this.type = type;
    }

    public void setLeftDelay(int leftDelay) {
        this.leftDelay = leftDelay;
    }

    public void fly(int angle, GameMap map) {
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
