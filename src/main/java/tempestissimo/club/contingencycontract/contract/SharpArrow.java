package tempestissimo.club.contingencycontract.contract;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class SharpEye extends Contract implements Listener {
    public SharpEye(Configuration config, ContingencyContract plugin) {
        super("SharpEye", config, plugin);
    }

    @EventHandler
    public void onShootArrow(EntityShootBowEvent e){
        Entity shooter = e.getEntity();
        if (shooter.equals(EntityType.SKELETON) || shooter.getType().equals(EntityType.PILLAGER)){
            e.getProjectile().setVelocity(e.getProjectile().getVelocity().);
        }
    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
