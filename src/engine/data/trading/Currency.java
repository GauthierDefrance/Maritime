package engine.data.trading;

import java.util.Objects;

public class Currency implements TradeObject {
    private String name;
    private int value;
    private int amount;

    public Currency(String name, int value, int amount) {
        this.name = name;
        this.value = value;
        this.amount = amount;
    }

    //Getters

    @Override
    public int getValue(){
        return value;
    }

    @Override
    public String getName(){
        return name;
    }

    public int getAmount(){
        return amount;
    }

    //Setters

    @Override
    public void setValue(int value){
        this.value = value;
    }

    @Override
    public void setName(String name){
        this.name = name;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }

    public void addAmount(int amount){
        this.amount += amount;
    }

    public void subtractAmount(int amount){
        this.amount -= amount;
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof Currency){
            Currency reference = (Currency) o;
            return this.name.equals(reference.name) && this.value == reference.value;
        } return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.name, this.value);
    }
}
