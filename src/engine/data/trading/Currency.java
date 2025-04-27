package engine.data.trading;

/**
 * Class relative to the notion of Currency
 * @version 0.2
 * @author Zue Jack-Arthur
 */
public class Currency extends Resource {

    public Currency(String name, int value) {
        super(name,value,Integer.MAX_VALUE);
    }

    /**
     * Sets the production rate for this object.
     * Currently, this method does not perform any action.
     * @param productionRate {@link Integer} The rate of production to be set.
     */
    @Override
    public void setProductionRate(int productionRate) {
        // No implementation as per current design
    }

    /**
     * Gets the production rate for this object.
     * Returns {@link Integer#MAX_VALUE} as a placeholder or default value.
     * @return {@link Integer} The production rate, currently returning {@link Integer#MAX_VALUE}.
     */
    @Override
    public int getProductionRate() {
        return Integer.MAX_VALUE;
    }

}
