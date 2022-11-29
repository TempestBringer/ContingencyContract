package tempestissimo.club.contingencycontract.contract;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class Stimulation extends Contract implements Listener {

    public Stimulation(Configuration config, ContingencyContract plugin) {
        super("Stimulation", config, plugin);
    }

    /**
     * Increase the Damage if caused by enemy.
     * Not Include:
     *  - Player
     *  - Tamed wolf
     *  - Player Created IronGolem
     * @param e
     */
    @EventHandler
    public void onEnemyCauseDamage(EntityDamageByEntityEvent e){
        if (selectedIndex>=0){
            Entity damager = e.getDamager();
            Boolean flag=false;
            if (damager.getType().equals(EntityType.PLAYER)){
                flag=true;
            }
            if (damager.getType().equals(EntityType.WOLF)){
                Wolf wolf = (Wolf) damager;
                if (wolf.isTamed()){
                    flag=true;
                }
            }
            if (damager.getType().equals(EntityType.IRON_GOLEM)){
                IronGolem ironGolem = (IronGolem) damager;
                if (ironGolem.isPlayerCreated()){
                    flag=true;
                }
            }
            if (!flag){
                Double ratio = this.levelColumnZero.get(this.selectedIndex);
                e.setDamage(e.getDamage()*(1+ratio/100));
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
