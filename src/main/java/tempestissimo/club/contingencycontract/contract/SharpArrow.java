package tempestissimo.club.contingencycontract.contract;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class SharpArrow extends Contract implements Listener {
    public SharpArrow(Configuration config, ContingencyContract plugin) {
        super("SharpArrow", config, plugin);
    }

    @EventHandler
    public void onShootArrow(EntityShootBowEvent e){
        if (!plugin.ctrl.gameIsOn){
            return;
        }
        Entity shooter = e.getEntity();
        if (this.selectedIndex<0)
            return;
        if (shooter.getType().equals(EntityType.SKELETON) || shooter.getType().equals(EntityType.PILLAGER)){
            e.getProjectile().setVelocity(e.getProjectile().getVelocity().multiply(1+this.levelColumnZero.get(this.selectedIndex)/100));
        }
    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
