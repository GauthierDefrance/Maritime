package engine.data.entity.boats;

import config.GameConfiguration;
import engine.data.graph.GraphPoint;

public class Standard extends Boat {
    public Standard(String name,String color, GraphPoint position) {
        super(name,color,GameConfiguration.STANDARD_VISION_RADIUS,position, (int) (GameConfiguration.STANDARD_HP), (int) (GameConfiguration.STANDARD_DAMAGE_SPEED), (int) (GameConfiguration.STANDARD_SPEED),(int) (GameConfiguration.STANDARD_INVENTORY_SIZE));
    }

    public void upgradeHp() {
        useUpgradePoint();
        this.setMaxHp((int) (this.getMaxHp()+(GameConfiguration.UPGRADE_DEFAULT_HP)));
    }

    public void upgradeDamageSpeed() {
        useUpgradePoint();
        this.setDamageSpeed((int) (this.getDamageSpeed()+(GameConfiguration.UPGRADE_DEFAULT_DAMAGE_SPEED)));
    }

    public void upgradeSpeed() {
        useUpgradePoint();
        this.setSpeed((int) (this.getSpeed()+(GameConfiguration.UPGRADE_DEFAULT_SPEED)));
    }

    public void upgradeInventorySize() {
        useUpgradePoint();
        this.getInventory().setCapacity((int) (this.getInventory().getCapacity()+(GameConfiguration.UPGRADE_DEFAULT_INVENTORY_SIZE)));
    }

}
