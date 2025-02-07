package maritime.config;

import maritime.engine.entity.Harbor;
import maritime.engine.faction.Faction;
import maritime.engine.faction.Player;
import maritime.engine.graph.GraphPoint;

import java.util.ArrayList;
import java.util.HashMap;

public class MapConfig1 extends MapConfig{

    public MapConfig1(){
        init();
    }

    @Override
    public void init(){
        ArrayList<Harbor> lstfarbor = new ArrayList<>();
        ArrayList<Faction> lstFaction = new ArrayList<>();
        ArrayList<Faction> lstBotFaction = new ArrayList<>();
        HashMap<String, GraphPoint> mapGraphPoint = new HashMap<>();
        Player player = new Player("blue");

//        map.put("A",new GraphPoint(new Point(0*GameConfiguration.GAME_SCALE,0*GameConfiguration.GAME_SCALE),"A"));


        lstFaction.addAll(lstBotFaction);
        lstFaction.addLast(player);
        this.setLstHarbor(lstfarbor);
        this.setLstFaction(lstFaction);
        this.setLstBotFaction(lstBotFaction);
        this.setMapGraphPoint(mapGraphPoint);
        this.setPlayer(player);
    }
}
