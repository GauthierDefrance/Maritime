package engine.process.manager;

import config.GameConfiguration;
import engine.MapGame;
import engine.battleengine.data.Battle;
import engine.data.entity.Harbor;
import engine.data.entity.boats.*;
import engine.data.Fleet;
import engine.data.faction.Faction;
import engine.data.graph.GraphPoint;
import engine.process.creational.EngineBuilder;
import engine.data.trading.SeaRoad;
import engine.utilities.SearchInGraph;
import gui.PopUp;
import gui.process.ImageStock;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Class Handling everything related directly to Factions in this game
 * @author Kenan Ammad
 * @author Zue Jack-Arthur
 * @version 0.6
 */
public class FactionManager {
    public static FactionManager instance;
    private final PlayerManager playerManager;
    private final BoatManager boatManager;
    private final HarborManager harborManager;
    private final FleetManager fleetManager;
    private final SeaRoadManager seaRoutManager;
    private boolean needUpdate;
    private AbstractMap.SimpleEntry<Battle, Boolean> battleTime;


    /**
     * Typical builder generating an FactionManager
     */
    private FactionManager() {
        this.playerManager = new PlayerManager();
        this.boatManager = new BoatManager();
        this.harborManager = new HarborManager();
        this.fleetManager = new FleetManager();
        this.seaRoutManager = new SeaRoadManager(this.fleetManager, this.boatManager);
        this.needUpdate = false;
        this.battleTime = null;
    }

    public boolean needUpdate(){
        if(needUpdate){
            needUpdate = false;
            return true;
        }
        return false;
    }

    public AbstractMap.SimpleEntry<Battle, Boolean> needBattle(){
        if(battleTime != null){
            AbstractMap.SimpleEntry<Battle, Boolean> tmp = battleTime;
            battleTime = null;
            return tmp;
        }
        return null;
    }

    public synchronized static FactionManager getInstance() {
        if (instance == null) {
            instance = new FactionManager();
        } return instance;
    }

    public void nextRound(){
        moveAllFactionBoat();
        allSeaRoadUpdate();
        allFleetUpdate();
        startChase();
        botsActions();
        allBoatApproachingHarbor();
        playerManager.updatePlayerVision();
        updateAllGeneratorTime();
        allChaseUpdate();
    }

    public void updateAllGeneratorTime(){
        for (Harbor harbor : MapGame.getInstance().getLstHarbor()){
            harborManager.updateGeneratorTime(harbor);
        }
    }

    public void allBoatApproachingHarbor(){
        for (Harbor harbor : MapGame.getInstance().getLstHarbor()){
            harborManager.boatApproachingHarbor(harbor);
        }
    }

    /**
     * Take all faction boat and make it follow its path, don't do anything if the path is empty
     */
    public void moveFactionBoat(Faction faction){
        for (Boat boat : faction.getLstBoat()){
            boatManager.followThePath(boat);
        }
    }

    /**
     * Take all map boat and make it follow its path, don't do anything if the path is empty
     */
    public void moveAllFactionBoat(){
        for (Faction faction : MapGame.getInstance().getLstFaction()){
            moveFactionBoat(faction);
        }
    }

    /**
     * Take a faction and for all faction fleet update all boat fleet path if is empty
     */
    public void fleetUpdate(Faction faction){
        for (Fleet fleet : faction.getLstFleet()){
            fleetManager.pathUpdate(fleet);
        }
    }

    /**
     * Take a map and for all faction fleet update all boat fleet path if is empty
     */
    public void allFleetUpdate(){
        for (Faction faction : MapGame.getInstance().getLstFaction()){
            fleetUpdate(faction);
        }
    }

    /**
     * for all faction sea road boat pickUpResources and sellResources and remove sea road if timer < 0
     */
    public void seaRoadUpdate(Faction faction){
        ArrayList<SeaRoad> lstSeaRouts = new ArrayList<>();
        for (SeaRoad seaRoad : faction.getLstSeaRouts()){
            if(seaRoad.isActive()) {
                seaRoutManager.sellAndPickUpAllResources(seaRoad);
            }
            else {
                needUpdate = true;
                if(seaRoad.getTimer()<=0){
                    modifyRelationship(faction,getMyFaction(seaRoad.getTargetHarbor().getColor()),-10);
                }
                else if(seaRoad.getSelection().getValue()==-1||seaRoad.getDemand().getValue()==-1){
                    modifyRelationship(getMyFaction(seaRoad.getTargetHarbor().getColor()),faction,-10);
                }
                else {
                    modifyRelationship(getMyFaction(seaRoad.getTargetHarbor().getColor()),faction,10);
                    modifyRelationship(faction,getMyFaction(seaRoad.getTargetHarbor().getColor()),10);
                }
                lstSeaRouts.add(seaRoad);
                fleetManager.removePath(seaRoad.getFleet());
                if(faction.equals(MapGame.getInstance().getPlayer()))getHarborManager().addFleetInHarbor(seaRoad.getSellerHarbor(),seaRoad.getFleet());
            }
        }
        faction.getLstSeaRouts().removeAll(lstSeaRouts);
    }

