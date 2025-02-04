package maritime.engine.entity;

import maritime.config.GameConfiguration;

import java.awt.*;

public class Fodder extends Boat{
    public Fodder(String name, double visionRadius, int maxHp, int damageSpeed, Point position, int speed) {
        super(name, visionRadius, (int) (maxHp* GameConfiguration.FODDER_HP_BOOST), (int) (damageSpeed*GameConfiguration.FODDER_DAMAGE_SPEED_BOOST), position, (int) (speed*GameConfiguration.FODDER_SPEED_BOOST));
    }

}