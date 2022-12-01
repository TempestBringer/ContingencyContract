package tempestissimo.club.contingencycontract.contract;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Piglin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

import java.util.List;

public class ZombiePiglinAnger extends Contract implements Listener {
    public ZombiePiglinAnger(Configuration config, ContingencyContract plugin) {
        super("ZombiePiglinAnger", config, plugin);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        if (!plugin.ctrl.gameIsOn){
            return;
        }
        if (this.selectedIndex<0){
            return;
        }
        Double radius = this.levelColumnZero.get(this.selectedIndex);
        List<Entity> nearbyEntities = e.getPlayer().getNearbyEntities(radius, radius, radius);
        for (Entity entity:nearbyEntities){
            if (entity.getType().equals(EntityType.ZOMBIFIED_PIGLIN)){
                PigZombie zombiePig = (PigZombie) entity;
//                zombiePig.damage(0.0, e.getPlayer());
                zombiePig.setAnger(65535);
                zombiePig.setAngry(true);
            }
//            else if (entity.getType().equals(EntityType.PIGLIN)){
//                Piglin piglin = (Piglin) entity;
//                piglin.damage(0.0, e.getPlayer());
//            }
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
