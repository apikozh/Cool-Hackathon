/**
 * Created by Victor Dichko on 22.11.14.
 */
public class GameMap {
    private MapObject gameMap[][];

    public GameMap(int height, int width) {
        gameMap = new MapObject[height][width];
    }

    public int getWidth() {
        return gameMap.length;
    }

    public int getHeight() {
        return gameMap[0].length;
    }

    public MapObject getElement(int positionX, int positionY) {
        return gameMap[positionY][positionX];
    }

    public void setElement(int positionX, int positionY, MapObject mapObject) {
        gameMap[positionY][positionX] = mapObject;
    }
}
