package battleengine_trash.test;

import battleengine_trash.engine.Battle;
import battleengine_trash.process.BattleManager;
import config.GameConfiguration;
import engine.entity.boats.Fleet;
import engine.entity.boats.Fodder;
import engine.entity.boats.Military;
import engine.graph.GraphPoint;

import javax.swing.*;
import java.awt.*;

public class MainTestPlacing extends JFrame {
    private Fleet A;
    private Fleet B;
    private Battle battle;
    private BattleManager battleManager;

    public MainTestPlacing() {
        A  = new Fleet();
        B  = new Fleet();
        for(int k=0; k<40; k++){
            A.add(new Military("military1", "blue", new GraphPoint(new Point(-100, -100), null)));
        }
        B.add(new Fodder("fodder1", "red", new GraphPoint(new Point(300, 300), null)));

        battleManager = new BattleManager(A, B);

        setTitle("Battle");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(battleManager.getJpanel());
        setVisible(true);

    }

    private void run(){
        while (true) {
            try {
                Thread.sleep(GameConfiguration.GAME_SPEED);

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            battleManager.getJpanel().repaint();
        }
    }



    public static void main(String[] args) {
        new MainTestPlacing();
    }



}
