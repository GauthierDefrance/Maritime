package engine.entity.boats;

import config.GameConfiguration;
import engine.graph.GraphPoint;

public class Military extends Boat {
    public Military(String name,String color, GraphPoint position) {
        super(name,color,GameConfiguration.STANDARD_VISION_RADIUS* GameConfiguration.MILITARY_VISION_RADIUS_BOOST,position, (int) (GameConfiguration.STANDARD_HP*GameConfiguration.MILITARY_HP_BOOST), (int) (GameConfiguration.STANDARD_DAMAGE_SPEED*GameConfiguration.MILITARY_DAMAGE_SPEED_BOOST), (int) (GameConfiguration.STANDARD_SPEED*GameConfiguration.MILITARY_SPEED_BOOST),(int) (GameConfiguration.STANDARD_INVENTORY_SIZE*GameConfiguration.MILITARY_INVENTORY_SIZE_BOOST));
    }

    public void UpgradeHp() {
        useUpgradePoint();
        this.setMaxHp((int) (this.getMaxHp()+(GameConfiguration.UPGRADE_DEFAULT_HP*GameConfiguration.MILITARY_HP_BOOST)));
    }

    public void UpgradeDamageSpeed() {
        useUpgradePoint();
        this.setDamageSpeed((int) (this.getDamageSpeed()+(GameConfiguration.UPGRADE_DEFAULT_DAMAGE_SPEED*GameConfiguration.MILITARY_DAMAGE_SPEED_BOOST)));
    }

    public void UpgradeSpeed() {
        useUpgradePoint();
        this.setSpeed((int) (this.getSpeed()+(GameConfiguration.UPGRADE_DEFAULT_SPEED*GameConfiguration.MILITARY_SPEED_BOOST)));
    }

    public void UpgradeInventorySize() {
        useUpgradePoint();
        this.getInventory().setCapacity((int) (this.getInventory().getCapacity()+(GameConfiguration.UPGRADE_DEFAULT_INVENTORY_SIZE*GameConfiguration.MILITARY_INVENTORY_SIZE_BOOST)));
    }

}
