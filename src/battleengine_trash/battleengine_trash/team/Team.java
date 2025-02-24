package battleengine_trash.battleengine_trash.team;

import engine.entity.boats.Fleet;

public class Team {
    private String color;
    private Fleet fleet;

    public Team(Fleet fleet) throws IllegalArgumentException{
//        if (fleet.getArrayListFleet().isEmpty()) {
//            throw new IllegalArgumentException("Fleet is empty");
//        }
        this.fleet = fleet;
        this.color = fleet.getArrayListFleet().get(0).getColor();
    }

    public String getColor() {
        return color;
    }

    public Fleet getFleet() {
        return fleet;
    }



}
