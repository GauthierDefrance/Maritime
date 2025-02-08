package maritime.engine.faction;

import maritime.engine.entity.*;
import maritime.engine.entity.boats.Boat;
import maritime.engine.entity.boats.Fleet;

import java.util.ArrayList;
/**
 * @author @Kenan Ammad
 * Classe Faction
 */
public class Faction {
    private ArrayList<Boat> lstBoat;
    private ArrayList<Harbor> lstHarbor;
    private ArrayList<Fleet> lstFleet;
    private String color;

    public Faction(String color){
        lstFleet = new ArrayList<>();
        lstHarbor = new ArrayList<>();
        lstBoat = new ArrayList<>();
        this.color = color;
    }

    //Getters
    public ArrayList<Boat> getLstBoat() {return lstBoat;}
    public ArrayList<Harbor> getLstHarbor() {return lstHarbor;}
    public ArrayList<Fleet> getLstFleet() {return lstFleet;}
    public String getColor() {return color;}

    //Setters
    public void setLstBoat(ArrayList<Boat> lstBoat) {
        this.lstBoat = lstBoat;
    }
    public void setLstHarbor(ArrayList<Harbor> lstHarbor) {
        this.lstHarbor = lstHarbor;
    }
    public void setColor(String color) {this.color = color;}
    public void setLstFleet(ArrayList<Fleet> lstFleet) {this.lstFleet = lstFleet;}

    //Content Handler
    public void addHarbor(Harbor harbor) {
        harbor.setColor(this.getColor());
        this.lstHarbor.add(harbor);}
    public void addBoat(Boat boat) {this.lstBoat.add(boat);}
    public void addFleet(Fleet fleet) {this.lstFleet.add(fleet);}
    public void removeBoat(Boat Boat) {this.lstBoat.remove(Boat);}
    public void removeHarbor(Harbor harbor) {
        harbor.setColor("");
        this.lstHarbor.remove(harbor);}
    public void removeFleet(Fleet fleet) {this.lstFleet.remove(fleet);}
}