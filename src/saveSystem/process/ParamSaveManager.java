package saveSystem.process;

import config.GameConfiguration;
import config.GameParameter;
import org.apache.log4j.Logger;
import saveSystem.ParamSave;

import java.io.*;

public class ParamSaveManager {

    private static final Logger logger = Logger.getLogger(ParamSaveManager.class);

    private ParamSaveManager(){}

    private void saveProcedure(ParamSave ps) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(GameConfiguration.SAVE_FILE_PATH + "userParam.save"))) {
            oos.writeObject(ps);
        }
    }
/*
    private ParamSave loadProcedure() throws IOException {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(GameConfiguration.SAVE_FILE_PATH + "userParam.save"));
            ParamSave pm = ParamSave.create(ois);
        }
    }
*/
    public void writeParamFile() {
        try {
            saveProcedure(ParamSave.create(GameParameter.getInstance()));
        } catch (IOException e) {
            logger.error(e.getMessage() + ": could not create or update \"userParam.save\", " + e.getCause());
        }
    }

    public void applyParamFileToGame(ParamSave ps){

    }

    public static ParamSaveManager create(){
        return new ParamSaveManager();
    }
}
