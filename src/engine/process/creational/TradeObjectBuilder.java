package engine.process.creational;
import engine.data.trading.Currency;
import engine.data.trading.Resource;

public class TradeObjectBuilder {
    private String name;
    private int value;
    private int productionRate;

    public TradeObjectBuilder(){}

    public TradeObjectBuilder name(String name){
        this.name = name;
        return this;
    }

    public TradeObjectBuilder value(int value){
        this.value = value;
        return this;
    }

    public TradeObjectBuilder productionRate(int productionRate){
        this.productionRate = productionRate;
        return this;
    }

    public Resource buildResource(){
        return new Resource(name, value, productionRate);
    }

    public Currency buildCurrency(){
        return new Currency(name, value);
    }
}
