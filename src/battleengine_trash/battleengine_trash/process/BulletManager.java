package battleengine_trash.battleengine_trash.process;

import maritime.battleengine_trash.entity.Bullet;
import maritime.config.GameConfiguration;

import java.awt.*;

public class BulletManager {

    public static void move(Bullet b){
        Point p = b.getPosition();
        int x = (int) p.getX();
        int y = (int) p.getY();
        p.move((int) (y+Math.cos(b.getAngle())),(int) (x+b.getSpeed()*Math.sin(b.getAngle())));
        reduceSpeed(b);
    }

    public static void reduceSpeed(Bullet b){
        if(b.getSpeed()<1){
            b.setSpeed(0);
        }
        else {
            b.setSpeed(b.getSpeed() * GameConfiguration.BULLET_FRICTION);
        }
    }

}
