package battleengine.entity;
import battleengine.factory.SpawnZoneFactory;
import battleengine.tools.DeepCopy;
import config.GameConfiguration;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that represents a Battle, it contains 2 Fleet that represent
 * the two team of enemy that will fight.
 * @author Gauthier Defrance
 * @version 0.4
 */
public class Battle {

    private ArrayList<Bullet> LstBullets;

    private HashMap<Boat, Integer> ReloadingHashMap;

    private HashMap<Boat, Boat> HunterPreyHashMap;

    private Fleet originalA;
    private Fleet originalB;

    private Fleet teamA;
    private Fleet teamB;

    private Fleet BoatsInBattleA;
    private Fleet BoatsInBattleB;

    private ArrayList<Boat> LstBoatsToPlace;
    private ArrayList<Boat> LstBoatsCurrentlyBeingPlaced;

    private Boat currentBoat;
    private Point currentBoatPoint;

    private SpawnZone spawnzone;
    private SpawnZone spawnzoneEnnemy;

    private boolean isInPlacingMode;

    /**
     * Constructor of the class Battle
     * @param fleetA
     * @param fleetB
     */
    public Battle(Fleet fleetA, Fleet fleetB) {
        this.originalA = fleetA;
        this.originalB = fleetB;
        this.teamA = DeepCopy.copyFleet(fleetA);
        this.teamB = DeepCopy.copyFleet(fleetB);
        this.LstBoatsToPlace = this.teamA.getArrayListBoat();
        this.LstBoatsCurrentlyBeingPlaced = new ArrayList<Boat>();
        this.HunterPreyHashMap = new HashMap<Boat, Boat>();
        this.ReloadingHashMap = new HashMap<Boat, Integer>();
        LstBullets=new ArrayList<Bullet>();

        for(Boat boat : this.teamA.getArrayListBoat()) {
            HunterPreyHashMap.put(boat, null);
            ReloadingHashMap.put(boat, GameConfiguration.RELOAD_TIME);
        }
        for(Boat boat : this.teamB.getArrayListBoat()) {
            HunterPreyHashMap.put(boat, null);
            ReloadingHashMap.put(boat, GameConfiguration.RELOAD_TIME);
        }

        this.BoatsInBattleA= new Fleet();
        this.BoatsInBattleB = new Fleet();
        this.spawnzone = SpawnZoneFactory.buildDefaultSpawnZone();
        this.spawnzoneEnnemy = SpawnZoneFactory.buildDefaultEnnemySpawnZone();
        this.isInPlacingMode = true;
        this.currentBoatPoint = new Point(0,0);


    }

    public HashMap<Boat, Integer> getReloadingHashMap() {
        return ReloadingHashMap;
    }

    /**
     * Getter of the Hashmap of hunter prey,
     * Hunters are keys and the prey are values.
     * @return The {@link HashMap<Boat,Boat>} of Hunter & Prey
     */
    public HashMap<Boat, Boat> getHunterPreyHashMap() { return HunterPreyHashMap;}

    /**
     * Gets the original fleet A.
     *
     * @return the original fleet A
     */
    public Fleet getOriginalA() { return originalA; }

    /**
     * Gets the original fleet B.
     *
     * @return the original fleet B
     */
    public Fleet getOriginalB() { return originalB; }

    /**
     * Gets team A's fleet.
     *
     * @return team A's fleet
     */
    public Fleet getTeamA() { return teamA; }

    /**
     * Gets team B's fleet.
     *
     * @return team B's fleet
     */
    public Fleet getTeamB() { return teamB; }

    /**
     * Gets the boats currently in battle for team A.
     *
     * @return the boats in battle for team A
     */
    public Fleet getBoatsInBattleA() { return BoatsInBattleA; }

    /**
     * Gets the boats currently in battle for team B.
     *
     * @return the boats in battle for team B
     */
    public Fleet getBoatsInBattleB() { return BoatsInBattleB; }

    /**
     * Gets the list of boats to be placed.
     *
     * @return the list of boats to be placed
     */
    public ArrayList<Boat> getLstBoatsToPlace() { return LstBoatsToPlace; }

