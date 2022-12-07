package tempestissimo.club.contingencycontract.contract;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

import java.util.List;

public class ZombiePiglinAnger extends Contract implements Listener {
    public ZombiePiglinAnger(Configuration config, ContingencyContract plugin) {
        super("ZombiePiglinAnger", config, plugin);
    }

    @EventHandler
    public void onPlayerLoadChunk(ChunkLoadEvent e){
        if (!plugin.ctrl.gameIsOn){
            return;
        }
        if (this.selectedIndex<0){
            return;
        }
        if (!e.getWorld().getName().contains("nether")){
            return;
        }
        for (Entity entity:e.getChunk().getEntities()){
            if (entity.getType().equals(EntityType.ZOMBIFIED_PIGLIN)){
                PigZombie zombiePig = (PigZombie) entity;
                List<Entity> nearbyEntities = zombiePig.getNearbyEntities(32, 32, 32);
                for (Entity p:nearbyEntities){
                    if (p.getType().equals(EntityType.PLAYER)){
                        zombiePig.damage(1E-7,p);
                    }
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
