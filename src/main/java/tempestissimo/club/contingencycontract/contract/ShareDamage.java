package tempestissimo.club.contingencycontract.contract;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

import static org.bukkit.Bukkit.getServer;

public class ShareDamage extends Contract implements Listener {
    public ShareDamage(Configuration config, ContingencyContract plugin) {
        super("ShareDamage", config, plugin);
    }

    @EventHandler
    public void onPlayerSufferDamage(EntityDamageEvent e){
        if (!plugin.ctrl.gameIsOn){
            return;
        }
        if (this.selectedIndex<0){
            return;
        }
        if(e.getEntity().getType().equals(EntityType.PLAYER)){
            Player player = (Player) e.getEntity();
            Double damage = e.getFinalDamage();
            Double ratio = this.levelColumnZero.get(this.selectedIndex)/100;
            for (Player other:getServer().getOnlinePlayers()){
                if (!other.getName().equals(player.getName())) {
                    Double newHealth = other.getHealth() - damage * ratio;
                    if (newHealth>0){
                        other.setHealth(other.getHealth() - damage * ratio);
                    }else{
                        other.setHealth(0.0);
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
