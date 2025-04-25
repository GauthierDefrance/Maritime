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
 * @version 0.3
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

    public ArrayList<Boat> getLstBoat() {
        return lstBoat;
    }

    public ArrayList<Harbor> getLstHarbor() {
        return lstHarbor;
    }

    public ArrayList<SeaRoad> getLstSeaRouts() {
        return lstSeaRouts;
    }

    public ArrayList<Fleet> getLstFleet() {
        return lstFleet;
    }

    public String getColor() {
        return color;
    }

    public int getRelationship(Faction faction) {
        return relationship.get(faction);
    }

    public int getAmountCurrency() {
        return currencySimpleEntry.getValue();
    }

    public Currency getCurrency() {
        return currencySimpleEntry.getKey();
    }

    //Setters

    public void setLstBoat(ArrayList<Boat> lstBoat) {
        this.lstBoat = lstBoat;
    }

    public void setLstHarbor(ArrayList<Harbor> lstHarbor) {
        this.lstHarbor = lstHarbor;
    }

    public void setLstSeaRouts(ArrayList<SeaRoad> lstSeaRouts) {this.lstSeaRouts = lstSeaRouts;}

    public void setLstFleet(ArrayList<Fleet> lstFleet) {this.lstFleet = lstFleet;}

    public void setColor(String color) {this.color = color;}

    public void setRelationship(Faction faction,int relationship) {
        this.relationship.put(faction,relationship);
    }

    public void setAmountCurrency(int nb) {
        this.currencySimpleEntry.setValue(nb);
    }

    public void setCurrency(Currency currency) {
        this.currencySimpleEntry = new AbstractMap.SimpleEntry<>(currency,0);
    }

    public void addAmountCurrency(int nb) {
        this.currencySimpleEntry.setValue(this.currencySimpleEntry.getValue()+nb);
    }

    public void subtractAmountCurrency(int nb) {
        this.currencySimpleEntry.setValue(this.currencySimpleEntry.getValue()-nb);
    }

    //Content Handler

    /**
     * Method that add a harbor to a faction.
     * @param harbor the {@link Harbor} that will be added
     */
    public void addHarbor(Harbor harbor) {
        harbor.setColor(this.getColor());
        this.lstHarbor.add(harbor);
        this.lstFleet.add(new Fleet());
    }

    /**
     * Method that add a given boat to a Faction.
     * @param boat the {@link Boat} that will be added
     */
    public void addBoat(Boat boat) {this.lstBoat.add(boat);}

    /**
     * Method add a given Fleet to a Faction
     * @param fleet the {@link Fleet} that will be added
     */
    public void addFleet(Fleet fleet) {this.lstFleet.add(fleet);}

    /**
     * Method add a given SeaRoad to a Faction
     * @param seaRoad the {@link SeaRoad} that will be added
     */
    public void addSeaRoad(SeaRoad seaRoad) {this.lstSeaRouts.add(seaRoad);}

    /**
     * Method that remove a Boat from a given Faction
     * @param Boat the {@link Boat} that will be removed
     */
    public void removeBoat(Boat Boat) {
        this.lstBoat.remove(Boat);
        for (Fleet fleet : lstFleet)fleet.getArrayListBoat().remove(Boat);
        for (Harbor harbor : lstHarbor)harbor.getHashMapBoat().remove(Boat);
    }

    /**
     * Method that remove a harbor from a given Faction
     * @param harbor the {@link Harbor} that will be removed
     */
    public void removeHarbor(Harbor harbor) {
        harbor.setColor("");
        this.lstHarbor.remove(harbor);
    }

    /**
     * Method that remove a fleet from a given Faction
     * @param fleet the {@link Fleet} that will be removed
     */
    public void removeFleet(Fleet fleet) {this.lstFleet.remove(fleet);}

    /**
     * Method that return the name of the faction.
     * @return {@link String} The name of the faction
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}