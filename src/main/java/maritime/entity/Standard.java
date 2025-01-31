package maritime.entity;

import java.awt.*;

public class Standard extends Boat{
    public Standard(String name, int visionRadius, int maxHp, Point position, String idModel) {
        super(name, visionRadius, maxHp, position, idModel,0);
    }
    static public Entity create(String name, int visionRadius, int maxHp, Point position, String idModel) {
        return new Standard(name, visionRadius, maxHp, position, idModel);
    }
}