    /**
     * Gets the list of boats currently being placed.
     *
     * @return the list of boats currently being placed
     */
    public ArrayList<Boat> getLstBoatsCurrentlyBeingPlaced() { return LstBoatsCurrentlyBeingPlaced; }

    /**
     * Gets the current boat.
     *
     * @return the current boat
     */
    public Boat getCurrentBoat() { return currentBoat; }

    /**
     * Gets the spawn zone.
     *
     * @return the spawn zone
     */
    public SpawnZone getSpawnzone() { return spawnzone; }

    /**
     * Gets the enemy spawn zone.
     *
     * @return the enemy spawn zone
     */
    public SpawnZone getSpawnzoneEnnemy() { return spawnzoneEnnemy; }

    /**
     * Checks if in placing mode.
     *
     * @return true if in placing mode, false otherwise
     */
    public boolean isInPlacingMode() { return isInPlacingMode; }

    /**
     * Gets the ArrayList of Bullet
     */
    public ArrayList<Bullet> getLstBullets(){return this.LstBullets;}


    /**
     * sets the ArrayList of Bullet
     * @param LstBullets ArrayList<Bullet>
     */
    public void setLstBullets(ArrayList<Bullet> LstBullets){this.LstBullets = LstBullets;}

    /**
     * Sets the HunterPreyHashMap
     * @param hashMap
     */
    public void setHunterPreyHashMap(HashMap<Boat, Boat> hashMap) { this.HunterPreyHashMap=hashMap;}

    /**
     * Sets the original fleet A.
     *
     * @param fleet the new original fleet A
     */
    public void setOriginalA(Fleet fleet) { this.originalA = fleet; }

    /**
     * Sets the original fleet B.
     *
     * @param fleet the new original fleet B
     */
    public void setOriginalB(Fleet fleet) { this.originalB = fleet; }

    /**
     * Sets team A's fleet.
     *
     * @param fleet the new fleet for team A
     */
    public void setTeamA(Fleet fleet) { this.teamA = fleet; }

    /**
     * Sets team B's fleet.
     *
     * @param fleet the new fleet for team B
     */
    public void setTeamB(Fleet fleet) { this.teamB = fleet; }

    /**
     * Sets the boats in battle for team A.
     *
     * @param fleet the new boats in battle for team A
     */
    public void setBoatsInBattleA(Fleet fleet) { this.BoatsInBattleA = fleet; }

    /**
     * Sets the boats in battle for team B.
     *
     * @param fleet the new boats in battle for team B
     */
    public void setBoatsInBattleB(Fleet fleet) { this.BoatsInBattleB = fleet; }

    /**
     * Sets the list of boats to be placed.
     *
     * @param lstBoat the new list of boats to be placed
     */
    public void setLstBoatsToPlace(ArrayList<Boat> lstBoat) { this.LstBoatsToPlace = lstBoat; }

    /**
     * Sets the list of boats currently being placed.
     *
     * @param lstBoat the new list of boats currently being placed
     */
    public void setLstBoatsCurrentlyBeingPlaced(ArrayList<Boat> lstBoat) { this.LstBoatsCurrentlyBeingPlaced = lstBoat; }

    /**
     * Sets the current boat.
     *
     * @param boat the new current boat
     */
    public void setCurrentBoat(Boat boat) { this.currentBoat = boat; }

    /**
     * Sets the spawn zone.
     *
     * @param spawnzone the new spawn zone
     */
    public void setSpawnzone(SpawnZone spawnzone) { this.spawnzone = spawnzone; }

    /**
     * Sets the enemy spawn zone.
     *
     * @param spawnzoneEnnemy the new enemy spawn zone
     */
    public void setSpawnzoneEnnemy(SpawnZone spawnzoneEnnemy) { this.spawnzoneEnnemy = spawnzoneEnnemy; }

    /**
     * Sets the placing mode.
     *
     * @param bool true to enable placing mode, false to disable
     */
    public void setPlacingMode(boolean bool) { this.isInPlacingMode = bool; }

    public Point getCurrentBoatPoint() {
        return currentBoatPoint;
    }

    public void setCurrentBoatPoint(Point currentBoatPoint) {
        this.currentBoatPoint.setLocation(currentBoatPoint);
    }
}