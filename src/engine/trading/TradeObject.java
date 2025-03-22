package engine.trading;

import java.io.Serializable;

public interface TradeObject extends Serializable {

    public String getName();

    public int getValue();

    public void setName(String name);

    public void setValue(int value);

    @Override
    public boolean equals(Object obj);
}