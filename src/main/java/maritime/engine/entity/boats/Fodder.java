package maritime.engine.entity.boats;

import maritime.config.GameConfiguration;

import java.awt.*;

public class Fodder extends Boat {
    public Fodder(String name,String color, Point position) {
        super(name,color,GameConfiguration.STANDARD_VISION_RADIUS*GameConfiguration.FODDER_VISION_RADIUS_BOOST,position, (int) (GameConfiguration.STANDARD_HP*GameConfiguration.FODDER_HP_BOOST), (int) (GameConfiguration.STANDARD_DAMAGE_SPEED*GameConfiguration.FODDER_DAMAGE_SPEED_BOOST), (int) (GameConfiguration.STANDARD_SPEED*GameConfiguration.FODDER_SPEED_BOOST));
    }

}