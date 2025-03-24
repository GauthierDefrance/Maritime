package engine.battleengine.process;

import engine.battleengine.data.Battle;

public class BattleEnd {

    private Battle battle;

    public BattleEnd(Battle battle) {
        this.battle = battle;
    }

    public void end(){

    }



    public boolean playerLose(){
        return battle.getBoatsInBattleA().getArrayListBoat().isEmpty()&&battle.getLstBoatsToPlace().isEmpty()&&!battle.isInPlacingMode();
    }

    public boolean playerWin(){
        return battle.getBoatsInBattleB().getArrayListBoat().isEmpty();
    }

    public boolean playerTie(){
        return playerLose()&&playerWin();
    }





}
