package engine.data.entity.boats;

import config.GameConfiguration;
import engine.data.graph.GraphPoint;

/**
 * class representing the extends type of Boats Merchant
 * @author Kenan Ammad
 * @author Zue Jack-Arthur
 * @version 1.0
 */
public class Merchant extends Boat {
    public Merchant(String name,String color, GraphPoint position) {
        super(name,color,GameConfiguration.STANDARD_VISION_RADIUS* GameConfiguration.MERCHANT_VISION_RADIUS_BOOST,position, (int) (GameConfiguration.STANDARD_HP* GameConfiguration.MERCHANT_HP_BOOST), (int) (GameConfiguration.STANDARD_DAMAGE_SPEED*GameConfiguration.MERCHANT_DAMAGE_SPEED_BOOST), (int) (GameConfiguration.STANDARD_SPEED*GameConfiguration.MERCHANT_SPEED_BOOST),(int) (GameConfiguration.STANDARD_INVENTORY_SIZE*GameConfiguration.MERCHANT_INVENTORY_SIZE_BOOST));
    }

    /**
     * Upgrades the merchant's maximum and current HP using an upgrade point.
     * The HP increase is based on the default upgrade value and the merchant-specific HP boost.
     */
    public void upgradeHp() {
        useUpgradePoint();
        this.setMaxHp((int) (this.getMaxHp() + (GameConfiguration.UPGRADE_DEFAULT_HP * GameConfiguration.MERCHANT_HP_BOOST)));
        this.setCurrentHp((int) (this.getCurrentHp() + (GameConfiguration.UPGRADE_DEFAULT_HP * GameConfiguration.MERCHANT_HP_BOOST)));
    }

    /**
     * Upgrades the merchant's damage speed using an upgrade point.
     * The increase is calculated with the default damage speed and the merchant-specific boost.
     */
    public void upgradeDamageSpeed() {
        useUpgradePoint();
        this.setDamageSpeed((int) (this.getDamageSpeed() + (GameConfiguration.UPGRADE_DEFAULT_DAMAGE_SPEED * GameConfiguration.MERCHANT_DAMAGE_SPEED_BOOST)));
    }

    /**
     * Upgrades the merchant's movement speed using an upgrade point.
     * The speed increase is based on the default value and merchant-specific speed boost.
     */
    public void upgradeSpeed() {
        useUpgradePoint();
        this.setSpeed((int) (this.getSpeed() + (Math.max(1, GameConfiguration.UPGRADE_DEFAULT_SPEED * GameConfiguration.MERCHANT_SPEED_BOOST))));
    }

    /**
     * Upgrades the merchant's inventory capacity using an upgrade point.
     * The increase depends on the default value and the merchant's inventory size boost.
     */
    public void upgradeInventorySize() {
        useUpgradePoint();
        this.getInventory().setCapacity((int) (this.getInventory().getCapacity() + (GameConfiguration.UPGRADE_DEFAULT_INVENTORY_SIZE * GameConfiguration.MERCHANT_INVENTORY_SIZE_BOOST)));
    }

    /**
     * Calculates the potential maximum HP after the next upgrade.
     * @return {@link Integer} The future maximum HP after applying the upgrade.
     */
    public int nextUpgradeHp() {
        return (int) (this.getMaxHp() + (GameConfiguration.UPGRADE_DEFAULT_HP * GameConfiguration.MERCHANT_HP_BOOST));
    }

    /**
     * Calculates the potential damage speed after the next upgrade.
     * @return {@link Integer} The future damage speed after applying the upgrade.
     */
    public int nextUpgradeDamageSpeed() {
        return (int) (this.getDamageSpeed() + (GameConfiguration.UPGRADE_DEFAULT_DAMAGE_SPEED * GameConfiguration.MERCHANT_DAMAGE_SPEED_BOOST));
    }

    /**
     * Calculates the potential movement speed after the next upgrade.
     * @return {@link Integer} The future movement speed after applying the upgrade.
     */
    public int nextUpgradeSpeed() {
        return (int) (this.getSpeed() + (Math.max(1, GameConfiguration.UPGRADE_DEFAULT_SPEED * GameConfiguration.MERCHANT_SPEED_BOOST)));
    }

    /**
     * Calculates the potential inventory capacity after the next upgrade.
     * @return {@link Integer} The future inventory size after applying the upgrade.
     */
    public int nextUpgradeInventorySize() {
        return (int) (this.getInventory().getCapacity() + (GameConfiguration.UPGRADE_DEFAULT_INVENTORY_SIZE * GameConfiguration.MERCHANT_INVENTORY_SIZE_BOOST));
    }


}
