package battleengine_trash;

import battleengine_trash.engine.Battle;
import engine.entity.boats.Fleet;
import engine.entity.boats.Fodder;
import engine.entity.boats.Military;
import engine.graph.GraphPoint;

import javax.swing.*;
import java.awt.*;

public class MainBattleTest extends JFrame {
    private Fleet A;
    private Fleet B;
    private Battle battle;

    public MainBattleTest() {
        A  = new Fleet();
        B  = new Fleet();
        for(int k=0; k<40; k++){
            A.add(new Military("military1", "blue", new GraphPoint(new Point(-100, -100), null)));
        }
        B.add(new Fodder("fodder1", "red", new GraphPoint(new Point(300, 300), null)));
        battle = new Battle(A, B);

        setTitle("Battle");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(battle.getJPanel());
        setVisible(true);


    }

    public static void main(String[] args) {
        new MainBattleTest();

    }



}
