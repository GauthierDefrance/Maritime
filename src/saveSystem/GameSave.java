package saveSystem;

import engine.data.MapGame;
import java.io.Serializable;

/**
 * In-game representation of a Save
 * @author Zue Jack-Arthur
 * @version 0.5
 */
public class GameSave implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String name;
    private final MapGame gameState;

    /**
     * Private Constructor
     * @param fileName name that should be given to the file
     * @param gameState map Object, container of the associated game state
     */
    private GameSave(String fileName, MapGame gameState) {
        this.name = fileName;
        this.gameState = gameState;
    }

    //Create

    /**
     * Generate a GameSave
     * @param fileName name that should be given to the file
     * @return built GameSave
     */
    public static GameSave create(String fileName) {
        return new GameSave(fileName, MapGame.getInstance());
    }

    //Getters

    /**
     * Typical getter to fetch an attribute, here it "name"
     * @return String representing the Name of this GameSave
     */
    public String getName() {
        return this.name;
    }

    /**
     * Typical getter to fetch an attribute, here it "gameState"
     * @return map Object, container of the associated game state
     */
    public MapGame getGameState() {
        return this.gameState;
    }
}
