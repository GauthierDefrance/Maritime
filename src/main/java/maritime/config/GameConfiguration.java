package maritime.config;

import java.awt.*;

public class GameConfiguration {

    public static final int NUMBER_OF_BACK_GROUND_FRAMES = 8;
    public static final int GAME_SPEED = 100; //ms per frame
    public static final int GAME_SCALE = 3;

    public static final int Transparency_Halo = 25;

    public static final double HITBOX_BOAT = 80;

    public static final double HARBOR_VISION_RADIUS = 500;
    public static final double HARBOR_HP = 500;

    public static final double STANDARD_VISION_RADIUS = 300;
    public static final double STANDARD_HP = 100;
    public static final double STANDARD_SPEED = 5;
    public static final double STANDARD_DAMAGE_SPEED = 5;
    public static final double STANDARD_INVENTORY_SIZE = 100;

    public static final double FODDER_VISION_RADIUS_BOOST = 0.5;
    public static final double FODDER_HP_BOOST = 0.5;
    public static final double FODDER_SPEED_BOOST = 0.90;
    public static final double FODDER_DAMAGE_SPEED_BOOST = 0.75;
    public static final double FODDER_INVENTORY_SIZE_BOOST = 0.5;

    public static final double MERCHANT_VISION_RADIUS_BOOST = 1;
    public static final double MERCHANT_HP_BOOST = 1.25;
    public static final double MERCHANT_SPEED_BOOST = 0.75;
    public static final double MERCHANT_DAMAGE_SPEED_BOOST = 1.25;
    public static final double MERCHANT_INVENTORY_SIZE_BOOST = 3;

    public static final double MILITARY_VISION_RADIUS_BOOST = 1.5;
    public static final double MILITARY_HP_BOOST = 1;
    public static final double MILITARY_SPEED_BOOST = 1.5;
    public static final double MILITARY_DAMAGE_SPEED_BOOST = 3;
    public static final double MILITARY_INVENTORY_SIZE_BOOST = 0.4;

    public static final Dimension WINDOW_SIZE = new Dimension(640, 360);
    public static final Font FONT = new Font( "Noto Sans Display", Font.BOLD, 20);
    public static final Font CREDITS_FONT = new Font("Noto Sans Display", Font.BOLD, 10);


    public static final double BULLET_SPEED = 100;
    public static final double BULLET_FRICTION = 0.9;


}
