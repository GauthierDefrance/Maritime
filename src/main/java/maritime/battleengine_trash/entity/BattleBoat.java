package maritime.battleengine_trash.entity;

import java.awt.*;
import java.util.ArrayList;
import java.util.Queue;

public abstract class BattleBoat {

    private Point position;
    private Hitbox hitbox;
    private int angle;

    private int SPEED;
    private int MAX_HEALTH;
    private int CURRENT_HEALTH;
    private int angleS;

    private String name;
    private int health;

    private Queue<Point> file;

    public BattleBoat(Point pos, Hitbox hitbox, int angle, int SPEED, int MAX_HEALTH, int CURRENT_HEALTH, int angleS, String name) {
        this.position = pos;
        this.hitbox = hitbox;
        this.angle = angle;
        this.SPEED = SPEED;
        this.MAX_HEALTH = MAX_HEALTH;
        this.CURRENT_HEALTH = CURRENT_HEALTH;
        this.angleS = angleS;
        this.name = name;
    }

}
