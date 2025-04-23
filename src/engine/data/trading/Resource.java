package engine.data.trading;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class relative to the notion of Resources : objects linked with the notion of inventory that can be transferred
 * @see Inventory
 * @author Zue Jack-Arthur
 * @version 0.3
 */
public class Resource implements Serializable {
    private String name;
    private int value;
    private int productionRate;

    /**
     * A typical constructor to make instances of Resources
     * @param name identifying String
     * @param value default selling value
     * @param productionRate default speed of generation
     */
    public Resource(String name, int value, int productionRate) {
        this.name = name;
        this.value = value;
        this.productionRate = productionRate;
    }

    //Getters

    /**
     * Allows fetching the name of the Resource
     * @return identifying String
     */
    public String getName() {
        return name;
    }

    /**
     * Allows fetching of the indicative value of the Resource
     * @return default selling value
     */
    public int getValue() {
        return value;
    }

    /**
     * Allows fetching of the indicative speed of generation of the Resource
     * @return default speed of generation
     */
    public int getProductionRate() {
        return productionRate;
    }

    //Setters

    /**
     * Change the name of this Resource Object
     * @param name String that must replace the current name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Allows alteration of the indicative value of the Resource
     * @param value new default selling value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Allows alteration of the indicative speed of generation of the Resource
     * @param productionRate new default speed of generation
     */
    public void setProductionRate(int productionRate) {
        this.productionRate = productionRate;
    }

    //equals

    /**
     * Compare if an Object is the same as this one
     * @param r a Resource Object
     * @return result of the comparison
     */
    public boolean equals(Object r) {
        if (r instanceof Resource) {
            return (this.name.equals(((Resource) r).getName()) && this.productionRate == ((Resource) r).getProductionRate());
        } return false;
    }

    /**
     * Generate the hashCode of this Object
     * @return hashCode
     */
    public int hashCode() {
        return Objects.hash(this.name, this.productionRate);
    }
}