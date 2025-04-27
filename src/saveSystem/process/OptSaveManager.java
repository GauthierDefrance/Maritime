package saveSystem.process;

import config.GameOptions;
import org.apache.log4j.Logger;
import static config.GameConfiguration.SAVE_FILE_PATH;

import java.io.*;

/**
 * Handle the loading and saving of GameOptions Objects for future use (ie user defined variable affecting game behaviors)
 * @author Zue Jack-Arthur
 * @version 1.0
 */
public class OptSaveManager {

    private static Logger logger = Logger.getLogger(OptSaveManager.class);

    private OptSaveManager(){}

    /**
     * Build a OptSaveManager and allow operations on it
     * @return OptSaveManager Object
     */
    public static OptSaveManager create(){
        return new OptSaveManager();
    }

    /**
     * try to fetch the "userOpt.ser" file, containing a GameOptions Object
     * @return GameOptions Object or null if "userOpt.ser" doesn't exist
     */
    public GameOptions loadParamFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE_PATH + "userOpt.ser"))){
            return (GameOptions) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            logger.info(e.getMessage() + " : failed to load \"userOpt.ser\" > " + e.getCause());
            return null;
        }
    }

    /**
     * try to make a "userOpt.ser" file, containing an updated GameOptions Object
     */
    public void writeParamFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE_PATH + "userOpt.ser"))) {
            oos.writeObject(GameOptions.getInstance());
        } catch (IOException e) {
            logger.error(e.getMessage() + " : could not create or update \"userOpt.ser\" > " + e.getCause());
        }
    }
}
