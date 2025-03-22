package engine.process.builder;
import engine.trading.Currency;
import engine.trading.Resource;

public class TradeObjectBuilder {
    private String name;
    private int value;
    private double productionRate;

    public TradeObjectBuilder(){}

    public TradeObjectBuilder name(String name){
        this.name = name;
        return this;
    }

    public TradeObjectBuilder value(int value){
        this.value = value;
        return this;
    }

    public TradeObjectBuilder productionRate(double productionRate){
        this.productionRate = productionRate;
        return this;
    }

    public Resource BuildResource(){
        return new Resource(name, value, productionRate);
    }

    public Currency BuildCurrency(){
        return new Currency(name, value);
    }
}
