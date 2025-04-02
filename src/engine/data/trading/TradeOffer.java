package engine.data.trading;

import engine.data.entity.Harbor;
import engine.data.Fleet;
import engine.data.faction.Faction;
import engine.process.manager.FactionManager;

import java.io.Serializable;
import java.util.HashMap;

/**
 * TradeOffer Object represents commercial propositions between the player and other factions
 * @author Zue Jack-Arthur
 * @version 0.5
 */
public class TradeOffer implements Serializable {
    private final Harbor startingHarbor;
    private final Harbor targetedHarbor;
    private final Faction interlocutor;
    private Fleet concernedFleet;
    private HashMap<TradeObject, Integer> selection;
    private HashMap<TradeObject, Integer> demand;
    private double successChance;

    /**
     * Allow the generation of a TradeOffer Object
     * @param A Starting Harbor : the one who will provide
     * @param B Targeted Harbor : the one who will receive
     */
    public TradeOffer(Harbor A, Harbor B) {
        FactionManager factionManager = FactionManager.getInstance();
        this.interlocutor = factionManager.getMyFaction(B.getColor());
        this.startingHarbor = A;
        this.targetedHarbor = B;
        this.selection = new HashMap<>();
        this.demand = new HashMap<>();
        this.concernedFleet = new Fleet();
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

    public HashMap<TradeObject, Integer> getSelection() {
        return selection;
    }

    public HashMap<TradeObject, Integer> getDemand() {
        return demand;
    }



    public Fleet getConcernedFleet() {
        return concernedFleet;
    }

    public double getSuccessChance() {
        return successChance;
    }

    //Setters

    public void setSelection(HashMap<TradeObject, Integer> selection) {
        this.selection = selection;
    }

    public void setDemand(HashMap<TradeObject, Integer> demand) {
        this.demand = demand;
    }

    public void setConcernedFleet(Fleet concernedFleet) {
        this.concernedFleet = concernedFleet;
    }

    public void setSuccessChance(double successChance) {
        this.successChance = successChance;
    }
}
