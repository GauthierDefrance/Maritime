package engine.trading;

import engine.entity.Harbor;
import engine.entity.boats.Fleet;
import engine.faction.Faction;
import engine.process.FactionManager;

import java.io.Serializable;
import java.util.HashMap;

public class TradeOffer implements Serializable {
    private final Harbor startingHarbor;
    private final Harbor targetedHarbor;
    private Fleet concernedFleet;
    private final Faction interlocutor;
    private final HashMap<Resource, Integer> selection;
    private final HashMap<Resource, Integer> demand;
    private boolean validation;
    private boolean abandoned;

    private TradeOffer(Harbor A, Harbor B) {
        this.interlocutor = new FactionManager().getMyFaction(B.getColor());
        this.startingHarbor = A;
        this.targetedHarbor = B;
        this.validation = false;
        this.selection = new HashMap<>();
        this.demand = new HashMap<>();
        this.concernedFleet = new Fleet();
    }

    public static TradeOffer create(Harbor A, Harbor B) {
        return new TradeOffer(A, B);
    }

    //Getters

    public Harbor getStartingHarbor() {
        return startingHarbor;
    }

    public Harbor getTargetedHarbor() {
        return targetedHarbor;
    }

    public Faction getInterlocutor() {
        return interlocutor;
    }

    public HashMap<Resource, Integer> getSelection() {
        return selection;
    }

    public HashMap<Resource, Integer> getDemand() {
        return demand;
    }

    public Fleet getConcernedFleet() {
        return concernedFleet;
    }

    public boolean isValid() {
        return validation;
    }

    public boolean isAbandoned() {
        return abandoned;
    }

    //Setters

    public void setConcernedFleet(Fleet concernedFleet) {
        this.concernedFleet = concernedFleet;
    }

    public void setValid(boolean validation) {
        this.validation = validation;
    }

    public void setAbandoned(boolean abandoned) {
        this.abandoned = abandoned;
    }

    //Basic Logic

    public void defineSelection(Resource selection) {
        if (this.selection.isEmpty()) {
            this.selection.put(selection, 0);
        }
    }

    public void defineDemand(Resource demand) {
        if (this.demand.isEmpty()) {
            this.demand.put(demand, 0);
        }
    }


    private int getNbResource(HashMap<Resource, Integer> side, Resource element) {
        return side.getOrDefault(element, 0);
    }

    public void addToSelection(Resource resource, int quantity) {
        if (this.selection.containsKey(resource)) {
            this.selection.put(resource, this.selection.get(resource) + quantity);
        }
    }

    public void removeFromSelection(Resource resource, int quantity) {
        if (this.selection.containsKey(resource)) {
            this.selection.put(resource, this.selection.get(resource) - quantity);
        }
    }

    public void clearSelection() {
        this.selection.clear();
    }

    public void addToDemand(Resource resource, int quantity) {
        if (this.demand.containsKey(resource)) {
            this.demand.put(resource, this.demand.get(resource) + quantity);
        }
    }

    public void removeFromDemand(Resource resource, int quantity) {
        if (this.demand.containsKey(resource)) {
            this.demand.put(resource, getNbResource(demand, resource) - quantity);
        }
    }

    public void clearDemand() {
        this.demand.clear();
    }
}
