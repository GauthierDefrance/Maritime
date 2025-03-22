package engine.process.builder;
import engine.trading.Currency;
import engine.trading.Resource;

public class TradeObjectBuilder {
    private String name;
    private int value;
    private double productionRate;
    private int amount;

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

    public TradeObjectBuilder amount(int amount){
        this.amount = amount;
        return this;
    }

    public Resource buildResource(){
        return new Resource(name, value, productionRate);
    }

    public Currency buildCurrency(){
        return new Currency(name, value, amount);
    }
}
