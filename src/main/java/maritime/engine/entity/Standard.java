package maritime.engine.entity;

import maritime.config.GameConfiguration;

import java.awt.*;

public class Standard extends Boat{
    public Standard(String name, Point position) {
        super(name,GameConfiguration.STANDARD_VISION_RADIUS, (int) (GameConfiguration.STANDARD_HP), (int) (GameConfiguration.STANDARD_DAMAGE_SPEED), position, (int) (GameConfiguration.STANDARD_SPEED));
    }

}