    /**
     * for all map faction sea road boat pickUpResources and sellResources and remove sea road if timer < 0
     */
    public void allSeaRoadUpdate(){
        for (Faction faction : MapGame.getInstance().getLstFaction()){
            seaRoadUpdate(faction);
        }
    }

    /**
     * Initialize a chase between two boats
     * @param hunter chasing boat
     * @param prey chased boat
     */
    public void chaseBoat(Boat hunter, Boat prey){
        MapGame.getInstance().addHunterPreyHashMap(hunter,prey);
        hunter.setPath(new ArrayList<>(Collections.singleton(new GraphPoint(prey.getPosition(), "target"))));
        hunter.setIPath(0);
        hunter.setContinuePath(false);
    }

    /**
     * Remove a chase between two boats
     * @param hunter chasing boat
     */
    public void chaseBoatRemove(Boat hunter){
        MapGame.getInstance().removeHunterPreyHashMap(hunter);
        hunter.getPath().clear();
        hunter.setNextGraphPoint(SearchInGraph.getClosestMapGraphPoint(hunter.getPosition()));
    }

    /**
     * Take two boats starts a fight if they are in contact cancels the chase if they are too far away
     */
    public ArrayList<Boat> chaseUpdate(Boat hunter,Boat prey){
        double distance = hunter.getPosition().distance(prey.getPosition());
        if(GameConfiguration.HITBOX_BOAT-5 >= distance){
            MapGame.getInstance().removeHunterPreyHashMap(hunter);
            hunter.getPath().clear();
            hunter.setNextGraphPoint(prey.getNextGraphPoint());
            ArrayList<Boat> lst = new ArrayList<>();
            lst.add(hunter);
            lst.add(prey);
            return lst;
        }
        else{
            boolean flag = false;
            for (Harbor harbor :getMyFaction(prey.getColor()).getLstHarbor()){
                if (harbor.getHashMapBoat().containsKey(prey)) {
                    flag = harbor.getHashMapBoat().get(prey);
                    break;
                }
            }
            if (hunter.getVisionRadius() < distance||!getMyFaction(prey.getColor()).getLstBoat().contains(prey)||flag){
                chaseBoatRemove(hunter);
            }
        }
        return null;
    }

    /**
     * For all the boats that are on the chase starts a fight if they are in contact cancels the chase if they are too far away
     */
    public void allChaseUpdate(){
        ArrayList<Boat> lst;
        HashMap<Boat, Boat> tmp = new HashMap<>(MapGame.getInstance().getHunterPreyHashMap());
        for (Boat boat : tmp.keySet()){
            lst = chaseUpdate(boat,tmp.get(boat));
            if(lst!=null){
                battleTime = new AbstractMap.SimpleEntry<>(startBattle(lst.get(0),lst.get(1)),MapGame.getInstance().getPlayer().getLstBoat().contains(lst.get(0)));
                break;
            }
        }
    }

    public Battle startBattle(Boat hunter, Boat prey){
        Fleet fleetHunter = getMyFleet(hunter);
        Fleet fleetPrey = getMyFleet(prey);
        return EngineBuilder.createBattle(getMyFaction(hunter.getColor()),getMyFaction(prey.getColor()),fleetHunter,fleetPrey);
    }

    public Battle startBattle(Boat hunter, Harbor prey){
        Fleet fleetHunter = getMyFleet(hunter);
        ArrayList<Boat> lstBoat = new ArrayList<>();
        for (Boat boat : prey.getHashMapBoat().keySet())if(prey.getHashMapBoat().get(boat))lstBoat.add(boat);
        Fleet fleetPrey = new Fleet(lstBoat,"");
        return EngineBuilder.createBattle(getMyFaction(hunter.getColor()),getMyFaction(prey.getColor()),fleetHunter,fleetPrey);
    }

