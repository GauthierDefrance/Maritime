package battleengine.factory;

import battleengine.entity.Bullet;

import java.awt.*;

public final class BulletFactory {

    public static Bullet createBullet(int x, int y, int speed, int angle, String color){
        return new Bullet(x, y,speed, angle, color);
    }

    public static Bullet createBullet(Point p, int speed, int angle, String color){
        return new Bullet(p, speed, angle, color);
    }

}
