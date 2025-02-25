package battleengine_trash.process;

import battleengine_trash.engine.Battle;
import battleengine_trash.gui.FightJPanel;
import battleengine_trash.gui.PlacingJPanel;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;

import javax.swing.*;
import java.util.ArrayList;


/**
 * Manager of the Battle, that also manage the PlacingManager and FightManager.
 * @author Gauthier Defrance
 * @version 0.1
 */
public class BattleManager {

    private Battle battle;

    private PlacingManager placingManager;
    private FightManager fightManager;

    private JPanel fightpanel;
    private JPanel placingpanel;

    private boolean isInPlacingMode;

    public BattleManager(Fleet fleetA, Fleet fleetB) {

        battle = new Battle(fleetA, fleetB);

        placingManager = new PlacingManager(battle.getFleetA());

        fightManager = new FightManager(new Fleet(), new Fleet());

        placingpanel=new PlacingJPanel(placingManager, fightManager);
        fightpanel= new FightJPanel(fightManager);

        isInPlacingMode=true;
    }

    /**
     * Selon si le booléen isInPlacingMode est vrai ou faux, on renvoie le Jpanel de placement ou de combat.
     * @return Jpanel
     */
    public JPanel getJpanel() { if (isInPlacingMode) { return placingpanel; } else { return fightpanel; } }


}
