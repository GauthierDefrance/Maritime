package battleengine.process;

import battleengine.entity.Battle;
import engine.entity.boats.Boat;

public class shootManager {

    private final Battle battle;

    public shootManager(Battle battle) {
        this.battle = battle;
    }

    public void tick(){

    }



    private void shoot(Boat hunter, Boat prey, double angle){

        if(angle<0){
            //Shoot on the right side of the boat
            

        }
        else{
            //shoot on the left side of the boat


        }

    }

    private boolean isReadyToShot(Boat boat){
        int tmp = boat.getDamageSpeed();
        if(tmp< this.battle.getReloadingHashMap().get(boat)){
            return true;
        }
        else {
            boat.setDamageSpeed(tmp+1);
            return false;
        }
    }






}
