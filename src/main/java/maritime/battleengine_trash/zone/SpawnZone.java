package maritime.battleengine_trash.zone;

import maritime.battleengine_trash.collision.Collision;
import maritime.battleengine_trash.collision.Rectangle;
import maritime.engine.entity.boats.Boat;

import java.awt.*;
import java.util.ArrayList;

public class SpawnZone  extends Rectangle {
    private String color;
    private ArrayList<Boat> placedList;

    public SpawnZone(Point topLeft, int width, int height, String color) {
        super(topLeft, width, height);
        this.color = color;
    }

    /**
     * @param boat
     * @return bool Indique si on peut placer un bateau à sa position actuelle.
     */
    public boolean isPlaceable(Boat boat) {
        //On vérifie si le bateau se trouve dans la zone de spawn lors du placement
        if (!this.contains(boat.getPosition())) {
            return false;
        }
        //On vérifie que le bateau n'est pas trop proche d'un autre bateau
        for (Boat placedBoat : this.placedList) {
            if (Collision.CollisionCircle(boat.getPosition(), placedBoat.getPosition())) {
                return false;
            }
        }
        return true;
    }




}