    public void startChase(){
        for(Faction hunterFaction : MapGame.getInstance().getLstBotFaction()){
            for(Boat hunter : hunterFaction.getLstBoat()) {
                boolean flag = !MapGame.getInstance().getHunterPreyHashMap().containsKey(hunter);
                for (Faction preyFaction : MapGame.getInstance().getLstFaction()) {
                    if(!hunterFaction.equals(preyFaction) && hunterFaction.getRelationship(preyFaction) <= -100) {
                        if(!hunterFaction.equals(MapGame.getInstance().getPirate())) {
                            ArrayList<Harbor> tmpHarbor = new ArrayList<>(preyFaction.getLstHarbor());
                            for (Harbor harbor : tmpHarbor) {
                                if(harbor.getGraphPosition().getPoint().equals(hunter.getPosition())){
                                    dealDamageHarbor(harbor,hunter);
                                    flag = false;
                                }
                            }
                        }
                        if (flag) {
                            for (Boat prey : preyFaction.getLstBoat()) {
                                if (flag && (hunter.getVisionRadius() / 2) >= prey.getPosition().distance(hunter.getPosition())) {
                                    chaseBoat(hunter, prey);
                                    flag = false;
                                }
                            }
                        }
                    }
                }
            }
            //for the player
            if(hunterFaction.getRelationship(MapGame.getInstance().getPlayer()) <= -100) {
                for (Boat boat : MapGame.getInstance().getPlayer().getLstBoat()) {
                    ArrayList<Harbor> tmpHarbor = new ArrayList<>(hunterFaction.getLstHarbor());
                    for (Harbor harbor : tmpHarbor) {
                        if(harbor.getGraphPosition().getPoint().equals(boat.getPosition())){
                            dealDamageHarbor(harbor,boat);
                        }
                    }
                }
            }
        }
    }

    public void warTime(Faction faction1,Faction faction2){
        for (SeaRoad seaRoad : faction1.getLstSeaRouts()){
            if(faction2.getLstHarbor().contains(seaRoad.getTargetHarbor()) || faction2.getLstHarbor().contains(seaRoad.getSellerHarbor())){
                seaRoad.abandonTask();
            }
        }
        for (SeaRoad seaRoad : faction2.getLstSeaRouts()){
            if(faction1.getLstHarbor().contains(seaRoad.getTargetHarbor()) || faction1.getLstHarbor().contains(seaRoad.getSellerHarbor())){
                seaRoad.abandonTask();
            }
        }
    }

    public void dealDamageHarbor(Harbor harbor,Boat boat){
        Random random = new Random();
        boolean flag = false;
        for (Boat boatTmp : harbor.getHashMapBoat().keySet()){
            if(harbor.getHashMapBoat().get(boatTmp)){
                flag = true;
                break;
            }
        }
        if(flag)battleTime = new AbstractMap.SimpleEntry<>(startBattle(boat,harbor),false);
        else if((((int)(MapGame.getInstance().getTime()*10)) % (GameConfiguration.RELOAD_TIME_DAMAGE_HARBOR/boat.getDamageSpeed())) == 0){
            harbor.setCurrentHp(harbor.getCurrentHp() - GameConfiguration.DAMAGE_TAKEN);
            BufferedImage sprite = ImageStock.getImage(harbor);
            MapGame.getInstance().addPopUp(new PopUp("explosion",new Point((int) (harbor.getPosition().getX()+((random.nextInt(sprite.getWidth()/2)+1)-((double) sprite.getWidth() /4))),(int) (harbor.getPosition().getY()+((random.nextInt(sprite.getHeight())+1)-((double) sprite.getHeight() /2)))), 5));
            if (harbor.getCurrentHp() <= 0) {
                getMyFaction(harbor.getColor()).removeHarbor(harbor);
                getMyFaction(boat.getColor()).addHarbor(harbor);
                harbor.setCurrentHp(harbor.getMaxHp() / 2);
                needUpdate = true;
            }
        }
    }

