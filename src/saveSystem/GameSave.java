package saveSystem;

import config.MapBuilder;
import java.io.Serializable;

public class GameSave implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private MapBuilder gameState;

    private GameSave(String fileName, MapBuilder gameState) {
        name = fileName;
        this.gameState = gameState;
    }

    //Getters

    public String getName() {
        return name;
    }

    public MapBuilder getGameState() {
        return gameState;
    }

    //Setters

    public void setGameState(MapBuilder gameState) {
        this.gameState = gameState;
    }

    //Create

    public static GameSave createSavefile(String fileName, MapBuilder gameState) {
        return new GameSave(fileName, gameState);
    }
}
