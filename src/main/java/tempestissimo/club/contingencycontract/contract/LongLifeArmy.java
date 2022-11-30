package tempestissimo.club.contingencycontract.contract;

import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class LongLifeArmy extends Contract implements Listener {
    public LongLifeArmy(Configuration config, ContingencyContract plugin) {
        super("LongLifeArmy", config, plugin);
    }

    @EventHandler
    public void onZombieSpawn(CreatureSpawnEvent e){
        if (this.selectedIndex<0)
            return;
        if(e.getEntity().getType().equals(EntityType.ZOMBIE)){
            Zombie zombie = (Zombie)e.getEntity();
            zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()*(1+this.levelColumnZero.get(this.selectedIndex)/100));
            zombie.setHealth(zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
            if(e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)){
                for (int i = 0; i < this.levelColumnOne.get(this.selectedIndex); i++) {
                    e.getLocation().getWorld().spawnEntity(e.getLocation(),EntityType.ZOMBIE,true);
                }
            }

        }
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
