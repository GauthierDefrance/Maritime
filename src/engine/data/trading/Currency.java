package engine.data.trading;

public class Currency extends Resource {

    public Currency(String name, int value) {
        super(name,value,Integer.MAX_VALUE);
    }

    //Getters
    @Override
    public void setProductionRate(int productionRate) {

    }

    @Override
    public int getProductionRate() {
        return Integer.MAX_VALUE;
    }

}
