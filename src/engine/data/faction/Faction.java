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
    private HashMap<Faction,Integer> relationship;
    private AbstractMap.SimpleEntry<Currency, Integer> currencySimpleEntry;

    public Faction(String color){
        this.lstBoat = new ArrayList<>();
        this.lstHarbor = new ArrayList<>();
        this.lstSeaRouts = new ArrayList<>();
        this.lstFleet = new ArrayList<>();
        this.color = color;
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

    public void addRelationship(Faction faction,int relationship) {
        this.relationship.put(faction,getRelationship(faction)+relationship);
    }

    public void subtractRelationship(Faction faction,int relationship) {
        this.relationship.put(faction,getRelationship(faction)-relationship);
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

    public void addHarbor(Harbor harbor) {
        harbor.setColor(this.getColor());
        this.lstHarbor.add(harbor);}

    public void addBoat(Boat boat) {this.lstBoat.add(boat);}

    public void addFleet(Fleet fleet) {this.lstFleet.add(fleet);}

    public void addSeaRoad(SeaRoad seaRoad) {this.lstSeaRouts.add(seaRoad);}

    public void removeBoat(Boat Boat) {
        this.lstBoat.remove(Boat);
        for (Fleet fleet : lstFleet)fleet.getArrayListBoat().remove(Boat);
        for (Harbor harbor : lstHarbor)harbor.getHashMapBoat().remove(Boat);
    }

    public void removeHarbor(Harbor harbor) {
        harbor.setColor("");
        this.lstHarbor.remove(harbor);
    }

    public void removeFleet(Fleet fleet) {this.lstFleet.remove(fleet);}
}