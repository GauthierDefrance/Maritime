package config;

import engine.data.trading.Currency;
import engine.data.trading.Resource;
import engine.data.trading.SeaRoad;
import engine.process.creational.TradeObjectBuilder;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * class containing constants necessary to the game execution
 */
public final class GameConfiguration {

    //-------- Game Stats --------
        public static final int NUMBER_OF_BACK_GROUND_FRAMES = 8;
        public static final int GAME_SPEED = 100; //ms per frame
        public static final int GAME_SCALE = 5;

    //--------------------------------

    //-------- Bot Stats --------

    public static final int MAX_VALUE_IN_INVENTORY_BOT = 10000;
    public static final int GAME_FLEET_BOT_SIZE = 5;
    public static final int GAME_PIRATE_MAX_BOAT = 4;
    public static final int GAME_FLEET_PIRATE_SIZE = 3;
    public static final int GAME_FLEET_SPAWN_TIME = 2000;

    //--------------------------------



    //-------- Harbor Stats --------
        public static final double HARBOR_VISION_RADIUS = 700;
        public static final double HARBOR_HP = 500;
        public static final int DAMAGE_TAKEN = 3;
        public static final int RELOAD_TIME_DAMAGE_HARBOR = 50;

    //--------------------------------

    //-------- Resource --------

    static TradeObjectBuilder tob = new TradeObjectBuilder();
    public static final Resource WOOD = tob.name("Wood").value(1).productionRate(50).buildResource();
    public static final Resource CLOTH = tob.name("Cloth").value(2).productionRate(80).buildResource();
    public static final Resource METAL = tob.name("Metal").value(4).productionRate(150).buildResource();
    public static final Resource SUGAR = tob.name("Sugar").value(40).productionRate(500).buildResource();
    public static final Resource CACAO = tob.name("Cacao").value(60).productionRate(1000).buildResource();
    public static final Resource PEARL = tob.name("Pearl").value(120).buildResource();
    public static final Currency GOLD = tob.name("Gold").value(1).buildCurrency();
    public static final ArrayList<Resource> LIST_RESOURCE = new ArrayList<>(Arrays.asList(WOOD,CLOTH,METAL,SUGAR,CACAO,PEARL));


    public static final int SEAROAD_TIME = 3000;

    //--------------------------------


    //-------- Global Upgrade -------

    public static final int COST_GENERATOR = 30000;
    public static final int COST_LEVEL_UP_HARBOR = 2000;
    public static final int COST_LEVEL_UP_BOAT = 200;
    public static final int COST_HEAL_HARBOR = 1;
    public static final int COST_HEAL_BOAT = 1;

    public static final int UPGRADE_POINT_DEFAULT = 5;
    public static final int UPGRADE_DEFAULT_HP = 100;
    public static final int UPGRADE_HARBOR_HP = 200;
    public static final double UPGRADE_DEFAULT_DAMAGE_SPEED = 1;
    public static final double UPGRADE_DEFAULT_SPEED = 1;
    public static final double UPGRADE_DEFAULT_INVENTORY_SIZE = 50;

    //--------------------------------


    //-------- Battle Stats -------

        public static final int MAX_X = 640*GAME_SCALE;
        public static final int MAX_Y = 360*GAME_SCALE-57*GAME_SCALE;
        public static final int MIN_X = 0;
        public static final int MIN_Y = 57*GAME_SCALE;

    //--------------------------------


    //-------- Boats Stats --------
            public static final int RELOAD_TIME = 20;

        // -> Global Stat
            public static final double BOAT_ROTATION_SPEED=0.0174533*3;
            public static final double GO_BACK_BOOST = 1.2;
            public static final double HITBOX_BOAT = 80;
            public static final int TRANSPARENCY_HALO = 25;

        // -> Standard Stat
            public static final double STANDARD_VISION_RADIUS = 300;
            public static final double STANDARD_HP = 100;
            public static final double STANDARD_SPEED = 5;
            public static final double STANDARD_DAMAGE_SPEED = 5;
            public static final double STANDARD_INVENTORY_SIZE = 100;

        // -> Fodder Stat
            public static final double FODDER_VISION_RADIUS_BOOST = 0.5;
            public static final double FODDER_HP_BOOST = 0.5;
            public static final double FODDER_SPEED_BOOST = 0.90;
            public static final double FODDER_DAMAGE_SPEED_BOOST = 0.75;
            public static final double FODDER_INVENTORY_SIZE_BOOST = 0.5;

