package maritime.test;

import javax.swing.*;
import java.awt.*;
import maritime.config.GameConfiguration;
import maritime.config.MapBuilder;
import maritime.engine.entity.boats.Fodder;
import maritime.engine.entity.boats.Merchant;
import maritime.engine.entity.boats.Military;
import maritime.engine.entity.boats.Standard;
import maritime.engine.process.FactionManager;
import maritime.gui.GameDisplay;

public class TestMove extends JFrame implements Runnable {
    private MapBuilder map = new MapBuilder(0);
    private FactionManager factionManager = new FactionManager(map);

    private GameDisplay dashboard;

    public TestMove(String title) {
        super(title);
        init();
    }

    private void init() {
        dashboard = new GameDisplay(map);

        Military military0 = new Military("military0","blue",new Point(320*GameConfiguration.GAME_SCALE,180*GameConfiguration.GAME_SCALE));
        Military military1 = new Military("military1","blue",new Point(320*GameConfiguration.GAME_SCALE,180*GameConfiguration.GAME_SCALE));
        Merchant merchant0 = new Merchant("merchant0","blue",new Point(320*GameConfiguration.GAME_SCALE,180*GameConfiguration.GAME_SCALE));
        Standard standard0 = new Standard("standard0","blue",new Point(320*GameConfiguration.GAME_SCALE,180*GameConfiguration.GAME_SCALE));
        Standard standard1 = new Standard("standard1","blue",new Point(320*GameConfiguration.GAME_SCALE,180*GameConfiguration.GAME_SCALE));
        Fodder fodder0 = new Fodder("fodder0","blue",new Point(320*GameConfiguration.GAME_SCALE,180*GameConfiguration.GAME_SCALE));

        map.getPlayer().getLstBoat().add(military0);
        map.getPlayer().getLstBoat().add(military1);
        map.getPlayer().getLstBoat().add(merchant0);
        map.getPlayer().getLstBoat().add(standard0);
        map.getPlayer().getLstBoat().add(standard1);
        map.getPlayer().getLstBoat().add(fodder0);

        Military military2 = new Military("military2","red",new Point(340*GameConfiguration.GAME_SCALE,180*GameConfiguration.GAME_SCALE));
        Merchant merchant1 = new Merchant("merchant1","red",new Point(340*GameConfiguration.GAME_SCALE,180*GameConfiguration.GAME_SCALE));
        Merchant merchant2 = new Merchant("merchant2","red",new Point(340*GameConfiguration.GAME_SCALE,180*GameConfiguration.GAME_SCALE));
        Standard standard2 = new Standard("standard2","red",new Point(340*GameConfiguration.GAME_SCALE,180*GameConfiguration.GAME_SCALE));
        Fodder fodder1 = new Fodder("fodder1","red",new Point(340*GameConfiguration.GAME_SCALE,180*GameConfiguration.GAME_SCALE));
        Fodder fodder2 = new Fodder("fodder2","red",new Point(340*GameConfiguration.GAME_SCALE,180*GameConfiguration.GAME_SCALE));

        map.getLstFaction().getFirst().getLstBoat().add(military2);
        map.getLstFaction().getFirst().getLstBoat().add(merchant1);
        map.getLstFaction().getFirst().getLstBoat().add(merchant2);
        map.getLstFaction().getFirst().getLstBoat().add(standard2);
        map.getLstFaction().getFirst().getLstBoat().add(fodder1);
        map.getLstFaction().getFirst().getLstBoat().add(fodder2);

        Container contentPane = getContentPane();
        contentPane.add(dashboard);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        dashboard.setBackground(new Color(78, 172, 233));
        setSize(640, 360);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setVisible(true);
        Debug debug = new Debug("Debug",map);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(GameConfiguration.GAME_SPEED);

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            if (!map.isTimeStop()){
                factionManager.nextRound();
            }
            dashboard.repaint();
        }
    }


        public static void main(String[] args) {

        TestMove gameMainGUI = new TestMove("Aircraft game");
        Thread gameThread = new Thread(gameMainGUI);
        gameThread.start();
    }



}
