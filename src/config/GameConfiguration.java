package config;

import java.awt.*;

/**
 * class containing constants necessary to the game execution
 */
public final class GameConfiguration {

    //-------- Game Stats --------
        public static final int NUMBER_OF_BACK_GROUND_FRAMES = 8;
        public static final int GAME_SPEED = 100; //ms per frame
        public static final int GAME_SCALE = 3;

    //--------------------------------



    //-------- Harbor Stats --------
        public static final double HARBOR_VISION_RADIUS = 500;
        public static final double HARBOR_HP = 500;

    //--------------------------------



    //-------- Boats Stats --------
            public static final int RELOAD_TIME = 20;
        // -> Global Stat
            public static final double BOAT_ROTATION_SPEED=0.0174533*6;
            public static final double HITBOX_BOAT = 80;
            public static final int Transparency_Halo = 25;

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
            public static final double MERCHANT_HP_BOOST = 1.25;
            public static final double MERCHANT_SPEED_BOOST = 0.75;
            public static final double MERCHANT_DAMAGE_SPEED_BOOST = 1.25;
            public static final double MERCHANT_INVENTORY_SIZE_BOOST = 3;

        // -> Military Stat
            public static final double MILITARY_VISION_RADIUS_BOOST = 1.5;
            public static final double MILITARY_HP_BOOST = 1;
            public static final double MILITARY_SPEED_BOOST = 1.5;
            public static final double MILITARY_DAMAGE_SPEED_BOOST = 3;
            public static final double MILITARY_INVENTORY_SIZE_BOOST = 0.4;

    //--------------------------------



    //-------- Bullets Stats --------
        //-> Global Stats
            public static final int DEFAULT_BULLET_SPEED=10*GAME_SCALE;
            public static final double DEFAULT_BULLET_FRICTION=1.15*0.5;
            public static final int DAMAGE_PER_BULLET = 1;

        //-> Bullet Math stats
            public static final int DEFAULT_SHOOT_DISTANCE=1;
            public static final double DEFAULT_SHOOTING_ANGLE=Math.PI/4;
            public static final int DEFAULT_WIDTH_BULLET_SPAWN=10;
            public static final int DEFAULT_HEIGHT_BULLET_SPAWN=4;

    //--------------------------------



    //-------- SpawnZone Stats --------
        public static final int SPAWN_ZONE_OPACITY = 15;
        public static final String DEFAULT_SPAWN_ZONE_COLOR ="blue";
        public static final Point DEFAULT_SPAWN_ZONE_COORDINATE= new Point(0,0);
        public static final Point DEFAULT_ENEMY_SPAWN_ZONE_COORDINATE= new Point(300,0);
        public static final int DEFAULT_SPAWN_ZONE_WIDTH = 200;
        public static final int DEFAULT_SPAWN_ZONE_HEIGHT = 600;

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
            public static final Color DEFAULT_BACKGROUND_COLOR = new Color(0,0,0,0);
            public static final Color WATER_BACKGROUND_COLOR = new Color(78, 172, 233);

        // -> Options stat
            public static final int MAX_SOUND_LEVEL = 10;
            public static final int MIN_SOUND_LEVEL = 0;

        // -> Token stat
            public static final int ROOT_START_MENU = 0;
            public static final int ROOT_MAIN_GAME = 1;
            public static final int ROOT_COMBAT = 2;
            public static final int ROOT_PAUSE_FROM_MAIN = 3;
            public static final int ROOT_PAUSE_FROM_COMBAT = 4;

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
