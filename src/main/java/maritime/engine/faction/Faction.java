package maritime.engine.faction;

import maritime.engine.entity.*;

import java.util.ArrayList;

public class Faction {
    private ArrayList<Boat> lstBoat = new ArrayList<>();
    private ArrayList<Harbor> lstHarbor = new ArrayList<>();

    public Faction(){}

    public ArrayList<Boat> getLstBoat() {return lstBoat;}
    public ArrayList<Harbor> getLstHarbor() {return lstHarbor;}

    public void addLstHarbor(Harbor Harbor) {this.lstHarbor.add(Harbor);}
    public void addLstBoat(Boat Boat) {this.lstBoat.add(Boat);}
}
