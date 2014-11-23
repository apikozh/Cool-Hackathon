/**
 * Created by Victor Dichko on 22.11.14.
 */
public class BasicLifeClass {

    private int health;

    private int livesNumber;

    public int getHealth() {
        return health;
    }

    public int getLivesNumber() {
        return livesNumber;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setLivesNumber(int livesNumber) {
        this.livesNumber = livesNumber;
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
