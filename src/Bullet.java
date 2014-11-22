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
}
