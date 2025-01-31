package maritime.entity;

import java.awt.*;

public class Military extends Boat{
    public Military(String name, int visionRadius, int maxHp, Point position, String idModel, int speed) {
        super(name, visionRadius, maxHp, position, idModel,speed);
    }
//    static public Entity create(String name, int visionRadius, int maxHp, Point position, String idModel) {
//        return new Military(name, visionRadius, maxHp, position, idModel);
//    }
}
