package engine.battleengine.process;

import engine.battleengine.data.Battle;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;
import engine.trading.Inventory;
import engine.trading.Resource;

import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Set;

public class BattleEnd {

    private Battle battle;

    public BattleEnd(Battle battle) {
        this.battle = battle;
    }

    /*---- WIN/LOSE COND ----*/
    public boolean playerLose(){
        return battle.getBoatsInBattleA().getArrayListBoat().isEmpty()&&battle.getLstBoatsToPlace().isEmpty()&&!battle.isInPlacingMode();
    }

    public boolean playerWin(){
        return battle.getBoatsInBattleB().getArrayListBoat().isEmpty();
    }

    public boolean playerTie(){
        return playerLose()&&playerWin();
    }
    /* ------------------- */


    public void actualizeOriginalFleet(){
        ArrayList<Boat> teamA = this.battle.getTeamAOriginal().getArrayListBoat();
        ArrayList<Boat> teamB = this.battle.getTeamBOriginal().getArrayListBoat();
        
    }

    public void transferInventory(Fleet from, Fleet to){
        Inventory bigInventory = new Inventory();

        for(Boat boat : from.getArrayListBoat()){
            for(Resource resource : boat.getInventory().getContent().keySet()){
                if(bigInventory.getContent().containsKey(resource)){
                    bigInventory.getContent().put(resource, bigInventory.getContent().get(resource)+ boat.getInventory().getContent().get(resource));
                } else {
                    bigInventory.getContent().put(resource, boat.getInventory().getContent().get(resource));
                }
            }
        }




    }
















}
