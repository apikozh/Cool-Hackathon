/**
 * Created by Victor Dichko on 22.11.14.
 */
 
public abstract class Bonus extends StaticObject {
    
	public static final int MEDIKIT = 1;
	
	protected int type;

    public Bonus() {
		setMaxHealth(10);
		setHealth(10);
	}
	
	public int getType() {
        return type;
    }
	
	public abstract void apply(Unit unit);
	
    /*
	public void setType(int type) {
        this.type = type;
    }
	*/
}
