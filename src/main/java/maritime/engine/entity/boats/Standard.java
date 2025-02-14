package maritime.engine.entity.boats;

import maritime.config.GameConfiguration;
import maritime.engine.entity.boats.Boat;
import maritime.engine.graph.GraphPoint;

import java.awt.*;

public class Standard extends Boat {
    public Standard(String name,String color, GraphPoint position) {
        super(name,color,GameConfiguration.STANDARD_VISION_RADIUS,position, (int) (GameConfiguration.STANDARD_HP), (int) (GameConfiguration.STANDARD_DAMAGE_SPEED), (int) (GameConfiguration.STANDARD_SPEED),(int) (GameConfiguration.STANDARD_INVENTORY_SIZE));
    }

}
