import java.util.*;

public class Team {
    private String name;

	ArrayList<Unit> units = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public void addUnit(Unit unit) {
		units.add(unit);
	}

	public void removeUnit(Unit unit) {
		units.remove(unit);
	}
	
}
