/**
 * Created by Victor Dichko on 22.11.14.
 */
public class BasicLifeClass {

    private int health;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void decreaseHealth(int decreaseValue){
        if (health >= 0) {
            if (health - decreaseValue <= 0) {
                health = 0;
            } else {
                health -= decreaseValue;
            }
        }
    }
}
