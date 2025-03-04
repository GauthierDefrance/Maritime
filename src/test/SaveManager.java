package test;

import config.MapBuilder;
import java.io.*;

/**
 * Class handling saving and loading Save Files
 * @see Savefile
 * @author Zue Jack-Arthur
 * @version 0.1
 */
public class SaveManager {

    private static void saveProcedure(Savefile sv) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("../" + sv.getName()))) {
            oos.writeObject(sv);
        }
    }

    public static String saveNewGame(String fileName, MapBuilder gameState) {
         Savefile sv = Savefile.createSavefile(fileName, gameState);
         try {
             saveProcedure(sv);
             return "Game saved successfully as "+sv.getName();
         } catch (IOException e) {
             return e.getMessage()+" Could not save game.";
         }
    }

    public static String overwriteGame(Savefile sv, MapBuilder gameState) {
        sv.setGameState(gameState);
        try {
            saveProcedure(sv);
            return "Game saved successfully as "+sv.getName();
        } catch (IOException e) {
            return e.getMessage()+" Could not save game.";
        }
    }

    public MapBuilder loadGame(Savefile sv) {
        return sv.getGameState(); //can be null, should be handled
    }

    public static Savefile fetchSavefile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("../" + fileName))){
            return (Savefile) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return Savefile.createSavefile(fileName, null);
        }
    }
}