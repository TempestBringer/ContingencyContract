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
        if (!plugin.ctrl.gameIsOn){
            return;
        }
        if (this.selectedIndex<0){
            return;
        }
        EntityType type = e.getEntity().getType();
        Double ratio = this.levelColumnZero.get(this.selectedIndex)/100.0;
        if (Math.random()<ratio){
            if (this.selectedIndex<2){
                if (type.equals(EntityType.SALMON)||type.equals(EntityType.SQUID)||
                        type.equals(EntityType.GLOW_SQUID)||type.equals(EntityType.COD)||
                        type.equals(EntityType.TADPOLE)||type.equals(EntityType.PUFFERFISH)||
                        type.equals(EntityType.AXOLOTL)||type.equals(EntityType.FROG)||
                        type.equals(EntityType.DOLPHIN)){
                    e.getLocation().getWorld().spawnEntity(e.getLocation(), EntityType.GUARDIAN);
                    e.setCancelled(true);
                }
            }else{
                if (type.equals(EntityType.SALMON)||type.equals(EntityType.SQUID)||
                        type.equals(EntityType.GLOW_SQUID)||type.equals(EntityType.COD)||
                        type.equals(EntityType.TADPOLE)||type.equals(EntityType.PUFFERFISH)||
                        type.equals(EntityType.AXOLOTL)||type.equals(EntityType.FROG)||
                        type.equals(EntityType.DOLPHIN)||type.equals(EntityType.ALLAY)||
                        type.equals(EntityType.BAT)||type.equals(EntityType.CAT)||
                        type.equals(EntityType.COW)||type.equals(EntityType.CHICKEN)||
                        type.equals(EntityType.LLAMA)||type.equals(EntityType.FOX)||
                        type.equals(EntityType.GOAT)||type.equals(EntityType.HORSE)||
                        type.equals(EntityType.MULE)||type.equals(EntityType.PIG)||
                        type.equals(EntityType.PANDA)||type.equals(EntityType.PARROT)||
                        type.equals(EntityType.POLAR_BEAR)||type.equals(EntityType.RABBIT)||
                        type.equals(EntityType.SILVERFISH)||type.equals(EntityType.SHEEP)||
                        type.equals(EntityType.SNOWMAN)||type.equals(EntityType.TURTLE)||
                        type.equals(EntityType.TRADER_LLAMA)||type.equals(EntityType.WOLF)||
                        type.equals(EntityType.WANDERING_TRADER)){
                    e.getLocation().getWorld().spawnEntity(e.getLocation(), EntityType.GUARDIAN);
                    e.setCancelled(true);
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
