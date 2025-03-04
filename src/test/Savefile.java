package test;

import config.MapBuilder;
import java.io.Serializable;

public class Savefile implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private MapBuilder gameState;

    public Savefile(String fileName, MapBuilder gameState) {
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
    public static Savefile createSavefile(String fileName, MapBuilder gameState) {
        return new Savefile(fileName, gameState);
    }
}