        // -> Merchant Stat
            public static final double MERCHANT_VISION_RADIUS_BOOST = 1;
            public static final double MERCHANT_HP_BOOST = 2;
            public static final double MERCHANT_SPEED_BOOST = 0.75;
            public static final double MERCHANT_DAMAGE_SPEED_BOOST = 1.25;
            public static final double MERCHANT_INVENTORY_SIZE_BOOST = 3;

        // -> Military Stat

            public static final double MILITARY_VISION_RADIUS_BOOST = 1.5;
            public static final double MILITARY_HP_BOOST = 1.2;
            public static final double MILITARY_SPEED_BOOST = 1.5;
            public static final double MILITARY_DAMAGE_SPEED_BOOST = 3;
            public static final double MILITARY_INVENTORY_SIZE_BOOST = 0.4;

    //--------------------------------


    //-------- Faction Stats --------
        //-> Global Stats
            public static final int WAR_THRESHOLD = -100;
            public static final int BFF_THRESHOLD = 100;

    //--------------------------------


    //-------- Bullets Stats --------
        //-> Global Stats
            public static final int DEFAULT_BULLET_SPEED=40;
            public static final double DEFAULT_BULLET_FRICTION=1.15*0.5;
            public static final int DAMAGE_PER_BULLET = 5;

        //-> Bullet Math stats
            public static final int DEFAULT_SHOOT_DISTANCE=2;
            public static final double DEFAULT_MIN_SHOOTING_ANGLE=Math.PI/4;
            public static final double DEFAULT_MAX_SHOOTING_ANGLE=Math.PI*3/4;

    //--------------------------------


    //-------- SpawnZone --------
        public static final Rectangle SPAWN_ZONE = new Rectangle(0,0,160*GAME_SCALE,360*GAME_SCALE);
        public static final Rectangle ZONE = new Rectangle(0,0,640*GAME_SCALE,360*GAME_SCALE);
        public static final int SPAWN_ZONE_MIN_Y = 60*GameConfiguration.GAME_SCALE;
        public static final int SPAWN_ZONE_MAX_Y = 300*GameConfiguration.GAME_SCALE;
        public static final int SPAWN_ZONE_STARTING_X = 600*GameConfiguration.GAME_SCALE;
        public static final int SPAWN_ZONE_STARTING_X2 = 60*GameConfiguration.GAME_SCALE;
        public static final int SPAWN_ZONE_MAX_BOATS_PER_COLUMN = 6;

    //--------------------------------



    //-------- GUI Stats --------
        // -> Global
            public static final Dimension WINDOW_SIZE = new Dimension(640, 360);
            public static final int BUTTON_SEPARATOR = 10;

        // -> Fonts
            public static final Font TITLE_FONT = new Font( "Noto Sans Display", Font.BOLD, 50);
            public static final Font FONT = new Font( "Noto Sans Display", Font.BOLD, 20);
            public static final Font CREDITS_FONT = new Font("Noto Sans Display", Font.BOLD, 10);

        // -> Background
            public static final Color WATER_BACKGROUND_COLOR = new Color(78, 172, 233);

        // -> Options stat
            public static final int MAX_SOUND_LEVEL = 10;
            public static final int MIN_SOUND_LEVEL = 0;

        // -> Token stat
            public static final int ROOT_START_MENU = 0;
            public static final int ROOT_MAIN_GAME = 1;
            public static final int ROOT_COMBAT = 2;
            public static final int ROOT_RELATION_MENU = 3;
            public static final int ROOT_PAUSE_FROM_MAIN = 4;
            public static final int ROOT_PAUSE_FROM_COMBAT = 5;
            public static final int ROOT_PAUSE_FROM_RELATION = 6;
    //--------------------------------


    //-------- Save Stats --------
        public static final String SAVE_FILE_PATH = "src/saveFiles/";

    //--------------------------------


    //-------- Images Stats --------
        public static final String IMG_FILE_PATH = "src/images";

    //--------------------------------


    //-------- Log Stats --------
        public static final String HTML_LOG_CONFIG = "src/log/log4j-html.properties";

    //--------------------------------


}
