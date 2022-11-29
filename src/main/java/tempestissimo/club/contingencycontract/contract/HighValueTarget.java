package tempestissimo.club.contingencycontract.contract;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class HighValueTarget extends Contract implements Listener {

    public HighValueTarget(Configuration config, ContingencyContract plugin) {
        super("HighValueTarget", config, plugin);
    }

    /**
     * Reduce the Damage if caused by player and his friends.
     * Include:
     *  - Player
     *  - Tamed wolf
     *  - Player Created IronGolem
     * @param e
     */
    @EventHandler
    public void onFriendCauseDamage(EntityDamageByEntityEvent e){
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
            if (flag){
                Double ratio = this.levelColumnZero.get(this.selectedIndex);
                e.setDamage(e.getDamage()*(1-ratio/100));
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
