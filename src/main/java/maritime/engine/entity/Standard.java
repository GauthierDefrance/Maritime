package maritime.engine.entity;

import maritime.config.GameConfiguration;

import java.awt.*;

public class Standard extends Boat{
    public Standard(String name,String color, Point position) {
        super(name,color,GameConfiguration.STANDARD_VISION_RADIUS,position, (int) (GameConfiguration.STANDARD_HP), (int) (GameConfiguration.STANDARD_DAMAGE_SPEED), (int) (GameConfiguration.STANDARD_SPEED));
    }

}
