package engine.data.entity.boats;

import config.GameConfiguration;
import engine.data.graph.GraphPoint;

/**
 * class representing the extends type of Boats Fodder
 * @author Kenan Ammad
 * @author Zue Jack-Arthur
 * @version 0.2
 */
public class Fodder extends Boat {
    public Fodder(String name,String color, GraphPoint position) {
        super(name,color,GameConfiguration.STANDARD_VISION_RADIUS*GameConfiguration.FODDER_VISION_RADIUS_BOOST,position, (int) (GameConfiguration.STANDARD_HP*GameConfiguration.FODDER_HP_BOOST), (int) (GameConfiguration.STANDARD_DAMAGE_SPEED*GameConfiguration.FODDER_DAMAGE_SPEED_BOOST), (int) (GameConfiguration.STANDARD_SPEED*GameConfiguration.FODDER_SPEED_BOOST),(int) (GameConfiguration.STANDARD_INVENTORY_SIZE*GameConfiguration.FODDER_INVENTORY_SIZE_BOOST));
    }

    /**
     * Increases the unit's maximum and current HP using an upgrade point.
     * The increase is calculated using the default HP upgrade value and
     * the unit's specific HP boost multiplier.
     */
    public void upgradeHp() {
        useUpgradePoint();
        this.setMaxHp((int) (this.getMaxHp()+(GameConfiguration.UPGRADE_DEFAULT_HP*GameConfiguration.FODDER_HP_BOOST)));
        this.setCurrentHp((int) (this.getCurrentHp()+(GameConfiguration.UPGRADE_DEFAULT_HP*GameConfiguration.FODDER_HP_BOOST)));
    }

    /**
     * Increases the unit's damage speed using an upgrade point.
     * The increase is based on the default damage speed upgrade value
     * and the unit's specific damage speed boost multiplier.
     */
    public void upgradeDamageSpeed() {
        useUpgradePoint();
        this.setDamageSpeed((int) (Math.max(1,this.getDamageSpeed()+(GameConfiguration.UPGRADE_DEFAULT_DAMAGE_SPEED*GameConfiguration.FODDER_DAMAGE_SPEED_BOOST))));
    }

    /**
     * Increases the unit's movement speed using an upgrade point.
     * The increase is calculated using the default speed upgrade value
     * and the unit's specific speed boost multiplier.
     */
    public void upgradeSpeed() {
        useUpgradePoint();
        this.setSpeed((int) (this.getSpeed()+(Math.max(1,GameConfiguration.UPGRADE_DEFAULT_SPEED*GameConfiguration.FODDER_SPEED_BOOST))));
    }

    /**
     * Increases the capacity of the unit's inventory using an upgrade point.
     * The increase is based on the default inventory size upgrade value
     * and the unit's specific inventory boost multiplier.
     */
    public void upgradeInventorySize() {
        useUpgradePoint();
        this.getInventory().setCapacity((int) (this.getInventory().getCapacity()+(GameConfiguration.UPGRADE_DEFAULT_INVENTORY_SIZE*GameConfiguration.FODDER_INVENTORY_SIZE_BOOST)));
    }

    /**
     * Calculates the unit's maximum HP after the next upgrade, without applying it.
     * @return {@link Integer} The potential maximum HP after the next upgrade.
     */
    public int nextUpgradeHp() {
        return (int) (this.getMaxHp()+(GameConfiguration.UPGRADE_DEFAULT_HP*GameConfiguration.FODDER_HP_BOOST));
    }

    /**
     * Calculates the unit's damage speed after the next upgrade, without applying it.
     * @return {@link Integer} The potential damage speed after the next upgrade.
     */
    public int nextUpgradeDamageSpeed() {
        return (int) (Math.max(1,this.getDamageSpeed()+(GameConfiguration.UPGRADE_DEFAULT_DAMAGE_SPEED*GameConfiguration.FODDER_DAMAGE_SPEED_BOOST)));
    }

    /**
     * Calculates the unit's movement speed after the next upgrade, without applying it.
     * @return {@link Integer} The potential movement speed after the next upgrade.
     */
    public int nextUpgradeSpeed() {
        return (int) (this.getSpeed()+(Math.max(1,GameConfiguration.UPGRADE_DEFAULT_SPEED*GameConfiguration.FODDER_SPEED_BOOST)));
    }

    /**
     * Calculates the inventory capacity after the next upgrade, without applying it.
     * @return {@link Integer} The potential inventory size after the next upgrade.
     */
    public int nextUpgradeInventorySize() {
        return (int) (this.getInventory().getCapacity()+(GameConfiguration.UPGRADE_DEFAULT_INVENTORY_SIZE*GameConfiguration.FODDER_INVENTORY_SIZE_BOOST));
    }

}