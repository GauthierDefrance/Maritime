package test;

import config.GameConfiguration;
import engine.data.MapGame;
import engine.data.Fleet;
import engine.data.entity.boats.*;
import engine.process.manager.FactionManager;
import engine.data.trading.SeaRoad;

public class TestMove {
    public static void addBoatTest(){

        MapGame.getInstance().getLstHarbor().get(0).setCurrentHp(300);

        MapGame.getInstance().getPlayer().addHarbor(MapGame.getInstance().getLstHarbor().get(0));
        MapGame.getInstance().getPlayer().addHarbor(MapGame.getInstance().getLstHarbor().get(1));
        MapGame.getInstance().getLstFaction().get(0).addHarbor(MapGame.getInstance().getLstHarbor().get(MapGame.getInstance().getLstHarbor().size()-1));
        MapGame.getInstance().getLstFaction().get(0).addHarbor(MapGame.getInstance().getLstHarbor().get(MapGame.getInstance().getLstHarbor().size()-2));

        Military militaryX = new Military("militaryX","blue", MapGame.getInstance().getLstHarbor().get(0).getGraphPosition());

        Military military0 = new Military("military0","blue", MapGame.getInstance().getLstHarbor().get(2).getGraphPosition());
        Military military1 = new Military("military1","blue", MapGame.getInstance().getLstHarbor().get(1).getGraphPosition());
        Merchant merchant0 = new Merchant("merchant0","blue", MapGame.getInstance().getLstHarbor().get(0).getGraphPosition());
        Standard standard0 = new Standard("standard0","blue", MapGame.getInstance().getLstHarbor().get(1).getGraphPosition());
        Standard standard1 = new Standard("standard1","blue", MapGame.getInstance().getLstHarbor().get(2).getGraphPosition());
        Fodder fodder0 = new Fodder("fodder0","blue", MapGame.getInstance().getLstHarbor().get(2).getGraphPosition());

        MapGame.getInstance().getPlayer().getLstBoat().add(militaryX);
        MapGame.getInstance().getPlayer().getLstBoat().add(military0);
        MapGame.getInstance().getPlayer().getLstBoat().add(military1);
        MapGame.getInstance().getPlayer().getLstBoat().add(merchant0);
        MapGame.getInstance().getPlayer().getLstBoat().add(standard0);
        MapGame.getInstance().getPlayer().getLstBoat().add(standard1);
        MapGame.getInstance().getPlayer().getLstBoat().add(fodder0);

        Fleet fleet1 = new Fleet("Fleet1");
        fleet1.getArrayListBoat().add(militaryX);
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

        MapGame.getInstance().getLstFaction().get(0).getLstBoat().add(military3);
        MapGame.getInstance().getLstFaction().get(0).getLstBoat().add(military2);
        MapGame.getInstance().getLstFaction().get(0).getLstBoat().add(merchant1);
        MapGame.getInstance().getLstFaction().get(0).getLstBoat().add(merchant2);
        MapGame.getInstance().getLstFaction().get(0).getLstBoat().add(standard2);
        MapGame.getInstance().getLstFaction().get(0).getLstBoat().add(fodder1);
        MapGame.getInstance().getLstFaction().get(0).getLstBoat().add(fodder2);
        MapGame.getInstance().getLstFaction().get(0).getLstBoat().add(fodder3);

        Fleet fleet2 = MapGame.getInstance().getLstFaction().get(0).getLstFleet().get(0);
        fleet2.getArrayListBoat().add(military2);
        fleet2.getArrayListBoat().add(merchant1);
        fleet2.getArrayListBoat().add(merchant2);
        fleet2.getArrayListBoat().add(standard2);
        fleet2.getArrayListBoat().add(fodder1);
        fleet2.getArrayListBoat().add(fodder2);
        fleet2.getArrayListBoat().add(fodder3);
        fleet2.getArrayListBoat().add(military3);


        MapGame.getInstance().getLstHarbor().get(0).getInventory().add(GameConfiguration.METAL,10000);
        MapGame.getInstance().getLstHarbor().get(0).getInventory().add(GameConfiguration.WOOD,10000);
        MapGame.getInstance().getLstHarbor().get(0).getInventory().add(GameConfiguration.CLOTH,10000);
        MapGame.getInstance().getLstHarbor().get(MapGame.getInstance().getLstHarbor().size()-1).getInventory().add(GameConfiguration.WOOD,10000);
        MapGame.getInstance().getLstHarbor().get(MapGame.getInstance().getLstHarbor().size()-1).getInventory().add(GameConfiguration.METAL,10000);

        MapGame.getInstance().getLstFaction().get(0).addAmountCurrency(4000);
        MapGame.getInstance().getPlayer().addAmountCurrency(10000);


        SeaRoad seaRoad1 = new SeaRoad("seaRoad1", MapGame.getInstance().getLstHarbor().get(0), MapGame.getInstance().getLstHarbor().get(MapGame.getInstance().getLstHarbor().size()-1),GameConfiguration.METAL,GameConfiguration.GOLD,1000,2000,3000);
        SeaRoad seaRoad2 = new SeaRoad("seaRoad2", MapGame.getInstance().getLstHarbor().get(MapGame.getInstance().getLstHarbor().size()-1), MapGame.getInstance().getLstHarbor().get(0),GameConfiguration.WOOD,GameConfiguration.METAL,2000,1000,3000);

        FactionManager factionManager = FactionManager.getInstance();
        factionManager.getSeaRoadManager().setNewFleet(seaRoad1,fleet1);
        factionManager.getSeaRoadManager().setNewFleet(seaRoad2,fleet2);

        MapGame.getInstance().getPlayer().addFleet(fleet1);

        MapGame.getInstance().getPlayer().addSeaRoad(seaRoad1);
        MapGame.getInstance().getLstFaction().get(0).addSeaRoad(seaRoad2);

        MapGame.getInstance().setTimeStop(true);
    }

}

