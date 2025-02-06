package maritime.engine.entity;

import maritime.config.GameConfiguration;

import java.awt.*;

public class Military extends Boat{
    public Military(String name, Point position) {
        super(name,GameConfiguration.STANDARD_VISION_RADIUS* GameConfiguration.MILITARY_VISION_RADIUS_BOOST, (int) (GameConfiguration.STANDARD_HP*GameConfiguration.MILITARY_HP_BOOST), (int) (GameConfiguration.STANDARD_DAMAGE_SPEED*GameConfiguration.MILITARY_DAMAGE_SPEED_BOOST), position, (int) (GameConfiguration.STANDARD_SPEED*GameConfiguration.MILITARY_SPEED_BOOST));
    }

}
