package maritime.engine.entity;

import maritime.config.GameConfiguration;

import java.awt.*;

public class Military extends Boat{
    public Military(String name, double visionRadius, int maxHp,int damageSpeed, Point position, int speed) {
        super(name, visionRadius, (int) (maxHp*GameConfiguration.MILITARY_HP_BOOST), (int) (damageSpeed*GameConfiguration.MILITARY_DAMAGE_SPEED_BOOST), position, (int) (speed*GameConfiguration.MILITARY_SPEED_BOOST));
    }

}
