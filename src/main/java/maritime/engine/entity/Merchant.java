package maritime.engine.entity;

import maritime.config.GameConfiguration;

import java.awt.*;

public class Merchant extends Boat{
    public Merchant(String name, double visionRadius, int maxHp,int damageSpeed, Point position, int speed) {
        super(name, visionRadius, (int) (maxHp* GameConfiguration.MERCHANT_HP_BOOST), (int) (damageSpeed*GameConfiguration.MERCHANT_DAMAGE_SPEED_BOOST), position, (int) (speed*GameConfiguration.MERCHANT_SPEED_BOOST));
    }

}
