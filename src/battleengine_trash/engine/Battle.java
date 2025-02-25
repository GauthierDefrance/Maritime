package battleengine_trash.engine;

import battleengine_trash.gui.BattleFightingPanel;
import battleengine_trash.gui.BattlePlacingPanel;
import battleengine_trash.tools.DeepCopy;
import engine.entity.boats.*;
import gui.PaintEntity;

import javax.swing.*;
import java.awt.*;

public class Battle {

    private Fleet orignalA, originalB;

    private Fleet Af, Bf; //Listes des bateaux en réserves
    private Fleet BattleA, BattleB; //Listes des bateaux sur la map

    private SpawnZone zoneA, zoneB;

    private PaintEntity paintEntity;
    private JPanel current_jpanel;

    public Battle(Fleet A, Fleet B) {

        BattleA = new Fleet();
        BattleB = new Fleet();

        this.zoneA = new SpawnZone(new Point(0,0),200,600,"blue", BattleA);
        this.zoneB = new SpawnZone(new Point(400,0),200,600,"red", BattleB);



        //On garde en mémoire les objets originelle : Les fleet originel passé en paramètres.
        this.orignalA = A; this.originalB = B;
        paintEntity = new PaintEntity();

        //On fait une copie de nos objets pour éviter de modifier les originaux A et B.
        Af = DeepCopy.copyFleet(A);
        Bf = DeepCopy.copyFleet(B);
        this.PlaceBoats();
    }

    public JPanel getJPanel() {return current_jpanel;}


    private void PlaceBoats() {
        current_jpanel = new BattlePlacingPanel(this.Af,this.Bf,this.BattleA, this.BattleB,this.paintEntity, zoneA, zoneB); //Stocker le JPanel ?
    }

    private void BattleFight(){
        current_jpanel = new BattleFightingPanel();
    }

}
