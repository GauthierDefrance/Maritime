package battleengine_trash.process;

import battleengine_trash.engine.Battle;
import battleengine_trash.gui.FightJPanel;
import battleengine_trash.gui.PlacingJPanel;
import engine.entity.boats.Fleet;

import javax.swing.*;



/**
 * Manager of the Battle, that also manage the PlacingManager and FightManager.
 * @author Gauthier Defrance
 * @version 0.1
 */
public class BattleManager {

    private Battle battle;

    public BattleManager(Fleet fleetA, Fleet fleetB) {

        battle = new Battle(fleetA, fleetB);

    }

    /**
     * Selon si le booléen isInPlacingMode est vrai ou faux, on renvoie le Jpanel de placement ou de combat.
     * @return Jpanel
     */
    public JPanel getJpanel() { if (this.battle.isInPlacingMode()) { return this.battle.getPlacingJPanel(); } else { return this.battle.getFightingJPanel(); } }

    public void nextTick() {}


}
