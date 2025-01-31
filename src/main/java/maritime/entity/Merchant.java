package maritime.entity;

import java.awt.*;

public class Merchant extends Boat{
    public Merchant(String name, int visionRadius, int maxHp, Point position, String idModel, int speed) {
        super(name, visionRadius, maxHp, position, idModel, speed);
    }
//    static public Entity create(String name, int visionRadius, int maxHp, Point position, String idModel) {
//        return new Merchant(name, visionRadius, maxHp, position, idModel);
//    }
}
