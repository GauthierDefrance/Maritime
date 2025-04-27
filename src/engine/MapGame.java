package engine;

import engine.data.entity.Harbor;
import engine.data.entity.boats.Boat;
import engine.data.faction.Faction;
import engine.data.faction.Pirate;
import engine.data.faction.Player;
import engine.data.graph.GraphPoint;
import gui.PopUp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * @author Kenan Ammad
 * @version 0.3
 */
public class MapGame implements Serializable {
    private static MapGame instance;
    private HashMap<Boat, Boat> hunterPreyHashMap;
    private ArrayList<GraphPoint> mapGraphPoint;
    private ArrayList<Harbor> lstHarbor;
    private ArrayList<Faction> lstBotFaction;
    private ArrayList<Faction> lstFaction;
    private Player player;
    private Pirate pirate;
    private boolean timeStop;
    private double time;
    private boolean godMode;
    private boolean noSpawnMode;
    private ArrayList<PopUp> lstPopUp;

    private MapGame(){
        timeStop = false;
        time = 0;
        godMode = false;
        noSpawnMode = false;
        lstPopUp = new ArrayList<>();
        lstHarbor = new ArrayList<>();
        lstFaction = new ArrayList<>();
        lstBotFaction = new ArrayList<>();
        mapGraphPoint = new ArrayList<>();
        hunterPreyHashMap = new HashMap<>();
        player = new Player("blue","Player");
        pirate = new Pirate("black","Pirate");
    }

    public static synchronized MapGame getInstance() {
        if (instance == null) {
            instance = new MapGame();
        } return instance;
    }

    /**
     * Sets the instance of the MapGame.
     * @param instance The {@link MapGame} instance to be set.
     */
    public static void setInstance(MapGame instance) {
        MapGame.instance = instance;
    }

    /**
     * Gets the list of bot factions in the game.
     * @return {@link ArrayList} The list of {@link Faction} objects representing the bot factions.
     */
    public ArrayList<Faction> getLstBotFaction() {
        return lstBotFaction;
    }

    /**
     * Sets the list of bot factions in the game.
     * @param lstBotFaction {@link ArrayList} The list of {@link Faction} objects to be set as bot factions.
     */
    public void setLstBotFaction(ArrayList<Faction> lstBotFaction) {
        this.lstBotFaction = lstBotFaction;
    }

    /**
     * Gets the list of GraphPoints representing the map.
     * @return {@link ArrayList} The list of {@link GraphPoint} objects representing the map.
     */
    public ArrayList<GraphPoint> getMapGraphPoint() {
        return mapGraphPoint;
    }

    /**
     * Sets the list of GraphPoints representing the map.
     * @param mapGraphPoint {@link ArrayList} The list of {@link GraphPoint} objects to be set as the map.
     */
    public void setMapGraphPoint(ArrayList<GraphPoint> mapGraphPoint) {
        this.mapGraphPoint = mapGraphPoint;
    }

    /**
     * Gets the player currently in the game.
     * @return {@link Player} The {@link Player} object representing the player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the list of factions in the game.
     * @return {@link ArrayList} The list of {@link Faction} objects representing all the factions.
     */
    public ArrayList<Faction> getLstFaction() {
        return lstFaction;
    }

    /**
     * Sets the list of factions in the game.
     * @param lstFaction {@link ArrayList} The list of {@link Faction} objects to be set as factions.
     */
    public void setLstFaction(ArrayList<Faction> lstFaction) {
        this.lstFaction = lstFaction;
    }

    /**
     * Sets the player in the game.
     * @param player {@link Player} The {@link Player} object to be set as the player.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets the list of harbors in the game.
     * @return {@link ArrayList} The list of {@link Harbor} objects representing the harbors.
     */
    public ArrayList<Harbor> getLstHarbor() {
        return lstHarbor;
    }

    /**
     * Sets the list of harbors in the game.
     * @param lstHarbor {@link ArrayList} The list of {@link Harbor} objects to be set as harbors.
     */
    public void setLstHarbor(ArrayList<Harbor> lstHarbor) {
        this.lstHarbor = lstHarbor;
    }

    /**
     * Checks if the time is stopped in the game.
     * @return {@link Boolean} True if time is stopped, false otherwise.
     */
    public boolean isTimeStop() {
        return timeStop;
    }

    /**
     * Sets whether the time is stopped in the game.
     * @param timeStop {@link Boolean} The flag indicating if time should be stopped.
     */
    public void setTimeStop(boolean timeStop) {
        this.timeStop = timeStop;
    }

