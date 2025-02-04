package maritime.engine.entity;

import maritime.config.GameConfiguration;

import java.awt.*;

public class Standard extends Boat{
    public Standard(String name, int visionRadius, int maxHp,int damageSpeed, Point position, int speed) {
        super(name, visionRadius, maxHp* GameConfiguration.STANDARD_HP_BOOST,damageSpeed*GameConfiguration.STANDARD_DAMAGE_SPEED_BOOST, position,speed*GameConfiguration.STANDARD_SPEED_BOOST);
    }

}
