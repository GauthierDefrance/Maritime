package test;

import battleengine.factory.BattleFactory;
import engine.MapGame;
import engine.entity.boats.*;
import engine.process.builder.TradeObjectBuilder;
import engine.trading.Currency;
import engine.utilities.SearchInGraph;
import engine.process.FactionManager;
import engine.trading.Resource;
import engine.trading.SeaRoad;
import gui.MainGUI;

public class TestMove {
    public static void addBoatTest(){
        Fleet fleet1 = new Fleet("Fleet1");
        Fleet fleet2 = new Fleet("Fleet2");

        MapGame.getInstance().getPlayer().addHarbor(MapGame.getInstance().getLstHarbor().get(0));
        MapGame.getInstance().getPlayer().addHarbor(MapGame.getInstance().getLstHarbor().get(1));
        MapGame.getInstance().getLstFaction().get(0).addHarbor(MapGame.getInstance().getLstHarbor().get(3));

        Military military0 = new Military("military0","blue", MapGame.getInstance().getLstHarbor().get(2).getGraphPosition());
        Military military1 = new Military("military1","blue", MapGame.getInstance().getLstHarbor().get(1).getGraphPosition());
        Merchant merchant0 = new Merchant("merchant0","blue", MapGame.getInstance().getLstHarbor().get(2).getGraphPosition());
        Standard standard0 = new Standard("standard0","blue", MapGame.getInstance().getLstHarbor().get(1).getGraphPosition());
        Standard standard1 = new Standard("standard1","blue", MapGame.getInstance().getLstHarbor().get(2).getGraphPosition());
        Fodder fodder0 = new Fodder("fodder0","blue", MapGame.getInstance().getLstHarbor().get(2).getGraphPosition());

        MapGame.getInstance().getPlayer().getLstBoat().add(military0);
        MapGame.getInstance().getPlayer().getLstBoat().add(military1);
        MapGame.getInstance().getPlayer().getLstBoat().add(merchant0);
        MapGame.getInstance().getPlayer().getLstBoat().add(standard0);
        MapGame.getInstance().getPlayer().getLstBoat().add(standard1);
        MapGame.getInstance().getPlayer().getLstBoat().add(fodder0);

        fleet1.getArrayListBoat().add(military0);
        fleet1.getArrayListBoat().add(military1);
        fleet1.getArrayListBoat().add(merchant0);
        fleet1.getArrayListBoat().add(standard0);
        fleet1.getArrayListBoat().add(standard1);
        fleet1.getArrayListBoat().add(fodder0);

        Military military2 = new Military("military2","red", MapGame.getInstance().getLstHarbor().get(2).getGraphPosition());
        Military military3 = new Military("military3","red", MapGame.getInstance().getLstHarbor().get(2).getGraphPosition());
        Merchant merchant1 = new Merchant("merchant1","red", MapGame.getInstance().getLstHarbor().get(1).getGraphPosition());
        Merchant merchant2 = new Merchant("merchant2","red", MapGame.getInstance().getLstHarbor().get(2).getGraphPosition());
        Standard standard2 = new Standard("standard2","red", MapGame.getInstance().getLstHarbor().get(2).getGraphPosition());
        Fodder fodder1 = new Fodder("fodder1","red", MapGame.getInstance().getLstHarbor().get(1).getGraphPosition());
        Fodder fodder2 = new Fodder("fodder2","red", MapGame.getInstance().getLstHarbor().get(2).getGraphPosition());
        Fodder fodder3 = new Fodder("fodder3","red", MapGame.getInstance().getLstHarbor().get(2).getGraphPosition());

        MapGame.getInstance().getLstFaction().get(0).getLstBoat().add(military2);
        MapGame.getInstance().getLstFaction().get(0).getLstBoat().add(merchant1);
        MapGame.getInstance().getLstFaction().get(0).getLstBoat().add(merchant2);
        MapGame.getInstance().getLstFaction().get(0).getLstBoat().add(standard2);
        MapGame.getInstance().getLstFaction().get(0).getLstBoat().add(fodder1);
        MapGame.getInstance().getLstFaction().get(0).getLstBoat().add(fodder2);

        fleet2.getArrayListBoat().add(military2);
        fleet2.getArrayListBoat().add(merchant1);
        fleet2.getArrayListBoat().add(merchant2);
        fleet2.getArrayListBoat().add(standard2);
        fleet2.getArrayListBoat().add(fodder1);
        fleet2.getArrayListBoat().add(fodder2);
        fleet2.getArrayListBoat().add(fodder3);
        fleet2.getArrayListBoat().add(military3);

        TradeObjectBuilder builder = new TradeObjectBuilder();
        Resource metal = builder.name("Metal").value(10).productionRate(1).BuildResource();
        Resource wood = builder.name("Wood").value(2).productionRate(3).BuildResource();
        Currency gold = builder.name("Gold").value(1).BuildCurrency();

        MapGame.getInstance().getLstHarbor().get(0).getInventory().add(metal,10000);
        MapGame.getInstance().getLstHarbor().get(0).getInventory().add(metal,10000);
        MapGame.getInstance().getLstHarbor().get(3).getInventory().add(wood,10000);
        MapGame.getInstance().getLstHarbor().get(3).getInventory().add(metal,10000);
        MapGame.getInstance().getLstHarbor().get(0).getInventory().add(wood,10000);

        SeaRoad seaRoad1 = new SeaRoad(20000, MapGame.getInstance().getLstHarbor().get(0), MapGame.getInstance().getLstHarbor().get(3),metal,wood,1,"seaRoad1");
        SeaRoad seaRoad2 = new SeaRoad(20000, MapGame.getInstance().getLstHarbor().get(3), MapGame.getInstance().getLstHarbor().get(0),wood,metal,1,"seaRoad2");

        FactionManager factionManager = new FactionManager();
        factionManager.getSeaRoadManager().setNewFleet(seaRoad1,fleet1);
        factionManager.getSeaRoadManager().setNewFleet(seaRoad2,fleet2);

        factionManager.getSeaRoadManager().setNewPath(seaRoad1, SearchInGraph.findPath(MapGame.getInstance().getLstHarbor().get(0).getGraphPosition(), MapGame.getInstance().getLstHarbor().get(3).getGraphPosition()));
        factionManager.getSeaRoadManager().setNewPath(seaRoad2, SearchInGraph.findPath(MapGame.getInstance().getLstHarbor().get(3).getGraphPosition(), MapGame.getInstance().getLstHarbor().get(0).getGraphPosition()));

        MapGame.getInstance().getPlayer().addFleet(fleet1);
        MapGame.getInstance().getLstFaction().get(0).addFleet(fleet2);

        MapGame.getInstance().getPlayer().addSeaRoad(seaRoad1);
        MapGame.getInstance().getLstFaction().get(0).addSeaRoad(seaRoad2);

        MainGUI.setBattle(BattleFactory.createBattle(fleet1, fleet2));
        MapGame.getInstance().setTimeStop(true);
    }
}

