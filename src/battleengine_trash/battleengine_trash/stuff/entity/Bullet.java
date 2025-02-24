package battleengine_trash.battleengine_trash.stuff.entity;

import java.awt.*;

public class Bullet {
    private Point position;
    private double speed;
    private int angle;

    public Bullet(Point position, double speed, int angle) {
        this.position = position;
        this.speed = speed;
        this.angle = angle;
    }


    public Point getPosition() {
        return position;
    }
    public double getSpeed() {
        return speed;
    }
    public int getAngle() {
        return angle;
    }


    public void setPosition(Point position) {
        this.position = position;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public boolean equals(Point other){
        return this.position.equals(other);
    }

}

