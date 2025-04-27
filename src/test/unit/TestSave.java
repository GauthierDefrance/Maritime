package test.unit;

import static org.junit.Assert.assertEquals;

import engine.data.entity.boats.Boat;
import engine.data.entity.boats.Standard;
import engine.data.faction.Player;
import engine.data.graph.GraphPoint;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.AfterClass;

import saveSystem.*;
import engine.data.MapGame;
import saveSystem.process.GameSaveManager;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;


/**
 * Unit test that check if the save does indeed save data without loss
 * @author Gauthier Defrance
 * Class TestSave
 * @version 0.1
 */
public class TestSave {
    private static final Logger logger = Logger.getLogger(TestSave.class);
    private MapGame game;

    private GameSave gameSave;
    private GameSave loadedGameSave;

    private GraphPoint testPoint;

    /**
     * Init of the test, create two save that we will test and compare
     */
    @Before
    public void initSave(){
        this.game = MapGame.getInstance();


        // --- Init the Map with a GraphPoint ---
        game.setMapGraphPoint(new ArrayList<GraphPoint>());
        testPoint = new GraphPoint(new Point(),"A");
        game.addGraphPoint(testPoint);


        // --- Init a player with a boat ---
        Player playerTest = new Player("red","Player");
        playerTest.addBoat(new Standard("tested","red",testPoint));
        game.setPlayer(playerTest);


        // --- Saving the current Map  ---
        this.gameSave = GameSave.create("GameSave0-1.ser");
        GameSaveManager saveManager = GameSaveManager.create();
        saveManager.saveNewGame(-1);

        // --- Loading the saved map ---
        loadedGameSave = saveManager.fetchSaveFile(-1);
    }

    /**
     * Here, we test if a GraphPoint from the Map is successfully saved
     */
    @Test
    public void testSaveGraphPoint(){

        //--- check is the gameSave name is the same as the loadedGameSave ---
        assertEquals(gameSave.getName(), loadedGameSave.getName()); //Check if the save name is the same

        //--- Check if the Graph point ID is the same ---
        assertEquals(gameSave.getGameState().getMapGraphPoint().get(0).getIdPoint(), testPoint.getIdPoint());
        assertEquals(loadedGameSave.getGameState().getMapGraphPoint().get(0).getIdPoint(), testPoint.getIdPoint());

        //--- Check if the coordinates of the graph point has stay the same ---
        assertEquals(gameSave.getGameState().getMapGraphPoint().get(0).getPoint().getX(), testPoint.getPoint().getX(), 0.0);
        assertEquals(gameSave.getGameState().getMapGraphPoint().get(0).getPoint().getY(), testPoint.getPoint().getY(), 0.0);
        assertEquals(loadedGameSave.getGameState().getMapGraphPoint().get(0).getPoint().getX(), testPoint.getPoint().getX(), 0.0);
        assertEquals(loadedGameSave.getGameState().getMapGraphPoint().get(0).getPoint().getY(), testPoint.getPoint().getY(), 0.0);

    }

    /**
     * Method that test the save of a boat inside the Player Faction.
     */
    @Test
    public void testSaveBoat(){
        Player PgameSave = gameSave.getGameState().getPlayer();
        Player PLoadedGameSave = loadedGameSave.getGameState().getPlayer();
        Boat savedBoat =  PgameSave.getLstBoat().get(0);
        Boat loadedBoat = PLoadedGameSave.getLstBoat().get(0);

        //--- Check if the player name is the same ---
        assertEquals(PLoadedGameSave.getColor(), PgameSave.getColor());

        //--- Check if the only saved boat is the same ---
        assertEquals(savedBoat.getName(), loadedBoat.getName());
        assertEquals(savedBoat.getColor(), loadedBoat.getColor());
        assertEquals(savedBoat.getPosition().getLocation(), loadedBoat.getPosition().getLocation());

    }


    /**
     * --- MUST BE A STATIC METHOD ---
     * Method that execute itself after the test, it deletes the test save from the disk.
     */
    @AfterClass
    public static void CleanFile(){
        String filename = "src/saveFiles/GameSave0-1.ser";
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        } else {
            logger.warn("File "+filename+" not found !");
        }
    }



}
