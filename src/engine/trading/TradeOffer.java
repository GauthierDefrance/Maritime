package engine.trading;

import engine.entity.Harbor;
import engine.entity.boats.Fleet;
import engine.faction.Faction;
import engine.process.FactionManager;

import java.io.Serializable;
import java.util.HashMap;

/**
 * TradeOffer Object represents commercial propositions between the player and other factions
 * @author Zue Jack-Arthur
 * @version 0.4
 */
public class TradeOffer implements Serializable {
    private final Harbor startingHarbor;
    private final Harbor targetedHarbor;
    private final Faction interlocutor;
    private Fleet concernedFleet;
    private HashMap<Resource, Integer> selection;
    private HashMap<Resource, Integer> demand;
    private double successChance;

    /**
     * Private Constructor
     * @param A Starting Harbor
     * @param B Targeted Harbor
     */
    private TradeOffer(Harbor A, Harbor B) {
        this.interlocutor = new FactionManager().getMyFaction(B.getColor());
        this.startingHarbor = A;
        this.targetedHarbor = B;
        this.selection = new HashMap<>();
        this.demand = new HashMap<>();
        this.concernedFleet = new Fleet();
    }

    /**
     * Allow the generation of a TradeOffer Object
     * @param A Starting Harbor : the one who will provide
     * @param B Targeted Harbor : the one who will receive
     * @return Built TradeOffer Object
     */
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

    public double getSuccessChance() {
        return successChance;
    }

    //Setters

    public void setSelection(HashMap<Resource, Integer> selection) {
        this.selection = selection;
    }

    public void setDemand(HashMap<Resource, Integer> demand) {
        this.demand = demand;
    }

    public void setConcernedFleet(Fleet concernedFleet) {
        this.concernedFleet = concernedFleet;
    }

    public void setSuccessChance(double successChance) {
        this.successChance = successChance;
    }
}
