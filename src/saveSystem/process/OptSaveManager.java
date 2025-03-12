package saveSystem.process;

import config.GameOptions;
import org.apache.log4j.Logger;
import static config.GameConfiguration.SAVE_FILE_PATH;

import java.io.*;

public class OptSaveManager {

    private static final Logger logger = Logger.getLogger(OptSaveManager.class);

    private OptSaveManager(){}

    public static OptSaveManager create(){
        return new OptSaveManager();
    }

    public GameOptions loadParamFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE_PATH + "userOpt.ser"))){
            return (GameOptions) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            logger.warn(e.getMessage() + " : failed to load \"userOpt.ser\"  " + e.getCause());
            return null;
        }
    }

    public void writeParamFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE_PATH + "userOpt.ser"))) {
            oos.writeObject(GameOptions.getInstance());
        } catch (IOException e) {
            logger.error(e.getMessage() + " : could not create or update \"userOpt.ser\", " + e.getCause());
        }
    }
}
