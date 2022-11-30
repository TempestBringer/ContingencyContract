package tempestissimo.club.contingencycontract.contract;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class Eutrophic extends Contract implements Listener {
    public Eutrophic(Configuration config, ContingencyContract plugin) {
        super("Eutrophic", config, plugin);
    }

    @EventHandler
    public void onSpawnWaterCreature(CreatureSpawnEvent e){
        if (this.selectedIndex<0){
            return;
        }
        EntityType type = e.getEntity().getType();
        Double ratio = this.levelColumnZero.get(this.selectedIndex)/100;
        if (type.equals(EntityType.SALMON)||type.equals(EntityType.SQUID)||
                type.equals(EntityType.GLOW_SQUID)||type.equals(EntityType.COD)||
                type.equals(EntityType.TADPOLE)||type.equals(EntityType.PUFFERFISH)||
                type.equals(EntityType.AXOLOTL)||type.equals(EntityType.FROG)||
                type.equals(EntityType.DOLPHIN)){
            if (this.selectedIndex<2){
                if (Math.random()<ratio){
                    e.setCancelled(true);
                    e.getLocation().getWorld().spawnEntity(e.getLocation(), EntityType.GUARDIAN);
                }
            }else{
                if (Math.random()<ratio){
                    e.setCancelled(true);
                    e.getLocation().getWorld().spawnEntity(e.getLocation(), EntityType.ELDER_GUARDIAN);
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
