package maritime.engine.entity;

import maritime.config.GameConfiguration;

import java.awt.*;

public class Merchant extends Boat{
    public Merchant(String name, int visionRadius, int maxHp,int damageSpeed, Point position, int speed) {
        super(name, visionRadius, maxHp* GameConfiguration.MERCHANT_HP_BOOST,damageSpeed*GameConfiguration.MERCHANT_DAMAGE_SPEED_BOOST, position,speed*GameConfiguration.MERCHANT_SPEED_BOOST);
    }

}