    /**
     * Gets the list of PopUp messages in the game.
     * @return {@link ArrayList} The list of {@link PopUp} objects representing the pop-up messages.
     */
    public ArrayList<PopUp> getLstPopUp() {
        return lstPopUp;
    }

    /**
     * Sets the list of PopUp messages in the game.
     * @param lstPopUp {@link ArrayList} The list of {@link PopUp} objects to be set.
     */
    public void setLstPopUp(ArrayList<PopUp> lstPopUp) {
        this.lstPopUp = lstPopUp;
    }

    /**
     * Adds a PopUp message to the list.
     * @param popUp {@link PopUp} The {@link PopUp} object to be added.
     */
    public void addPopUp(PopUp popUp) {
        this.lstPopUp.add(popUp);
    }

    /**
     * Removes a PopUp message from the list.
     * @param popUp {@link PopUp} The {@link PopUp} object to be removed.
     */
    public void removePopUp(PopUp popUp) {
        this.lstPopUp.remove(popUp);
    }

    /**
     * Removes a faction from the list of factions and bot factions.
     * @param faction {@link Faction} The {@link Faction} object to be removed.
     */
    public void removeFaction(Faction faction) {
        this.lstFaction.remove(faction);
        this.lstBotFaction.remove(faction);
    }

    /**
     * Adds a GraphPoint to the map.
     * @param graphPoint {@link GraphPoint} The {@link GraphPoint} object to be added.
     */
    public void addGraphPoint(GraphPoint graphPoint) {
        mapGraphPoint.add(graphPoint);
    }

    /**
     * Gets the Hunter-Prey mapping in the game.
     * @return {@link HashMap} A map of {@link Boat} objects where each key is a hunter and each value is the prey.
     */
    public HashMap<Boat, Boat> getHunterPreyHashMap() {
        return hunterPreyHashMap;
    }

    /**
     * Sets the Hunter-Prey mapping in the game.
     * @param hunterPreyHashMap {@link HashMap} The map of {@link Boat} objects representing the hunter-prey relationships.
     */
    public void setHunterPreyHashMap(HashMap<Boat, Boat> hunterPreyHashMap) {
        this.hunterPreyHashMap = hunterPreyHashMap;
    }

    /**
     * Adds a hunter-prey relationship to the map.
     * @param hunter {@link Boat} The hunter {@link Boat}.
     * @param prey {@link Boat} The prey {@link Boat}.
     */
    public void addHunterPreyHashMap(Boat hunter, Boat prey) {
        hunterPreyHashMap.put(hunter, prey);
    }

    /**
     * Removes a hunter from the Hunter-Prey map.
     * @param hunter {@link Boat} The hunter {@link Boat} to be removed.
     */
    public void removeHunterPreyHashMap(Boat hunter) {
        hunterPreyHashMap.remove(hunter);
    }

    /**
     * Checks if God Mode is enabled in the game.
     * @return {@link Boolean} True if God Mode is enabled, false otherwise.
     */
    public boolean isGodMode() {
        return godMode;
    }

    /**
     * Sets the God Mode status.
     * @param godMode {@link Boolean} The flag indicating if God Mode should be enabled or disabled.
     */
    public void setGodMode(boolean godMode) {
        this.godMode = godMode;
    }

    /**
     * Checks if No Spawn Mode is enabled in the game.
     * @return {@link Boolean} True if No Spawn Mode is enabled, false otherwise.
     */
    public boolean isNoSpawnMode() {
        return noSpawnMode;
    }

    /**
     * Sets the No Spawn Mode status.
     * @param noSpawnMode {@link Boolean} The flag indicating if No Spawn Mode should be enabled or disabled.
     */
    public void setNoSpawnMode(boolean noSpawnMode) {
        this.noSpawnMode = noSpawnMode;
    }

    /**
     * Gets the pirate in the game.
     * @return {@link Pirate} The {@link Pirate} object representing the pirate.
     */
    public Pirate getPirate() {
        return pirate;
    }

    /**
     * Sets the pirate in the game.
     * @param pirate {@link Pirate} The {@link Pirate} object to be set.
     */
    public void setPirate(Pirate pirate) {
        this.pirate = pirate;
    }

    /**
     * Gets the current time in the game.
     * @return {@link Double} The current time in the game.
     */
    public double getTime() {
        return time;
    }

    /**
     * Sets the current time in the game.
     * @param time {@link Double} The time to be set in the game.
     */
    public void setTime(double time) {
        this.time = time;
    }

    /**
     * Adds time to the current game time.
     * @param time {@link Double} The amount of time to be added.
     */
    public void addTime(double time) {
        this.time += time;
    }


}
