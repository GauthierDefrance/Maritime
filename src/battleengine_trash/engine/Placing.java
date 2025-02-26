package battleengine_trash.engine;


import battleengine_trash.gui.DashBoardPlacingPanel;
import battleengine_trash.process.SpawnZoneFactory;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;

import javax.swing.*;
import java.util.HashMap;


/**
 * Classe de donnée gardant en mémoire les données relatives au placement des bateaux
 * @author Gauthier Defrance
 * @version 0.2
 */
public class Placing {

    private Fleet BoatToPlace;
    private Fleet BoatCurrentlyBeingPlaced;
    private Fleet PlacedFleet;

    private Boat currentBoat;

    private SpawnZone spawnzone;
    private SpawnZone spawnzoneEnnemy;

    private JPanel buttonPanel;
    private HashMap<Boat, JButton> buttonHashMap;
    private JScrollPane scrollPane;
    private DashBoardPlacingPanel dashBoardPlacingPanel;


    public Placing(Fleet BoatToPlace) {
        this.BoatToPlace = BoatToPlace;
        BoatCurrentlyBeingPlaced = new Fleet();
        PlacedFleet = new Fleet();
        spawnzone = SpawnZoneFactory.buildDefaultSpawnZone();
        spawnzoneEnnemy = SpawnZoneFactory.buildDefaultEnnemySpawnZone();
    }

    public SpawnZone getSpawnzone() { return spawnzone; }
    public SpawnZone getSpawnzoneEnnemy() { return spawnzoneEnnemy; }

    public Boat getCurrentBoat() { return currentBoat; }
    public Fleet getBoatToPlace() { return BoatToPlace; }
    public Fleet getPlacedFleet() { return PlacedFleet; }
    public Fleet getBoatCurrentlyBeingPlaced() { return BoatCurrentlyBeingPlaced; }
    public JPanel getButtonPanel() { return buttonPanel; }
    public JScrollPane getScrollPane() { return scrollPane; }
    public HashMap<Boat, JButton> getButtonHashMap() { return buttonHashMap; }
    public DashBoardPlacingPanel getDashBoardPlacingPanel() { return dashBoardPlacingPanel; }

    public void setCurrentBoat(Boat currentBoat) { this.currentBoat = currentBoat; }
    public void setBoatToPlace(Fleet BoatToPlace) { this.BoatToPlace = BoatToPlace; }
    public void setButtonHashMap(HashMap<Boat, JButton> buttonHashMap) { this.buttonHashMap = buttonHashMap; }
    public void setDashBoardPlacingPanel(DashBoardPlacingPanel dashBoardPlacingPanel) { this.dashBoardPlacingPanel = dashBoardPlacingPanel; }
    public void setButtonPanel(JPanel buttonPanel) { this.buttonPanel = buttonPanel; }
    public void setScrollPane(JScrollPane scrollPane) { this.scrollPane = scrollPane; }

}
