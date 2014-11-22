/**
 * Created by Victor Dichko on 22.11.14.
 */
public class DynamicObject extends BasicLifeClass {
    public final int ANGLE_RIGHT = 0;
    public final int ANGLE_DOWN  = 1;
    public final int ANGLE_LEFT  = 2;
    public final int ANGLE_UP    = 3;

    private int positionX;

    private int positionY;

    private int angle;

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public void setPosition(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
