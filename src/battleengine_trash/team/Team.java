package maritime.battleengine_trash.team;

import maritime.engine.entity.boats.Fleet;

public class Team {
    private String color;
    private Fleet alive_fleet;
    private Fleet dead_fleet;

    public Team(Fleet fleet) throws IllegalArgumentException{
        if (fleet.getArrayListFleet().isEmpty()) {
            throw new IllegalArgumentException("Fleet is empty");
        }
        this.fleet = fleet;
        this.color = fleet.getArrayListFleet().get(0).getColor();
        this.alive_fleet = new Fleet();
    }

    public String getColor() {
        return color;
    }

    public Fleet getAliveFleet() {
        return alive_fleet;
    }

    public Fleet getDeadFleet() {
        return dead_fleet;
    }


}
