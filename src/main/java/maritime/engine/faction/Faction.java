package maritime.engine.faction;

import maritime.engine.entity.*;

import java.util.ArrayList;
/**
 * @author @Kenan Ammad
 * Classe Faction
 */
public class Faction {
    private ArrayList<Boat> lstBoat;
    private ArrayList<Harbor> lstHarbor;
    private String color;

    public Faction(String color){
        lstBoat = new ArrayList<>();
        lstHarbor = new ArrayList<>();
        this.color = color;
    }

    //Getters
    public ArrayList<Boat> getLstBoat() {return lstBoat;}
    public ArrayList<Harbor> getLstHarbor() {return lstHarbor;}

    //Setters
    public void setLstBoat(ArrayList<Boat> lstBoat) {
        this.lstBoat = lstBoat;
    }
    public void setLstHarbor(ArrayList<Harbor> lstHarbor) {
        this.lstHarbor = lstHarbor;
    }

    //Content Handler
    public void addHarbor(Harbor Harbor) {this.lstHarbor.add(Harbor);}
    public void addBoat(Boat Boat) {this.lstBoat.add(Boat);}
    public void removeBoat(Boat Boat) {this.lstBoat.remove(Boat);}
    public void removeHarbor(Harbor Harbor) {this.lstHarbor.remove(Harbor);}

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}