    public void botsActions(){
        Random random = new Random();
        for(Faction botFaction : MapGame.getInstance().getLstBotFaction()){
            boolean isWar = false;
            if(!botFaction.equals(MapGame.getInstance().getPirate())) {
                for (Boat boat : botFaction.getLstBoat()) {
                    boolean flag = false;// to know if you are already on a harbor
                    for (Faction faction : MapGame.getInstance().getLstFaction()) {
                        for (Harbor harbor : faction.getLstHarbor()) {
                            if (botFaction.getRelationship(faction) <= -100 && harbor.getGraphPosition().getPoint().equals(boat.getPosition())) {
                                flag = true;
                            }
                        }
                    }
                    for (Faction faction : MapGame.getInstance().getLstFaction()) {
                        if (botFaction.getRelationship(faction) <= -100) {
                            Harbor harbor = getMyHarbor(boat);
                            if(!getMyFleet(boat).getPath().isEmpty())fleetManager.removePath(getMyFleet(boat));
                            isWar = true;
                            if (harbor != null) {
                                getHarborManager().removeBoatInHarbor(harbor, boat);
                                getHarborManager().removeFleetInHarbor(harbor, getMyFleet(boat));
                            }
                            if (boat.getPath().isEmpty() && !flag && !faction.getLstHarbor().isEmpty()) {
                                boat.setPath(SearchInGraph.findPath(boat, faction.getLstHarbor().get(random.nextInt(faction.getLstHarbor().size())).getGraphPosition()));
                            }
                        }
                    }

                    for (Faction faction : MapGame.getInstance().getLstFaction()) {
                        if (botFaction.getRelationship(faction) <= -100) {
                            if (boat.getPath().isEmpty() && !flag && !faction.getLstBoat().isEmpty()) {
                                boat.setPath(SearchInGraph.findPath(boat, SearchInGraph.getClosestMapGraphPoint(faction.getLstBoat().get(random.nextInt(faction.getLstBoat().size())).getPosition())));
                            }
                        }
                    }
                }
                for (Fleet fleet : botFaction.getLstFleet()) {
                    if (fleet.getArrayListBoat().size() < GameConfiguration.GAME_FLEET_BOT_SIZE && (((int) (MapGame.getInstance().getTime() * 10)) % GameConfiguration.GAME_FLEET_SPAWN_TIME) == 1) {
                        Boat newBoat = getRandomBoat(botFaction, botFaction.getLstHarbor().get(random.nextInt(botFaction.getLstHarbor().size())).getGraphPosition());
                        fleet.add(newBoat);
                        botFaction.addBoat(newBoat);
                    }
                }
                if(!isWar){
                    for (Fleet fleet : botFaction.getLstFleet()){
                        if(!fleet.getContinuePath()) {
                            if (MapGame.getInstance().getLstHarbor().size() > botFaction.getLstHarbor().size()+MapGame.getInstance().getPlayer().getLstHarbor().size()) {
                                int randomInt1 = random.nextInt(botFaction.getLstHarbor().size());
                                int randomInt2 = random.nextInt(MapGame.getInstance().getLstHarbor().size());
                                while (botFaction.getLstHarbor().contains(MapGame.getInstance().getLstHarbor().get(randomInt2)) || MapGame.getInstance().getPlayer().getLstHarbor().contains(MapGame.getInstance().getLstHarbor().get(randomInt2))) {
                                    randomInt2 = random.nextInt(MapGame.getInstance().getLstHarbor().size());
                                }
                                fleetManager.setNewPath(fleet, SearchInGraph.findPath(botFaction.getLstHarbor().get(randomInt1).getGraphPosition(), MapGame.getInstance().getLstHarbor().get(randomInt2).getGraphPosition()),true);
                            }
                            else if(botFaction.getLstHarbor().size()>=2){
                                int randomInt1 = random.nextInt(botFaction.getLstHarbor().size());
                                int randomInt2 = random.nextInt(MapGame.getInstance().getLstHarbor().size());
                                while (MapGame.getInstance().getPlayer().getLstHarbor().contains(MapGame.getInstance().getLstHarbor().get(randomInt2))) {
                                    randomInt2 = random.nextInt(MapGame.getInstance().getLstHarbor().size());
                                }
                                fleetManager.setNewPath(fleet, SearchInGraph.findPath(botFaction.getLstHarbor().get(randomInt1).getGraphPosition(), MapGame.getInstance().getLstHarbor().get(randomInt2).getGraphPosition()),true);
                            }
                            else {
                                int randomInt = random.nextInt(MapGame.getInstance().getMapGraphPoint().size()-11)+11;
                                fleetManager.setNewPath(fleet, SearchInGraph.findPath(botFaction.getLstHarbor().get(0).getGraphPosition(), MapGame.getInstance().getMapGraphPoint().get(randomInt)),true);
                            }
                        }
                        else {
                            boolean needToChanger = false;
                            for (Harbor harbor : MapGame.getInstance().getPlayer().getLstHarbor()){
                                if (harbor.getGraphPosition().equals(fleet.getPath().get(0)) || harbor.getGraphPosition().equals(fleet.getPath().get(fleet.getPath().size()-1))) {
                                    needToChanger = true;
                                    for (SeaRoad seaRoad : botFaction.getLstSeaRouts()){
                                        if (seaRoad.getTargetHarbor().getGraphPosition().equals(fleet.getPath().get(0)) || seaRoad.getTargetHarbor().getGraphPosition().equals(fleet.getPath().get(fleet.getPath().size() - 1))) {
                                            needToChanger = false;
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                            if(needToChanger){
                                fleetManager.removePath(fleet);
                            }
                        }
                    }
                }
            }
            if(botFaction.getAmountCurrency()<1000){
                botFaction.addAmountCurrency(100*random.nextInt(50));
            }
            for (Harbor harbor : botFaction.getLstHarbor()){
                if(TradeManager.getInstance().totalValue(harbor.getInventory())<GameConfiguration.MAX_VALUE_IN_INVENTORY_BOT){
                    harbor.getInventory().add(GameConfiguration.LIST_RESOURCE.get(random.nextInt(GameConfiguration.LIST_RESOURCE.size())),random.nextInt(10)+1);
                }
            }
        }
        if(MapGame.getInstance().getPirate().getLstBoat().size()<GameConfiguration.GAME_FLEET_PIRATE_SIZE && (((int)(MapGame.getInstance().getTime()*10)) % GameConfiguration.GAME_FLEET_SPAWN_TIME) == 0){
            int randomInt = random.nextInt(MapGame.getInstance().getMapGraphPoint().size()-11)+11;
            Boat newBoat = getRandomBoat(MapGame.getInstance().getPirate(),MapGame.getInstance().getMapGraphPoint().get(randomInt));
            newBoat.setNextGraphPoint(SearchInGraph.getClosestMapGraphPoint(newBoat.getPosition()));
            MapGame.getInstance().getPirate().addBoat(newBoat);
        }
        for(Boat boat : MapGame.getInstance().getPirate().getLstBoat()){
            if(boat.getPath().isEmpty()){
                int randomInt = random.nextInt(MapGame.getInstance().getMapGraphPoint().size());
                boat.setPath(SearchInGraph.findPath(boat,MapGame.getInstance().getMapGraphPoint().get(randomInt)));
            }
        }
    }

    public Boat getRandomBoat(Faction faction, GraphPoint graphPoint){
        Random random = new Random();
        int randomInt = random.nextInt(4);
        switch (randomInt) {
            case 0 :{
                return new Standard("Standard", faction.getColor(), graphPoint);
            }
            case 1 :{
                return new Fodder("Fodder", faction.getColor(), graphPoint);
            }
            case 2 :{
                return new Merchant("Merchant", faction.getColor(), graphPoint);
            }
            case 3 :{
                return new Military("Military", faction.getColor(), graphPoint);
            }
            default : {
                return new Standard("Standard ", faction.getColor(), graphPoint);
            }
        }
    }

    /**
     * gives the faction associated with a color
     * @param color a String representing a color
     */
    public Faction getMyFaction(String color){
        for (Faction faction : MapGame.getInstance().getLstFaction()) {
            if (faction.getColor().equals(color)) {
                return faction;
            }
        } return new Faction("");
    }

    /**
     * Gives the Fleet associated with a boat
     */
    public Fleet getMyFleet(Boat boat){
        Fleet fleet = null;
        for(Fleet fleet2 : getMyFaction(boat.getColor()).getLstFleet()){
            if(fleet2.getArrayListBoat().contains(boat))fleet=fleet2;
        }
        if(fleet == null){
            fleet = new Fleet();
            fleet.add(boat);
        }
        return fleet;
    }

    public Harbor getMyHarbor(Boat boat){
        Harbor harbor = null;
        for(Harbor harbor2 : getMyFaction(boat.getColor()).getLstHarbor()){
            if(harbor2.getHashMapBoat().containsKey(boat))harbor=harbor2;
        }
        return harbor;
    }

    public void modifyRelationship(Faction factionTarget, Faction factionOwner, int value){
        int uncheckedResult = factionOwner.getRelationship(factionTarget) + value;
        if (uncheckedResult <= GameConfiguration.WAR_THRESHOLD) {
            factionOwner.setRelationship(factionTarget,GameConfiguration.WAR_THRESHOLD);
            warTime(factionTarget, factionOwner);
        }
        else if(uncheckedResult >= GameConfiguration.BFF_THRESHOLD){
            factionOwner.setRelationship(factionTarget,GameConfiguration.BFF_THRESHOLD);
        }
        else factionOwner.setRelationship(factionTarget,uncheckedResult);
    }

    public SeaRoadManager getSeaRoadManager() {
        return seaRoutManager;
    }

    public BoatManager getBoatManager(){
        return boatManager;
    }

    public HarborManager getHarborManager(){
        return harborManager;
    }
}
