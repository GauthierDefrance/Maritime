package battleengine_trash.engine;

import battleengine_trash.gui.FightJPanel;
import battleengine_trash.gui.PlacingJPanel;
import battleengine_trash.process.FightManager;
import battleengine_trash.process.PlacingManager;
import battleengine_trash.tools.DeepCopy;
import engine.entity.boats.Fleet;
import gui.PaintEntity;

public class Battle {

    private Fleet fleetA;
    private Fleet fleetB;
    private Fleet originalA;
    private Fleet originalB;

    private Placing placing;
    private BattleMap battleMap;

    private PlacingJPanel placingJPanel;
    private FightJPanel fightingJPanel;

    private PaintEntity paintEntity;

    private PlacingManager placingManager;
    private FightManager fightManager;

    private boolean isInPlacingMode;

    public Battle(Fleet fleetA, Fleet fleetB) {
        this.fleetA = DeepCopy.copyFleet(fleetA);
        this.fleetB = DeepCopy.copyFleet(fleetB);
        this.originalA = fleetA;
        this.originalB = fleetB;
        this.placing = new Placing(this.fleetA);
        this.battleMap = new BattleMap();

        this.paintEntity = new PaintEntity();

        this.placingManager = new PlacingManager(this);
        this.fightManager = new FightManager(this);

        this.fightingJPanel = new FightJPanel(this);
        this.placingJPanel = new PlacingJPanel(this);

        this.isInPlacingMode = true;

    }

    public Fleet getFleetA() { return fleetA; }
    public Fleet getFleetB() { return fleetB; }
    public Fleet getOriginalA() { return originalA; }
    public Fleet getOriginalB() { return originalB; }
    public Placing getPlacing() { return placing; }
    public BattleMap getBattleMap() { return battleMap; }
    public PlacingJPanel getPlacingJPanel() { return placingJPanel; }
    public FightJPanel getFightingJPanel() { return fightingJPanel; }
    public PaintEntity getPaintEntity() { return paintEntity; }
    public PlacingManager getPlacingManager() { return placingManager; }
    public FightManager getFightManager() { return fightManager; }
    public boolean isInPlacingMode() { return isInPlacingMode; }


}
