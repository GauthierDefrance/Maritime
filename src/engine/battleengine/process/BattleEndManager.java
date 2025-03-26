package engine.battleengine.process;

import engine.battleengine.data.Battle;
import engine.entity.boats.Boat;
import engine.process.TradeManager;
import engine.trading.Inventory;

import java.util.ArrayList;
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
        return battle.getBoatsInBattleA().getArrayListBoat().isEmpty()&&battle.getLstBoatsToPlace().isEmpty()&&!battle.isInPlacingMode();
    }

    /**
     *
     * @return
     */
    public boolean playerWin(){
        return battle.getBoatsInBattleB().getArrayListBoat().isEmpty();
    }

    /**
     *
     * @return
     */
    public boolean playerTie(){
        return playerLose()&&playerWin();
    }
    /* ------------------- */


    /**
     * Function that actualize the current state of the original fleet.
     * It removes all the boats that were loosed and change the inventory of the survivors boats.
     */
    public ArrayList<String> actualizeOriginalFleet(){
        Inventory BigInv = new Inventory();

        // Yo, ici on va supprimer les bateaux qui sont morts durant la partie
        // De plus on ajoute au BigInv l'inventaire de tout les bateaux morts.
        for(Boat boat : this.battle.getDeadBoatsA().getArrayListBoat()){
            TradeManager.getInstance().transferAll(boat.getInventory(),BigInv);
            this.battle.getTeamAOriginal().getArrayListBoat().remove(this.battle.getCopyToOrignalHashMap().get(boat));
            this.battle.getFactionA().removeBoat(this.battle.getCopyToOrignalHashMap().get(boat));
        }
        for(Boat boat : this.battle.getDeadBoatsB().getArrayListBoat()){
            TradeManager.getInstance().transferAll(boat.getInventory(),BigInv);
            this.battle.getTeamBOriginal().getArrayListBoat().remove(this.battle.getCopyToOrignalHashMap().get(boat));
            this.battle.getFactionB().removeBoat(this.battle.getCopyToOrignalHashMap().get(boat));
        }

        if(playerWin()){
            //On actualise les HP
            for(Boat boat: this.battle.getBoatsInBattleA().getArrayListBoat()){
                this.battle.getCopyToOrignalHashMap().get(boat).setCurrentHp(boat.getCurrentHp());
            }
            transferInvToFleet(BigInv, this.battle.getTeamAOriginal().getArrayListBoat());
        } else {
            //On actualise les HP
            for(Boat boat: this.battle.getBoatsInBattleB().getArrayListBoat()){
                this.battle.getCopyToOrignalHashMap().get(boat).setCurrentHp(boat.getCurrentHp());
            }
            transferInvToFleet(BigInv, this.battle.getTeamBOriginal().getArrayListBoat());
        }
        ArrayList<String> lstText = new ArrayList<>();
        lstText.add("Boat destroy : "+this.battle.getDeadBoatsB().getArrayListBoat().size());
        lstText.add("Boat lost : "+this.battle.getDeadBoatsA().getArrayListBoat().size());
        return lstText;
        
    }

    /**
     * Method that try to fill a fleet with a given inventory.
     * @param inv
     * @param fleet
     */
    public void transferInvToFleet(Inventory inv, ArrayList<Boat> fleet){
        Iterator<Boat> iterator = fleet.iterator();
        boolean loop=true;
        while(iterator.hasNext()&&loop){
            Boat tmp = iterator.next();
            loop = !TradeManager.getInstance().transferAll(inv, tmp.getInventory());
        }
    }

}
