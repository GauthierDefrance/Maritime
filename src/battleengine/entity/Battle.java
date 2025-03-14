package battleengine.entity;
import battleengine.tools.SpawnZoneFactory;
import battleengine.tools.DeepCopy;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;

import java.util.ArrayList;

/**
 * @author Gauthier Defrance
 * @version 0.3
 */
public class Battle {
    private Fleet originalA;
    private Fleet originalB;

    private Fleet teamA;
    private Fleet teamB;

    private Fleet BoatsInBattleA;
    private Fleet BoatsInBattleB;

    private ArrayList<Boat> BoatsToPlace;
    private ArrayList<Boat> BoatsCurrentlyBeingPlaced;

    private Boat currentBoat;

    private SpawnZone spawnzone;
    private SpawnZone spawnzoneEnnemy;

    private boolean isInPlacingMode;

    public Battle(Fleet fleetA, Fleet fleetB) {
        this.originalA = fleetA;
        this.originalB = fleetB;
        this.teamA = DeepCopy.copyFleet(fleetA);
        this.teamB = DeepCopy.copyFleet(fleetB);
        this.BoatsToPlace = this.teamA.getArrayListBoat();
        this.BoatsCurrentlyBeingPlaced = new ArrayList<>();
        this.BoatsInBattleA= new Fleet();
        this.BoatsInBattleB = new Fleet();
        this.spawnzone = SpawnZoneFactory.buildDefaultSpawnZone();
        this.spawnzoneEnnemy = SpawnZoneFactory.buildDefaultEnnemySpawnZone();
        this.isInPlacingMode = true;

    }

    //Getters


    public Fleet getOriginalA() { return originalA; }

    public Fleet getOriginalB() { return originalB; }

    public Fleet getTeamA() { return teamA; }

    public Fleet getTeamB() { return teamB; }

    public Fleet getBoatsInBattleA() { return BoatsInBattleA; }

    public Fleet getBoatsInBattleB() { return BoatsInBattleB; }

    public ArrayList<Boat> getBoatsToPlace() { return BoatsToPlace; }

    public ArrayList<Boat> getBoatsCurrentlyBeingPlaced() { return BoatsCurrentlyBeingPlaced; }

    public Boat getCurrentBoat() { return currentBoat; }

    public SpawnZone getSpawnzone() { return spawnzone; }

    public SpawnZone getSpawnzoneEnnemy() { return spawnzoneEnnemy; }

    public boolean isInPlacingMode() { return isInPlacingMode; }

    public void setOriginalA(Fleet fleet) { this.originalA = fleet; }

    public void setOriginalB(Fleet fleet) { this.originalB = fleet; }

    public void setTeamA(Fleet fleet) { this.teamA = fleet; }

    public void setTeamB(Fleet fleet) { this.teamB = fleet; }

    public void setBoatsInBattleA(Fleet fleet) { this.BoatsInBattleA = fleet; }

    public void setBoatsInBattleB(Fleet fleet) { this.BoatsInBattleB = fleet; }

    public void setBoatsToPlace(ArrayList<Boat> lstBoat) { this.BoatsToPlace = lstBoat; }

    public void setBoatsCurrentlyBeingPlaced(ArrayList<Boat> lstBoat) { this.BoatsCurrentlyBeingPlaced = lstBoat; }

    public void setCurrentBoat(Boat boat) { this.currentBoat = boat; }

    public void setSpawnzone(SpawnZone spawnzone) { this.spawnzone = spawnzone; }

    public void setSpawnzoneEnnemy(SpawnZone spawnzone) { this.spawnzoneEnnemy = spawnzone; }

    public void setPlacingMode(boolean bool) { this.isInPlacingMode = bool; }
}