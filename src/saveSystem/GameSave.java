package saveSystem;

import engine.Map;
import java.io.Serializable;

public class GameSave implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String name;
    private final Map gameState;

    private GameSave(String fileName, Map gameState) {
        this.name = fileName;
        this.gameState = gameState;
    }
    //Create

    public static GameSave create(String fileName) {
        return new GameSave(fileName, Map.getInstance());
    }

    //Getters

    public String getName() {
        return this.name;
    }

    public Map getGameState() {
        return this.gameState;
    }
}
