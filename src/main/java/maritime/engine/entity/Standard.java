package maritime.engine.entity;

import maritime.config.GameConfiguration;

import java.awt.*;

public class Standard extends Boat{
    public Standard(String name, double visionRadius, int maxHp,int damageSpeed, Point position, int speed) {
        super(name, visionRadius, (int) (maxHp* GameConfiguration.STANDARD_HP_BOOST), (int) (damageSpeed*GameConfiguration.STANDARD_DAMAGE_SPEED_BOOST), position, (int) (speed*GameConfiguration.STANDARD_SPEED_BOOST));
    }

}
