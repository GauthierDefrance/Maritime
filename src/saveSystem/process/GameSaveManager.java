package saveSystem.process;

import config.GameConfiguration;
import config.MapBuilder;
import saveSystem.GameSave;

import java.io.*;

/**
 * Class handling saving and loading Save Files
 * @see GameSave
 * @author Zue Jack-Arthur
 * @version 0.2
 */
public class GameSaveManager {

    private GameSaveManager() {}

    private void saveProcedure(GameSave sv) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(GameConfiguration.SAVE_FILE_PATH + sv.getName()))) {
            oos.writeObject(sv);
        }
    }

    public String saveNewGame(String fileName, MapBuilder gameState) {
         GameSave sv = GameSave.createSavefile(fileName, gameState);
         try {
             saveProcedure(sv);
             return "Game saved successfully as "+sv.getName();
         } catch (IOException e) {
             return e.getMessage()+" Could not save game.";
         }
    }

    public String overwriteGame(GameSave sv, MapBuilder gameState) {
        sv.setGameState(gameState);
        try {
            saveProcedure(sv);
            return "Game saved successfully as "+sv.getName();
        } catch (IOException e) {
            return e.getMessage()+" Could not save game.";
        }
    }

    public MapBuilder loadGame(GameSave sv) {
        return sv.getGameState();
    }

    public GameSave fetchSaveFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(GameConfiguration.SAVE_FILE_PATH + fileName))){
            return (GameSave) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public static GameSaveManager create() {
        return new GameSaveManager();
    }

}