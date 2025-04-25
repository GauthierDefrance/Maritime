package engine.data.entity.boats;

import config.GameConfiguration;
import engine.data.graph.GraphPoint;

/**
 * class representing the extends type of Boats Standard
 * @author Kenan Ammad
 * @author Zue Jack-Arthur
 * @version 0.2
 */
public class Standard extends Boat {
    public Standard(String name,String color, GraphPoint position) {
        super(name,color,GameConfiguration.STANDARD_VISION_RADIUS,position, (int) (GameConfiguration.STANDARD_HP), (int) (GameConfiguration.STANDARD_DAMAGE_SPEED), (int) (GameConfiguration.STANDARD_SPEED),(int) (GameConfiguration.STANDARD_INVENTORY_SIZE));
    }
}
