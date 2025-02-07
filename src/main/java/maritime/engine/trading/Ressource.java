package maritime.engine.trading;

public class Ressource {
    private String name;
    private int value;
    private double productionRate;

    public Ressource(String name, int value, double productionRate, int quantity) {
        this.name = name;
        this.value = value;
        this.productionRate = productionRate;
    }

    //Getters

    public String getName() { return name; }

    public int getValue() { return value; }

    public double getProductionRate() { return productionRate; }

    //Setters

    public void setName(String name) { this.name = name; }

    public void setValue(int value) { this.value = value; }

    public void setProductionRate(double productionRate) { this.productionRate = productionRate; }
}