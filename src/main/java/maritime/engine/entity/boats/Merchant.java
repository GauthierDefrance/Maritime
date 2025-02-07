package maritime.engine.entity.boats;

import maritime.config.GameConfiguration;

import java.awt.*;

public class Merchant extends Boat {
    public Merchant(String name, Point position) {
        super(name,GameConfiguration.STANDARD_VISION_RADIUS* GameConfiguration.MERCHANT_VISION_RADIUS_BOOST, (int) (GameConfiguration.STANDARD_HP* GameConfiguration.MERCHANT_HP_BOOST), (int) (GameConfiguration.STANDARD_DAMAGE_SPEED*GameConfiguration.MERCHANT_DAMAGE_SPEED_BOOST), position, (int) (GameConfiguration.STANDARD_SPEED*GameConfiguration.MERCHANT_SPEED_BOOST));
    }

}
