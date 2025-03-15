package saveSystem;

import engine.Map;
import java.io.Serializable;

public class GameSave implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private Map gameState;

    private GameSave(String fileName, Map gameState) {
        name = fileName;
        this.gameState = gameState;
    }
    //Create

    public static GameSave createSavefile(String fileName, Map gameState) {
        return new GameSave(fileName, gameState);
    }

    //Getters

    public String getName() {
        return name;
    }

    public Map getGameState() {
        return gameState;
    }

    //Setters

    public void setGameState(Map gameState) {
        this.gameState = gameState;
    }
}
