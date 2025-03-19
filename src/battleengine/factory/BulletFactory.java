package battleengine.factory;

import battleengine.entity.Bullet;
import config.GameConfiguration;

import java.awt.*;

public final class BulletFactory {

    /**
     *
     * @param x
     * @param y
     * @param angle
     * @param color
     * @return
     */
    public static Bullet createBullet(int x, int y, double angle, String color){
        return new Bullet(x, y, GameConfiguration.DEFAULT_BULLET_SPEED, angle, color);
    }
}
