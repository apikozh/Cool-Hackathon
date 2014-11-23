/**
 * Created by Victor Dichko on 22.11.14.
 */
 
public class BonusMedikit extends Bonus {
    	
	private int healthIncarace;

	public BonusMedikit() {
		super();
		this.healthIncarace = 30;
		this.type = MEDIKIT;
	}
	
	public BonusMedikit(int healthIncarace) {
		super();
		this.healthIncarace = healthIncarace;
		this.type = MEDIKIT;
	}
	
    public int getHealthIncarace() {
        return healthIncarace;
    }

    public void setHealthIncarace(int healthIncarace) {
        this.healthIncarace = healthIncarace;
    }
	
	public void apply(Unit unit) {
		unit.setHealth(unit.getHealth() + healthIncarace);
	}
	
}
