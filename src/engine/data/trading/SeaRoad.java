package engine.data.trading;

import config.GameConfiguration;
import engine.data.entity.Harbor;
import engine.data.Fleet;
import engine.data.graph.GraphPoint;
import engine.utilities.SearchInGraph;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;

/**
 * @author Kenan Ammad
 * @version 1.0
 */
public class SeaRoad implements Serializable {
    private String name;
    private int timer;
    private ArrayList<GraphPoint> path;
    private Fleet fleet;
    private final Harbor sellerHarbor;
    private final Harbor targetHarbor;
    private AbstractMap.SimpleEntry<Resource, Integer> selection;
    private AbstractMap.SimpleEntry<Resource, Integer> demand;
    private final double ratio;

    public SeaRoad(String name,Harbor sellerHarbor,Harbor targetHarbor, Resource selectionResource,Resource demandResource,int selectionQuantity,int demandQuantity,int timer){
        this.name = name;
        this.sellerHarbor = sellerHarbor;
        this.targetHarbor = targetHarbor;
        this.fleet = new Fleet();
        this.selection = new AbstractMap.SimpleEntry<>(selectionResource,selectionQuantity);
        this.demand = new AbstractMap.SimpleEntry<>(demandResource,demandQuantity);
        this.timer = timer;
        this.ratio = (double) demand.getValue()/selection.getValue();
        this.path = SearchInGraph.findPath(sellerHarbor.getGraphPosition(), targetHarbor.getGraphPosition());
    }

    /**
     * Sets the path for the current object.
     * @param path {@link ArrayList} The path to be set as an {@link ArrayList} of {@link GraphPoint}.
     */
    public void setPath(ArrayList<GraphPoint> path) {
        this.path = path;
    }

    /**
     * Gets the current path of the object.
     * @return {@link ArrayList} The path as an {@link ArrayList} of {@link GraphPoint}.
     */
    public ArrayList<GraphPoint> getPath() {
        return path;
    }

    /**
     * Gets the seller harbor associated with the current object.
     * @return {@link Harbor} The seller harbor.
     */
    public Harbor getSellerHarbor() {
        return sellerHarbor;
    }

    /**
     * Gets the target harbor associated with the current object.
     * @return {@link Harbor} The target harbor.
     */
    public Harbor getTargetHarbor() {
        return targetHarbor;
    }

    /**
     * Gets the ratio associated with the current object.
     * @return {@link Double} The ratio.
     */
    public double getRatio() {
        return ratio;
    }

    /**
     * Gets the fleet associated with the current object.
     * @return {@link Fleet} The fleet.
     */
    public Fleet getFleet() {
        return fleet;
    }

    /**
     * Gets the name of the object.
     * @return {@link String} The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the timer associated with the current object.
     * @return {@link Integer} The timer value.
     */
    public int getTimer() {
        return timer;
    }

    /**
     * Sets the fleet for the current object.
     * @param fleet {@link Fleet} The fleet to be set.
     */
    public void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }

    /**
     * Removes the current fleet, resetting it to a new {@link Fleet}.
     */
    public void removeFleet() {
        this.fleet = new Fleet();
    }

    /**
     * Sets the time (timer) for the current object.
     * @param nb {@link Integer} The new timer value.
     */
    public void setTime(int nb) {
        this.timer = nb;
    }

    /**
     * Sets the name for the current object.
     * @param name {@link String} The name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Abandons the current task by resetting the selection and demand values.
     */
    public void abandonTask() {
        this.selection.setValue(-1);
        this.demand.setValue(-1);
    }

    /**
     * Subtracts a given time from the current timer.
     * @param nb {@link Integer} The number of time units to subtract.
     */
    public void subtractTime(int nb) {
        this.timer -= nb;
    }

    /**
     * Adds a given time to the current timer.
     * @param nb {@link Integer} The number of time units to add.
     */
    public void addTime(int nb) {
        this.timer += nb;
    }

    /**
     * Checks if the task is still active based on the timer and selection/demand values.
     * @return {@link Boolean} True if the task is active, false otherwise.
     */
    public boolean isActive() {
        return timer >= 0 && (selection.getValue() >= 0 || demand.getValue() >= 0);
    }

    /**
     * Gets a string representation of the timer, formatted as "minutes:seconds".
     * @return {@link String} The formatted timer string.
     */
    public String getStringTimer() {
        int time = (int) (this.timer * (((double) GameConfiguration.GAME_SPEED) / 1000));
        return time / 60 + ":" + time % 60;
    }

    /**
     * Gets the current demand as a resource and its associated quantity.
     * @return {@link AbstractMap.SimpleEntry} A pair containing the {@link Resource} and its quantity.
     */
    public AbstractMap.SimpleEntry<Resource, Integer> getDemand() {
        return demand;
    }

    /**
     * Sets the demand with a resource and its associated quantity.
     * @param demand {@link AbstractMap.SimpleEntry} A pair containing the {@link Resource} and its quantity.
     */
    public void setDemand(AbstractMap.SimpleEntry<Resource, Integer> demand) {
        this.demand = demand;
    }

    /**
     * Gets the current selection as a resource and its associated quantity.
     * @return {@link AbstractMap.SimpleEntry} A pair containing the {@link Resource} and its quantity.
     */
    public AbstractMap.SimpleEntry<Resource, Integer> getSelection() {
        return selection;
    }

    /**
     * Sets the selection with a resource and its associated quantity.
     * @param selection {@link AbstractMap.SimpleEntry} A pair containing the {@link Resource} and its quantity.
     */
    public void setSelection(AbstractMap.SimpleEntry<Resource, Integer> selection) {
        this.selection = selection;
    }


}
