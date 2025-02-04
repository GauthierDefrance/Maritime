package maritime.engine.entity;

import maritime.config.GameConfiguration;

import java.awt.*;

public class Military extends Boat{
    public Military(String name, int visionRadius, int maxHp,int damageSpeed, Point position, int speed) {
        super(name, visionRadius, maxHp*GameConfiguration.MILITARY_HP_BOOST,damageSpeed*GameConfiguration.MILITARY_DAMAGE_SPEED_BOOST, position,speed*GameConfiguration.MILITARY_SPEED_BOOST);
    }

}
