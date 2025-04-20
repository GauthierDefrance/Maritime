package engine.battleengine.data;
import engine.battleengine.utilities.DeepCopy;
import engine.data.entity.boats.Boat;
import engine.data.Fleet;
import engine.data.faction.Faction;

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

    private Faction factionA;
    private Faction factionB;

    private Fleet teamAOriginal;
    private Fleet teamBOriginal;

    private HashMap<Boat,Boat> CopyToOrignalHashMap;

    private volatile ArrayList<Bullet> LstBulletsteamA;
    private volatile ArrayList<Bullet> LstBulletsteamB;

    private HashMap<Boat, Integer> ReloadingHashMap;
    private HashMap<Boat, Boat> HunterPreyHashMap;
    private HashMap<Boat, Point> HunterPreyPointHashMap;

    private Fleet teamA;
    private Fleet teamB;

    private volatile Fleet BoatsInBattleA;
    private volatile Fleet BoatsInBattleB;

    private Fleet DeadBoatsA;
    private Fleet DeadBoatsB;

    private ArrayList<Boat> LstBoatsToPlace;
    private ArrayList<Boat> LstBoatsCurrentlyBeingPlaced;

    private Boat currentBoat;
    private Boat currentBoat2;
    private Point currentBoatPoint;
    private Point currentBoatPoint2;

    private boolean isInPlacingMode;

    /**
     * Constructor of the class Battle
     * @param fleetA
     * @param fleetB
     */
    public Battle(Faction factionA, Faction factionB, Fleet fleetA, Fleet fleetB) {
        this.factionA = factionA;
        this.factionB = factionB;
        this.teamAOriginal= fleetA;
        this.teamBOriginal= fleetB;
        this.CopyToOrignalHashMap = new HashMap<>();
        this.teamA = DeepCopy.copyFleet(fleetA, CopyToOrignalHashMap);
        this.teamB = DeepCopy.copyFleet(fleetB, CopyToOrignalHashMap);
        this.LstBoatsToPlace = this.teamA.getArrayListBoat();
        this.LstBoatsCurrentlyBeingPlaced = new ArrayList<>();
        this.HunterPreyHashMap = new HashMap<>();
        this.HunterPreyPointHashMap = new HashMap<>();
        this.ReloadingHashMap = new HashMap<>();
        this.LstBulletsteamA= new ArrayList<>();
        this.LstBulletsteamB= new ArrayList<>();
        this.BoatsInBattleA= new Fleet();
        this.BoatsInBattleB = new Fleet();
        this.DeadBoatsA= new Fleet();
        this.DeadBoatsB = new Fleet();
        this.isInPlacingMode = true;
        this.currentBoatPoint = new Point(0,0);
    }

    public Fleet getDeadBoatsA(){
        return this.DeadBoatsA;
    }

    public Fleet getDeadBoatsB(){
        return this.DeadBoatsB;
    }


    public HashMap<Boat, Boat> getCopyToOrignalHashMap() {
        return CopyToOrignalHashMap;
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
     * Gets team A's original fleet.
     *
     * @return team A's fleet
     */
    public Fleet getTeamAOriginal() { return teamAOriginal; }

    /**
     * Gets team B's original fleet.
     *
     * @return team B's fleet
     */
    public Fleet getTeamBOriginal() { return teamBOriginal; }


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
    public Fleet  getBoatsInBattleA() {return BoatsInBattleA; }

    /**
     * Gets the boats currently in battle for team B.
     *
     * @return the boats in battle for team B
     */
    public Fleet getBoatsInBattleB() {return BoatsInBattleB; }

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


    public HashMap<Boat, Point> getHunterPreyPointHashMap() { return HunterPreyPointHashMap;}

    /**
     * Checks if in placing mode.
     *
     * @return true if in placing mode, false otherwise
     */
    public boolean isInPlacingMode() { return isInPlacingMode; }

    /**
     * Sets the HunterPreyHashMap
     * @param hashMap
     */
    public void setHunterPreyHashMap(HashMap<Boat, Boat> hashMap) { this.HunterPreyHashMap=hashMap;}

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

    public synchronized ArrayList<Bullet> getLstBulletsteamA() {
        return LstBulletsteamA;
    }

    public synchronized void setLstBulletsteamA(ArrayList<Bullet> lstBulletsteamA) {
        LstBulletsteamA = lstBulletsteamA;
    }

    public synchronized ArrayList<Bullet> getLstBulletsteamB() {
        return LstBulletsteamB;
    }

    public synchronized void setLstBulletsteamB(ArrayList<Bullet> lstBulletsteamB) {
        LstBulletsteamB = lstBulletsteamB;
    }

    public Faction getFactionA() {
        return factionA;
    }

    public void setFactionA(Faction factionA) {
        this.factionA = factionA;
    }

    public Faction getFactionB() {
        return factionB;
    }

    public void setFactionB(Faction factionB) {
        this.factionB = factionB;
    }

    public Boat getCurrentBoat2() {
        return currentBoat2;
    }

    public void setCurrentBoat2(Boat currentBoat2) {
        this.currentBoat2 = currentBoat2;
    }

    public Point getCurrentBoatPoint2() {
        return currentBoatPoint2;
    }

    public void setCurrentBoatPoint2(Point currentBoatPoint2) {
        this.currentBoatPoint2 = currentBoatPoint2;
    }
}