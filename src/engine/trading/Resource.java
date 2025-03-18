package engine.trading;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class relative to the notion of Resources : tradable objects linked with the notion of inventory
 * @see Inventory
 * @author Zue Jack-Arthur
 * @version 0.3
 */
public class Resource implements Serializable {
    private final String name;
    private int value;
    private double productionRate;

    /**
     * A typical constructor to make instances of Resources
     * @param name identifying String
     * @param value default selling value
     * @param productionRate default speed of generation
     */
    private Resource(String name, int value, double productionRate) {
        this.name = name;
        this.value = value;
        this.productionRate = productionRate;
    }

    public static Resource create(String name, int value, double productionRate) {
        return new Resource(name, value, productionRate);
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
    public double getProductionRate() { return productionRate; }

    //Setters

    /**
     * Allows alteration of the indicative value of the Resource
     * @param value new default selling value
     */
    public void setValue(int value) { this.value = value; }

    /**
     * Allows alteration of the indicative speed of generation of the Resource
     * @param productionRate new default speed of generation
     */
    public void setProductionRate(double productionRate) { this.productionRate = productionRate; }

    //equals

    /**
     * Compare if an Object is the same as this one
     * @param r a Resource Object
     * @return result of the comparison
     */
    @Override
    public boolean equals(Object r) {
        if (r instanceof Resource) {
            return (this.name.equals(((Resource) r).getName()) && this.productionRate == ((Resource) r).getProductionRate());
        } return false;
    }

    /**
     * Generate the hashCode of this Object
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.productionRate);
    }
}