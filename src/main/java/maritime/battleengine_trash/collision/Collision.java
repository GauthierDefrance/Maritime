package maritime.battleengine_trash.collision;

import maritime.config.GameConfiguration;

import java.awt.*;

public final class Collision {

    public static boolean CollisionCircle(Point BoatCoordinate, Point p) {
        return BoatCoordinate.distance(p)<GameConfiguration.HITBOX_BOAT;
    }

    public static boolean CollisionRectangle(Rectangle rect, Point p) {
        return rect.contains(p);
    }

}
