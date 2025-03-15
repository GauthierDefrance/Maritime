package test;

import battleengine.entity.Battle;
import engine.Map;
import engine.entity.boats.*;
import engine.graph.SearchInGraph;
import engine.process.FactionManager;
import engine.trading.Resource;
import engine.trading.SeaRoad;
import gui.MainGUI;

public class TestMove {
    public static void addBaotTest(){
        Fleet fleet1 = new Fleet();
        Fleet fleet2 = new Fleet();

        Map.getInstance().getPlayer().getLstFleet().add(fleet1);
        Map.getInstance().getLstFaction().get(0).getLstFleet().add(fleet2);

        Military military0 = new Military("military0","blue",Map.getInstance().getLstHarbor().get(2).getGraphPosition());
        Military military1 = new Military("military1","blue",Map.getInstance().getLstHarbor().get(1).getGraphPosition());
        Merchant merchant0 = new Merchant("merchant0","blue",Map.getInstance().getLstHarbor().get(2).getGraphPosition());
        Standard standard0 = new Standard("standard0","blue",Map.getInstance().getLstHarbor().get(1).getGraphPosition());
        Standard standard1 = new Standard("standard1","blue",Map.getInstance().getLstHarbor().get(2).getGraphPosition());
        Fodder fodder0 = new Fodder("fodder0","blue",Map.getInstance().getLstHarbor().get(2).getGraphPosition());

        Map.getInstance().getPlayer().getLstBoat().add(military0);
        Map.getInstance().getPlayer().getLstBoat().add(military1);
        Map.getInstance().getPlayer().getLstBoat().add(merchant0);
        Map.getInstance().getPlayer().getLstBoat().add(standard0);
        Map.getInstance().getPlayer().getLstBoat().add(standard1);
        Map.getInstance().getPlayer().getLstBoat().add(fodder0);

        fleet1.getArrayListBoat().add(military0);
        fleet1.getArrayListBoat().add(military1);
        fleet1.getArrayListBoat().add(merchant0);
        fleet1.getArrayListBoat().add(standard0);
        fleet1.getArrayListBoat().add(standard1);
        fleet1.getArrayListBoat().add(fodder0);

        Military military2 = new Military("military2","red",Map.getInstance().getLstHarbor().get(2).getGraphPosition());
        Merchant merchant1 = new Merchant("merchant1","red",Map.getInstance().getLstHarbor().get(1).getGraphPosition());
        Merchant merchant2 = new Merchant("merchant2","red",Map.getInstance().getLstHarbor().get(2).getGraphPosition());
        Standard standard2 = new Standard("standard2","red",Map.getInstance().getLstHarbor().get(2).getGraphPosition());
        Fodder fodder1 = new Fodder("fodder1","red",Map.getInstance().getLstHarbor().get(1).getGraphPosition());
        Fodder fodder2 = new Fodder("fodder2","red",Map.getInstance().getLstHarbor().get(2).getGraphPosition());

        Map.getInstance().getLstFaction().get(0).getLstBoat().add(military2);
        Map.getInstance().getLstFaction().get(0).getLstBoat().add(merchant1);
        Map.getInstance().getLstFaction().get(0).getLstBoat().add(merchant2);
        Map.getInstance().getLstFaction().get(0).getLstBoat().add(standard2);
        Map.getInstance().getLstFaction().get(0).getLstBoat().add(fodder1);
        Map.getInstance().getLstFaction().get(0).getLstBoat().add(fodder2);

        fleet2.getArrayListBoat().add(military2);
        fleet2.getArrayListBoat().add(merchant1);
        fleet2.getArrayListBoat().add(merchant2);
        fleet2.getArrayListBoat().add(standard2);
        fleet2.getArrayListBoat().add(fodder1);
        fleet2.getArrayListBoat().add(fodder2);

        Resource resource1 = new Resource("resource1",1,1);
        Resource resource2 = new Resource("resource1",1,1);

        Map.getInstance().getLstHarbor().get(0).getInventory().add(resource1,10000);
        Map.getInstance().getLstHarbor().get(3).getInventory().add(resource2,10000);
        Map.getInstance().getLstHarbor().get(3).getInventory().add(resource1,10000);
        Map.getInstance().getLstHarbor().get(0).getInventory().add(resource2,10000);

        SeaRoad seaRoad1 = new SeaRoad(20000,Map.getInstance().getLstHarbor().get(0),Map.getInstance().getLstHarbor().get(3),resource1,resource2,1);
        SeaRoad seaRoad2 = new SeaRoad(20000,Map.getInstance().getLstHarbor().get(3),Map.getInstance().getLstHarbor().get(0),resource2,resource1,1);

        FactionManager factionManager = new FactionManager();
        factionManager.getSeaRoadManager().setNewFleet(seaRoad1,fleet1);
        factionManager.getSeaRoadManager().setNewFleet(seaRoad2,fleet2);

        factionManager.getSeaRoadManager().setNewPath(seaRoad1, SearchInGraph.findPath(Map.getInstance().getLstHarbor().get(0).getGraphPosition(),Map.getInstance().getLstHarbor().get(3).getGraphPosition()));
        factionManager.getSeaRoadManager().setNewPath(seaRoad2, SearchInGraph.findPath(Map.getInstance().getLstHarbor().get(3).getGraphPosition(),Map.getInstance().getLstHarbor().get(0).getGraphPosition()));

        Map.getInstance().getPlayer().addFleet(fleet1);
        Map.getInstance().getLstFaction().get(0).addFleet(fleet2);

        Map.getInstance().getPlayer().addSeaRoad(seaRoad1);
        Map.getInstance().getLstFaction().get(0).addSeaRoad(seaRoad2);

        MainGUI.setBattle(new Battle(fleet1,fleet2));
        Map.getInstance().setTimeStop(true);
    }
}

