package engine.data.entity.boats;

import config.GameConfiguration;
import engine.data.graph.GraphPoint;

public class Merchant extends Boat {
    public Merchant(String name,String color, GraphPoint position) {
        super(name,color,GameConfiguration.STANDARD_VISION_RADIUS* GameConfiguration.MERCHANT_VISION_RADIUS_BOOST,position, (int) (GameConfiguration.STANDARD_HP* GameConfiguration.MERCHANT_HP_BOOST), (int) (GameConfiguration.STANDARD_DAMAGE_SPEED*GameConfiguration.MERCHANT_DAMAGE_SPEED_BOOST), (int) (GameConfiguration.STANDARD_SPEED*GameConfiguration.MERCHANT_SPEED_BOOST),(int) (GameConfiguration.STANDARD_INVENTORY_SIZE*GameConfiguration.MERCHANT_INVENTORY_SIZE_BOOST));
    }

    public void upgradeHp() {
        useUpgradePoint();
        this.setMaxHp((int) (this.getMaxHp()+(GameConfiguration.UPGRADE_DEFAULT_HP*GameConfiguration.MERCHANT_HP_BOOST)));
    }

    public void upgradeDamageSpeed() {
        useUpgradePoint();
        this.setDamageSpeed((int) (this.getDamageSpeed()+(GameConfiguration.UPGRADE_DEFAULT_DAMAGE_SPEED*GameConfiguration.MERCHANT_DAMAGE_SPEED_BOOST)));
    }

    public void upgradeSpeed() {
        useUpgradePoint();
        this.setSpeed((int) (this.getSpeed()+(GameConfiguration.UPGRADE_DEFAULT_SPEED*GameConfiguration.MERCHANT_SPEED_BOOST)));
    }

    public void upgradeInventorySize() {
        useUpgradePoint();
        this.getInventory().setCapacity((int) (this.getInventory().getCapacity()+(GameConfiguration.UPGRADE_DEFAULT_INVENTORY_SIZE*GameConfiguration.MERCHANT_INVENTORY_SIZE_BOOST)));
    }

}
