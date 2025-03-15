package saveSystem.process;

import engine.Map;
import gui.utilities.GUILoader;
import org.apache.log4j.Logger;
import saveSystem.GameSave;
import static config.GameConfiguration.SAVE_FILE_PATH;
import java.io.*;

/**
 * Class handling saving and loading Save Files
 * @see GameSave
 * @author Zue Jack-Arthur
 * @version 0.2
 */
public class GameSaveManager {

    private static final Logger log = Logger.getLogger(GameSaveManager.class);
    private static GameSaveManager instance;

    private GameSaveManager() {}

    private String saveProcedure(GameSave sv) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE_PATH + sv.getName()))) {
            oos.writeObject(sv);
            return "Game saved successfully as "+sv.getName();
        } catch (IOException e) {
            return e.getMessage()+" Could not save game.";
        }
    }

    public GameSave fetchSaveFile(int fileID) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE_PATH + "GameSave0"+fileID+".ser"))){
            Object obj = ois.readObject();
            if (obj instanceof GameSave) {
                return (GameSave) obj;
            }
            log.info(obj+" is not a GameSave object");
            return null;
        } catch (IOException | ClassNotFoundException e) {
            log.info(e.getMessage() +" Could not fetch save file : "+fileID+" - "+e.getCause());
            return null;
        }
    }

    public String saveNewGame(int fileID) {
        return saveProcedure(GameSave.create("GameSave0"+fileID+".ser"));
    }

    public String overwriteSaveFile(GameSave sv) {
        return saveProcedure(sv);
    }

    public void loadGame(GameSave sv) {
        Map.setInstance(sv.getGameState());
        GUILoader.loadMainGame();
    }


    public static GameSaveManager create() {
        return new GameSaveManager();
    }

    public static GameSaveManager getInstance() {
        if (instance == null) {
            instance = new GameSaveManager();
        } return instance;
    }

}