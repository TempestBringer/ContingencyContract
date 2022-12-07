package tempestissimo.club.contingencycontract.contract;

import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class Octane extends Contract implements Listener {
    public Octane(Configuration config, ContingencyContract plugin) {
        super("Octane", config, plugin);
    }

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent e){
        if (!plugin.ctrl.gameIsOn){
            return;
        }
        EntityType type = e.getEntity().getType();
        if (this.selectedIndex<0)
            return;
        Double ratio = this.levelColumnZero.get(this.selectedIndex)/100;
        // Always enemy
        if (type.equals(EntityType.ZOMBIE)||type.equals(EntityType.ZOMBIFIED_PIGLIN)||
                type.equals(EntityType.ZOGLIN)||type.equals(EntityType.ZOMBIE_VILLAGER)||
                type.equals(EntityType.BLAZE)||type.equals(EntityType.CAVE_SPIDER)||
                type.equals(EntityType.CREEPER)||type.equals(EntityType.DROWNED)||
                type.equals(EntityType.ELDER_GUARDIAN)||type.equals(EntityType.ENDERMAN)||
                type.equals(EntityType.ENDERMITE)||type.equals(EntityType.ENDER_DRAGON)||
                type.equals(EntityType.EVOKER)||type.equals(EntityType.GHAST)||
                type.equals(EntityType.GUARDIAN)||type.equals(EntityType.HOGLIN)||
                type.equals(EntityType.HUSK)||type.equals(EntityType.ILLUSIONER)||
                type.equals(EntityType.MAGMA_CUBE)||type.equals(EntityType.PHANTOM)||
                type.equals(EntityType.PIGLIN)||type.equals(EntityType.PIGLIN_BRUTE)||
                type.equals(EntityType.PILLAGER)||type.equals(EntityType.RAVAGER)||
                type.equals(EntityType.SHULKER)||type.equals(EntityType.SKELETON)||
                type.equals(EntityType.SILVERFISH)||type.equals(EntityType.SLIME)||
                type.equals(EntityType.SPIDER)||type.equals(EntityType.STRAY)||
                type.equals(EntityType.STRIDER)||type.equals(EntityType.VEX)||
                type.equals(EntityType.VINDICATOR)||type.equals(EntityType.WARDEN)||
                type.equals(EntityType.WITCH)||type.equals(EntityType.WITHER)||
                type.equals(EntityType.WITHER_SKELETON)){
            Double value = e.getEntity().getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue()*(1.0+ratio);
            e.getEntity().getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(value);
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
