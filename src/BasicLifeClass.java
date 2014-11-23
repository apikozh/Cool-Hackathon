/**
 * Created by Victor Dichko on 22.11.14.
 */
public class BasicLifeClass {

    private int health;
    private int maxHealth;

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
	
    public void setHealth(int health) {
        this.health = health;
		if (health > maxHealth)
			this.health = maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
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
