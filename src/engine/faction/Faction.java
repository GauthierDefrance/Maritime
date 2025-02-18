package engine.faction;

import engine.entity.Harbor;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;
import engine.trading.SeaRoad;

import java.util.ArrayList;

/**
 * @author @Kenan Ammad
 * @author Zue Jack-Arthur
 * Classe Faction
 * @version 0.3
 */

public class Faction {
    private ArrayList<Boat> lstBoat;
    private ArrayList<Harbor> lstHarbor;
    private ArrayList<SeaRoad> lstSeaRouts;
    private ArrayList<Fleet> lstFleet;
    private String color;

    public Faction(String color){
        this.lstBoat = new ArrayList<>();
        this.lstHarbor = new ArrayList<>();
        this.lstSeaRouts = new ArrayList<>();
        this.lstFleet = new ArrayList<>();
        this.color = color;
    }

    //Getters

    public ArrayList<Boat> getLstBoat() {return lstBoat;}

    public ArrayList<Harbor> getLstHarbor() {return lstHarbor;}

    public ArrayList<SeaRoad> getLstSeaRouts() {return lstSeaRouts;}

    public ArrayList<Fleet> getLstFleet() {return lstFleet;}

    public String getColor() {return color;}

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

    //Content Handler

    public void addHarbor(Harbor harbor) {
        harbor.setColor(this.getColor());
        this.lstHarbor.add(harbor);}

    public void addBoat(Boat boat) {this.lstBoat.add(boat);}

    public void addFleet(Fleet fleet) {this.lstFleet.add(fleet);}

    public void addSeaRoad(SeaRoad seaRoad) {this.lstSeaRouts.add(seaRoad);}

    public void removeBoat(Boat Boat) {this.lstBoat.remove(Boat);}

    public void removeHarbor(Harbor harbor) {
        harbor.setColor("");
        this.lstHarbor.remove(harbor);
    }

    public void removeFleet(Fleet fleet) {this.lstFleet.remove(fleet);}
}