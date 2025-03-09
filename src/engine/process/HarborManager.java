package engine.process;

import config.MapBuilder;

/**
 * @author Kenan Ammad
 * @version 0.1
 */
public class HarborManager {
    private final MapBuilder map;

    /**
     * Typical constructor generating an HarborManager
     */
    public HarborManager(MapBuilder map, TradeManager tradeManager){
        this.map = map;
    }



}
