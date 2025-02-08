package maritime.engine.entity;

import maritime.config.GameConfiguration;

import java.awt.*;

public class Merchant extends Boat{
    public Merchant(String name,String color, Point position) {
        super(name,color,GameConfiguration.STANDARD_VISION_RADIUS* GameConfiguration.MERCHANT_VISION_RADIUS_BOOST,position, (int) (GameConfiguration.STANDARD_HP* GameConfiguration.MERCHANT_HP_BOOST), (int) (GameConfiguration.STANDARD_DAMAGE_SPEED*GameConfiguration.MERCHANT_DAMAGE_SPEED_BOOST), (int) (GameConfiguration.STANDARD_SPEED*GameConfiguration.MERCHANT_SPEED_BOOST));
    }

}
