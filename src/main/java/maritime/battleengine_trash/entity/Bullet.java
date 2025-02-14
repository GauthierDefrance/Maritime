package maritime.battleengine_trash.entity;

import java.awt.*;

public class Bullet {
    private Point position;
    private int speed;
    private int angle;

    public Bullet(Point position, int speed, int angle) {
        this.position = position;
        this.speed = speed;
        this.angle = angle;
    }


    public Point getPosition() {
        return position;
    }
    public int getSpeed() {
        return speed;
    }
    public int getAngle() {
        return angle;
    }


    public void setPosition(Point position) {
        this.position = position;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public boolean equals(Point other){
        return this.position.equals(other);
    }

}

