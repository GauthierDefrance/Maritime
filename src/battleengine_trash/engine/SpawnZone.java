package battleengine_trash.engine;

import config.GameConfiguration;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;

import java.awt.*;

public class SpawnZone  extends Rectangle {
    private String color;
    private Fleet placedList;

    public SpawnZone(Point topLeft, int width, int height, String color, Fleet placedList) {
        super(topLeft, width, height);
        this.color = color;
        this.placedList = placedList;
    }

    public void addPlacedBoat(Boat boat) {
        placedList.add(boat);
    }

    public void removePlacedBoat(Boat boat) {
        placedList.remove(boat);
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
        for (Boat placedBoat : this.placedList.getArrayListBoat()) {
            if (boat.getPosition().distance(placedBoat.getPosition())< GameConfiguration.HITBOX_BOAT ) {
                return false;
            }
        }
        return true;
    }




}
