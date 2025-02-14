package maritime.engine.trading;

/**
 * Class relative to the notion of Resources : tradable objects linked with the notion of inventory
 * @see Inventory
 * @author Zue Jack-Arthur
 * @version 0.1
 */
public class Resource {
    private String name;
    private int value;
    private double productionRate;

    /**
     * A typical builder to make instances of Resources
     * @param name identifying String
     * @param value default selling value
     * @param productionRate default speed of generation
     */
    public Resource(String name, int value, double productionRate) {
        this.name = name;
        this.value = value;
        this.productionRate = productionRate;
    }

    //Getters

    /**
     * Allows fetching the name of the Resource
     * @return identifying String
     */
    public String getName() { return name; }

    /**
     * Allows fetching of the indicative value of the Resource
     * @return default selling value
     */
    public int getValue() { return value; }

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
}