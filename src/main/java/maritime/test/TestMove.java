package maritime.test;

import javax.swing.*;
import java.awt.*;
import maritime.config.GameConfiguration;
import maritime.config.MapBuilder;
import maritime.engine.entity.boats.*;
import maritime.engine.graph.SearchInGraph;
import maritime.engine.process.FactionManager;
import maritime.engine.trading.Resource;
import maritime.engine.trading.SeaRoad;
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
        Fleet fleet1 = new Fleet();
        Fleet fleet2 = new Fleet();

        map.getPlayer().getLstFleet().add(fleet1);
        map.getLstFaction().getFirst().getLstFleet().add(fleet2);

        Military military0 = new Military("military0","blue",map.getLstHarbor().get(2).getGraphPosition());
        Military military1 = new Military("military1","blue",map.getLstHarbor().get(1).getGraphPosition());
        Merchant merchant0 = new Merchant("merchant0","blue",map.getLstHarbor().get(2).getGraphPosition());
        Standard standard0 = new Standard("standard0","blue",map.getLstHarbor().get(1).getGraphPosition());
        Standard standard1 = new Standard("standard1","blue",map.getLstHarbor().get(2).getGraphPosition());
        Fodder fodder0 = new Fodder("fodder0","blue",map.getLstHarbor().get(2).getGraphPosition());

        map.getPlayer().getLstBoat().add(military0);
        map.getPlayer().getLstBoat().add(military1);
        map.getPlayer().getLstBoat().add(merchant0);
        map.getPlayer().getLstBoat().add(standard0);
        map.getPlayer().getLstBoat().add(standard1);
        map.getPlayer().getLstBoat().add(fodder0);

        fleet1.getArrayListFleet().add(military0);
        fleet1.getArrayListFleet().add(military1);
        fleet1.getArrayListFleet().add(merchant0);
        fleet1.getArrayListFleet().add(standard0);
        fleet1.getArrayListFleet().add(standard1);
        fleet1.getArrayListFleet().add(fodder0);

        Military military2 = new Military("military2","red",map.getLstHarbor().get(2).getGraphPosition());
        Merchant merchant1 = new Merchant("merchant1","red",map.getLstHarbor().get(1).getGraphPosition());
        Merchant merchant2 = new Merchant("merchant2","red",map.getLstHarbor().get(2).getGraphPosition());
        Standard standard2 = new Standard("standard2","red",map.getLstHarbor().get(2).getGraphPosition());
        Fodder fodder1 = new Fodder("fodder1","red",map.getLstHarbor().get(1).getGraphPosition());
        Fodder fodder2 = new Fodder("fodder2","red",map.getLstHarbor().get(2).getGraphPosition());

        map.getLstFaction().getFirst().getLstBoat().add(military2);
        map.getLstFaction().getFirst().getLstBoat().add(merchant1);
        map.getLstFaction().getFirst().getLstBoat().add(merchant2);
        map.getLstFaction().getFirst().getLstBoat().add(standard2);
        map.getLstFaction().getFirst().getLstBoat().add(fodder1);
        map.getLstFaction().getFirst().getLstBoat().add(fodder2);

        fleet2.getArrayListFleet().add(military2);
        fleet2.getArrayListFleet().add(merchant1);
        fleet2.getArrayListFleet().add(merchant2);
        fleet2.getArrayListFleet().add(standard2);
        fleet2.getArrayListFleet().add(fodder1);
        fleet2.getArrayListFleet().add(fodder2);

        Resource resource1 = new Resource("resource1",1,1);
        Resource resource2 = new Resource("resource1",1,1);

        map.getLstHarbor().get(0).getInventory().add(resource1,10000);
        map.getLstHarbor().get(3).getInventory().add(resource2,10000);
        map.getLstHarbor().get(3).getInventory().add(resource1,10000);
        map.getLstHarbor().get(0).getInventory().add(resource2,10000);

        SeaRoad seaRoad1 = new SeaRoad(20000,map.getLstHarbor().get(0),map.getLstHarbor().get(3),resource1,resource2,1);
        SeaRoad seaRoad2 = new SeaRoad(20000,map.getLstHarbor().get(3),map.getLstHarbor().get(0),resource2,resource1,1);

        factionManager.getSeaRoadManager().setNewFleet(seaRoad1,fleet1);
        factionManager.getSeaRoadManager().setNewFleet(seaRoad2,fleet2);

        factionManager.getSeaRoadManager().setNewPath(seaRoad1, SearchInGraph.findPath(map.getLstHarbor().get(0).getGraphPosition(),map.getLstHarbor().get(3).getGraphPosition()));
        factionManager.getSeaRoadManager().setNewPath(seaRoad2, SearchInGraph.findPath(map.getLstHarbor().get(3).getGraphPosition(),map.getLstHarbor().get(0).getGraphPosition()));

        map.getPlayer().addFleet(fleet1);
        map.getLstFaction().getFirst().addFleet(fleet2);

        map.getPlayer().addSeaRoad(seaRoad1);
        map.getLstFaction().getFirst().addSeaRoad(seaRoad2);

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

        TestMove gameMainGUI = new TestMove("game");
        Thread gameThread = new Thread(gameMainGUI);
        gameThread.start();
    }



}
