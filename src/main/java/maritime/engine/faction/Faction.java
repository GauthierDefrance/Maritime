package maritime.engine.faction;

import maritime.engine.entity.*;

import java.util.ArrayList;

public class Faction {
    private ArrayList<Boat> lstBoat;
    private ArrayList<Harbor> lstHarbor;

    public Faction(){
        lstBoat = new ArrayList<>();
        lstHarbor = new ArrayList<>();
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
}