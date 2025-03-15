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
 * @version 0.4
 */
public class GameSaveManager {

    private static final Logger log = Logger.getLogger(GameSaveManager.class);

    /**
     * Private Constructor
     */
    private GameSaveManager() {}

    /**
     * Procedure used to save files
     * @param sv GameSave Object to be written
     * @return Completed operation completed message
     */
    private String saveProcedure(GameSave sv) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE_PATH + sv.getName()))) {
            oos.writeObject(sv);
            return "Game saved successfully as "+sv.getName();
        } catch (IOException e) {
            return e.getMessage()+" Could not save game.";
        }
    }

    /**
     * Deserialize a SaveFile
     * @param fileID identifier of the file to fetch
     * @return GameSave Object (can be null if fileID doesn't have a correspondance)
     */
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

    /**
     * Make a new SaveFile using a fileID
     * @param fileID identifier of the to be made file
     * @return Completed operation completed message
     */
    public String saveNewGame(int fileID) {
        return saveProcedure(GameSave.create("GameSave0"+fileID+".ser"));
    }

    /**
     * Overwrite a preexisting SaveFile using a GameSave Object
     * @param sv GameSave Object to be written
     * @return Completed operation completed message
     */
    public String overwriteSaveFile(GameSave sv) {
        return saveProcedure(sv);
    }

    /**
     * Load the game Using the GameSave Object state
     * @param sv GameSave Object
     */
    public void loadGame(GameSave sv) {
        Map.setInstance(sv.getGameState());
        GUILoader.loadMainGame();
    }

    /**
     * Create a new GameSaveManager and allows operations on it
     * @return GameSaveManager
     */
    public static GameSaveManager create() {
        return new GameSaveManager();
    }

}