package engine.battleengine.process;

import engine.MapGame;
import engine.battleengine.data.Battle;
import engine.data.Fleet;
import engine.data.entity.boats.Boat;
import engine.process.manager.FactionManager;
import engine.process.manager.TradeManager;
import engine.data.trading.Inventory;
import engine.data.trading.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Manager of the BattleEnd class, manage the modification that may occur at the end of a battle.
 * @author Gauthier Defrance
 * @version 0.4
 */
public class BattleEndManager {

    private Battle battle;

    public BattleEndManager(Battle battle) {
        this.battle = battle;
    }

    /*---- WIN/LOSE COND ----*/

    /**
     *
     * @return
     */
    public boolean playerLose(){
        return battle.getBoatsInBattleA().getArrayListBoat().isEmpty()&& (battle.getLstBoatsToPlace() == null||battle.getLstBoatsToPlace().isEmpty()) && (battle.getLstBoatsCurrentlyBeingPlaced() == null ||battle.getLstBoatsCurrentlyBeingPlaced().isEmpty());
    }

    /**
     *
     * @return
     */
    public boolean playerWin(){
        return battle.getBoatsInBattleB().getArrayListBoat().isEmpty();
    }



    /**
     * Function that actualize the current state of the original fleet.
     * It removes all the boats that were loosed and change the inventory of the survivors boats.
     */
    public ArrayList<String> actualizeOriginalFleet(){
        Inventory bigInv = new Inventory();
        Inventory lostInv = new Inventory();
        Inventory resoursHashMap = new Inventory();

        FactionManager.getInstance().modifyRelationship(this.battle.getFactionB(),this.battle.getFactionA(),-5*this.battle.getDeadBoatsA().getArrayListBoat().size());
        FactionManager.getInstance().modifyRelationship(this.battle.getFactionA(),this.battle.getFactionB(),-5*this.battle.getDeadBoatsB().getArrayListBoat().size());
        for(Boat boat : this.battle.getDeadBoatsB().getArrayListBoat()){
            TradeManager.getInstance().transferMaxAll(this.battle.getCopyToOrignalHashMap().get(boat).getInventory(), bigInv,null,null);
            this.battle.getTeamBOriginal().getArrayListBoat().remove(this.battle.getCopyToOrignalHashMap().get(boat));
            this.battle.getFactionB().removeBoat(this.battle.getCopyToOrignalHashMap().get(boat));
            MapGame.getInstance().getHunterPreyHashMap().remove(this.battle.getCopyToOrignalHashMap().get(boat));
            ArrayList<Boat> tmp = new ArrayList<>(MapGame.getInstance().getHunterPreyHashMap().keySet());
            for(Boat boat1 : tmp){
                if(MapGame.getInstance().getHunterPreyHashMap().get(boat1).equals(this.battle.getCopyToOrignalHashMap().get(boat)))MapGame.getInstance().getHunterPreyHashMap().remove(boat1);
            }
        }
        for(Boat boat : this.battle.getDeadBoatsA().getArrayListBoat()){
            TradeManager.getInstance().transferMaxAll(this.battle.getCopyToOrignalHashMap().get(boat).getInventory(), lostInv,null,null);
            this.battle.getTeamAOriginal().getArrayListBoat().remove(this.battle.getCopyToOrignalHashMap().get(boat));
            this.battle.getFactionA().removeBoat(this.battle.getCopyToOrignalHashMap().get(boat));
            MapGame.getInstance().getHunterPreyHashMap().remove(this.battle.getCopyToOrignalHashMap().get(boat));

            ArrayList<Boat> tmp = new ArrayList<>(MapGame.getInstance().getHunterPreyHashMap().keySet());
            for(Boat boat1 : tmp){
                if(MapGame.getInstance().getHunterPreyHashMap().get(boat1).equals(this.battle.getCopyToOrignalHashMap().get(boat)))MapGame.getInstance().getHunterPreyHashMap().remove(boat1);
            }
        }
        if(playerWin()){
            //On actualise les HP
            for(Boat boat: this.battle.getBoatsInBattleA().getArrayListBoat()){
                this.battle.getCopyToOrignalHashMap().get(boat).setCurrentHp(boat.getCurrentHp());
            }
            for (Resource resource : bigInv.getContent().keySet()){
                resoursHashMap.getContent().put(resource, bigInv.getContent().get(resource));
            }
            TradeManager.getInstance().transferMaxAll(lostInv,bigInv,null,null);
            if(!transferInvToFleet(bigInv, this.battle.getTeamAOriginal().getArrayListBoat())){
                for (Resource resource : bigInv.getContent().keySet()){
                    resoursHashMap.getContent().put(resource, resoursHashMap.getNbResource(resource)-bigInv.getContent().get(resource));
                }
            }
        } 
        else {
            //On actualise les HP
            for(Boat boat: this.battle.getBoatsInBattleB().getArrayListBoat()){
                this.battle.getCopyToOrignalHashMap().get(boat).setCurrentHp(boat.getCurrentHp());
            }
            resoursHashMap = new Inventory();
            for (Resource resource : lostInv.getContent().keySet()){
                resoursHashMap.getContent().put(resource,-lostInv.getContent().get(resource));
            }
            TradeManager.getInstance().transferMaxAll(lostInv,bigInv,null,null);
            transferInvToFleet(bigInv, this.battle.getTeamBOriginal().getArrayListBoat());
        }



        ArrayList<String> lstText = new ArrayList<>();
        lstText.add("Boat destroy : "+this.battle.getDeadBoatsB().getArrayListBoat().size());
        lstText.add("Boat lost : "+this.battle.getDeadBoatsA().getArrayListBoat().size());
        lstText.add("Resource collected : "+resoursHashMap.toString());
        return lstText;
        
    }

    /**
     * Method that try to fill a fleet with a given inventory.
     * @param inv
     * @param fleet
     */
    public boolean transferInvToFleet(Inventory inv, ArrayList<Boat> fleet){
        Iterator<Boat> iterator = fleet.iterator();
        boolean loop=true;
        while(iterator.hasNext()&&loop){
            Boat tmp = iterator.next();
            loop = !TradeManager.getInstance().transferMaxAll(inv, tmp.getInventory(),null,tmp);
        }
        return loop;
    }

}
