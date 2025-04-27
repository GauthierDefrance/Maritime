package engine.data.entity.boats;

import config.GameConfiguration;
import engine.data.graph.GraphPoint;

/**
 * class representing the extends type of Boats Military
 * @author Kenan Ammad
 * @author Zue Jack-Arthur
 * @version 1.0
 */
public class Military extends Boat {
    public Military(String name,String color, GraphPoint position) {
        super(name,color,GameConfiguration.STANDARD_VISION_RADIUS* GameConfiguration.MILITARY_VISION_RADIUS_BOOST,position, (int) (GameConfiguration.STANDARD_HP*GameConfiguration.MILITARY_HP_BOOST), (int) (GameConfiguration.STANDARD_DAMAGE_SPEED*GameConfiguration.MILITARY_DAMAGE_SPEED_BOOST), (int) (GameConfiguration.STANDARD_SPEED*GameConfiguration.MILITARY_SPEED_BOOST),(int) (GameConfiguration.STANDARD_INVENTORY_SIZE*GameConfiguration.MILITARY_INVENTORY_SIZE_BOOST));
    }

    /**
     * Upgrades the military unit's maximum and current HP using an upgrade point.
     * The HP gain is based on the default value and the military-specific HP boost multiplier.
     */
    public void upgradeHp() {
        useUpgradePoint();
        this.setMaxHp((this.getMaxHp() + (int)(GameConfiguration.UPGRADE_DEFAULT_HP * GameConfiguration.MILITARY_HP_BOOST)));
        this.setCurrentHp((int)(this.getCurrentHp() + (GameConfiguration.UPGRADE_DEFAULT_HP * GameConfiguration.MILITARY_HP_BOOST)));
    }

    /**
     * Upgrades the military unit's damage speed using an upgrade point.
     * The gain is calculated based on default values and military-specific boost.
     */
    public void upgradeDamageSpeed() {
        useUpgradePoint();
        this.setDamageSpeed((int)(this.getDamageSpeed() + (GameConfiguration.UPGRADE_DEFAULT_DAMAGE_SPEED * GameConfiguration.MILITARY_DAMAGE_SPEED_BOOST)));
    }

    /**
     * Upgrades the military unit's movement speed using an upgrade point.
     * Includes an additional flat speed bonus (+1) on top of the scaled boost.
     */
    public void upgradeSpeed() {
        useUpgradePoint();
        this.setSpeed((int)(this.getSpeed() + (GameConfiguration.UPGRADE_DEFAULT_SPEED * GameConfiguration.MILITARY_SPEED_BOOST + 1)));
    }

    /**
     * Increases the military unit's inventory capacity using an upgrade point.
     * Uses the default inventory upgrade value and military-specific multiplier.
     */
    public void upgradeInventorySize() {
        useUpgradePoint();
        this.getInventory().setCapacity((int)(this.getInventory().getCapacity() + (GameConfiguration.UPGRADE_DEFAULT_INVENTORY_SIZE * GameConfiguration.MILITARY_INVENTORY_SIZE_BOOST)));
    }

    /**
     * Calculates the military unit's max HP after the next upgrade.
     * @return {@link Integer} The future max HP after the upgrade.
     */
    public int nextUpgradeHp() {
        return (this.getMaxHp() + (int)(GameConfiguration.UPGRADE_DEFAULT_HP * GameConfiguration.MILITARY_HP_BOOST));
    }

    /**
     * Calculates the military unit's damage speed after the next upgrade.
     * @return {@link Integer} The future damage speed after the upgrade.
     */
    public int nextUpgradeDamageSpeed() {
        return (int)(this.getDamageSpeed() + (GameConfiguration.UPGRADE_DEFAULT_DAMAGE_SPEED * GameConfiguration.MILITARY_DAMAGE_SPEED_BOOST));
    }

    /**
     * Calculates the military unit's speed after the next upgrade.
     * Includes an additional flat bonus of 1.
     * @return {@link Integer} The future speed after the upgrade.
     */
    public int nextUpgradeSpeed() {
        return (int)(this.getSpeed() + (GameConfiguration.UPGRADE_DEFAULT_SPEED * GameConfiguration.MILITARY_SPEED_BOOST + 1));
    }

    /**
     * Calculates the military unit's inventory capacity after the next upgrade.
     * @return {@link Integer} The future inventory size after the upgrade.
     */
    public int nextUpgradeInventorySize() {
        return (int)(this.getInventory().getCapacity() + (GameConfiguration.UPGRADE_DEFAULT_INVENTORY_SIZE * GameConfiguration.MILITARY_INVENTORY_SIZE_BOOST));
    }

}
