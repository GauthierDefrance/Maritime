package engine.data.faction;

import config.GameConfiguration;
import engine.data.entity.Harbor;
import engine.data.entity.boats.Boat;
import engine.data.Fleet;
import engine.data.trading.Currency;
import engine.data.trading.SeaRoad;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class representing one of the many Faction in a game.
 * @author Kenan Ammad
 * @author Zue Jack-Arthur
 * Classe Faction
 * @version 1.0
 */
public class Faction implements Serializable {
    private ArrayList<Boat> lstBoat;
    private ArrayList<Harbor> lstHarbor;
    private ArrayList<SeaRoad> lstSeaRouts;
    private ArrayList<Fleet> lstFleet;
    private String color;
    private String name;
    private HashMap<Faction,Integer> relationship;
    private AbstractMap.SimpleEntry<Currency, Integer> currencySimpleEntry;

    public Faction(String color,String name){
        this.lstBoat = new ArrayList<>();
        this.lstHarbor = new ArrayList<>();
        this.lstSeaRouts = new ArrayList<>();
        this.lstFleet = new ArrayList<>();
        this.color = color;
        this.name = name;
        this.relationship = new HashMap<>();
        this.currencySimpleEntry = new AbstractMap.SimpleEntry<>(GameConfiguration.GOLD,0);
    }

    //Getters

    /**
     * Gets the list of boats owned by the faction.
     * @return {@link ArrayList} of {@link Boat}.
     */
    public ArrayList<Boat> getLstBoat() {
        return lstBoat;
    }

    /**
     * Gets the list of harbors owned by the faction.
     * @return {@link ArrayList} of {@link Harbor}.
     */
    public ArrayList<Harbor> getLstHarbor() {
        return lstHarbor;
    }

    /**
     * Gets the list of sea routes controlled by the faction.
     * @return {@link ArrayList} of {@link SeaRoad}.
     */
    public ArrayList<SeaRoad> getLstSeaRouts() {
        return lstSeaRouts;
    }

    /**
     * Gets the list of fleets under the faction's control.
     * @return {@link ArrayList} of {@link Fleet}.
     */
    public ArrayList<Fleet> getLstFleet() {
        return lstFleet;
    }

    /**
     * Gets the faction's representative color.
     * @return {@link String} The color.
     */
    public String getColor() {
        return color;
    }

    /**
     * Gets the relationship level between this faction and another.
     * @param faction The {@link Faction} to check relationship with.
     * @return {@link Integer} The relationship value.
     */
    public int getRelationship(Faction faction) {
        return relationship.get(faction);
    }

    /**
     * Gets the current amount of currency the faction holds.
     * @return {@link Integer} The currency amount.
     */
    public int getAmountCurrency() {
        return currencySimpleEntry.getValue();
    }

    /**
     * Gets the type of currency used by the faction.
     * @return {@link Currency} The currency type.
     */
    public Currency getCurrency() {
        return currencySimpleEntry.getKey();
    }

    /**
     * Gets the name of the faction.
     * @return {@link String} The faction name.
     */
    public String getName() {
        return name;
    }


    //Setters

    /**
     * Sets the list of boats for the faction.
     * @param lstBoat {@link ArrayList} of {@link Boat}.
     */
    public void setLstBoat(ArrayList<Boat> lstBoat) {
        this.lstBoat = lstBoat;
    }

    /**
     * Sets the list of harbors for the faction.
     * @param lstHarbor {@link ArrayList} of {@link Harbor}.
     */
    public void setLstHarbor(ArrayList<Harbor> lstHarbor) {
        this.lstHarbor = lstHarbor;
    }

    /**
     * Sets the list of sea routes for the faction.
     * @param lstSeaRouts {@link ArrayList} of {@link SeaRoad}.
     */
    public void setLstSeaRouts(ArrayList<SeaRoad> lstSeaRouts) {
        this.lstSeaRouts = lstSeaRouts;
    }

    /**
     * Sets the list of fleets for the faction.
     * @param lstFleet {@link ArrayList} of {@link Fleet}.
     */
    public void setLstFleet(ArrayList<Fleet> lstFleet) {
        this.lstFleet = lstFleet;
    }

    /**
     * Sets the color representing the faction.
     * @param color {@link String} representing the color.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Updates the relationship value with a specific faction.
     * @param faction {@link Faction} to update.
     * @param relationship The new relationship value.
     */
    public void setRelationship(Faction faction, int relationship) {
        this.relationship.put(faction, relationship);
    }

    /**
     * Sets the amount of currency the faction holds.
     * @param nb {@link Integer} New amount of currency.
     */
    public void setAmountCurrency(int nb) {
        this.currencySimpleEntry.setValue(nb);
    }

    /**
     * Sets the currency type used by the faction.
     * Initializes the value to 0.
     * @param currency {@link Currency} The currency type.
     */
    public void setCurrency(Currency currency) {
        this.currencySimpleEntry = new AbstractMap.SimpleEntry<>(currency, 0);
    }

    /**
     * Sets the name of the faction.
     * @param name {@link String} The faction name.
     */
    public void setName(String name) {
        this.name = name;
    }


    //Content Handler

    /**
     * Adds a specified amount of currency to the faction.
     * @param nb {@link Integer} The amount to add.
     */
    public void addAmountCurrency(int nb) {
        this.currencySimpleEntry.setValue(this.currencySimpleEntry.getValue() + nb);
    }

    /**
     * Subtracts a specified amount of currency from the faction.
     * @param nb {@link Integer} The amount to subtract.
     */
    public void subtractAmountCurrency(int nb) {
        this.currencySimpleEntry.setValue(this.currencySimpleEntry.getValue() - nb);
    }

    /**
     * Adds a harbor to the faction and associates a new fleet with it.
     * Also sets the harbor's color to the faction's color.
     * @param harbor The {@link Harbor} to be added.
     */
    public void addHarbor(Harbor harbor) {
        harbor.setColor(this.getColor());
        this.lstHarbor.add(harbor);
        this.lstFleet.add(new Fleet());
    }

    /**
     * Adds a boat to the faction.
     * @param boat The {@link Boat} to be added.
     */
    public void addBoat(Boat boat) {
        this.lstBoat.add(boat);
    }

    /**
     * Adds a fleet to the faction.
     * @param fleet The {@link Fleet} to be added.
     */
    public void addFleet(Fleet fleet) {
        this.lstFleet.add(fleet);
    }

    /**
     * Adds a sea road to the faction.
     * @param seaRoad The {@link SeaRoad} to be added.
     */
    public void addSeaRoad(SeaRoad seaRoad) {
        this.lstSeaRouts.add(seaRoad);
    }

    /**
     * Removes a boat from the faction and updates all associated fleets and harbors.
     * @param boat The {@link Boat} to be removed.
     */
    public void removeBoat(Boat boat) {
        this.lstBoat.remove(boat);
        for (Fleet fleet : lstFleet) fleet.getArrayListBoat().remove(boat);
        for (Harbor harbor : lstHarbor) harbor.getHashMapBoat().remove(boat);
    }

    /**
     * Removes a harbor from the faction and clears its color.
     * @param harbor The {@link Harbor} to be removed.
     */
    public void removeHarbor(Harbor harbor) {
        harbor.setColor("");
        this.lstHarbor.remove(harbor);
    }

    /**
     * Removes a fleet from the faction.
     * @param fleet The {@link Fleet} to be removed.
     */
    public void removeFleet(Fleet fleet) {
        this.lstFleet.remove(fleet);
    }


}