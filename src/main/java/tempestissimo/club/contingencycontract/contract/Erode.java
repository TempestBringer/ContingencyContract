package tempestissimo.club.contingencycontract.contract;

import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

import static org.bukkit.Bukkit.getServer;

public class Erode extends Contract implements Listener {
    public Erode(Configuration config, ContingencyContract plugin) {
        super("Erode", config, plugin);
    }

    /**
     * Decrease the player's HP upper limit when login.
     * @param e
     */
    @EventHandler
    public void onEnemyCauseDamage(PlayerJoinEvent e){
        if (selectedIndex>=0){
            Double ratio = this.levelColumnZero.get(this.selectedIndex);
            e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20*(1-ratio/100));
        }else{
            e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
        }
    }

    /**
     * Decrease the player's HP upper limit when start the game.
     */
    @Override
    public void start() {
        if (selectedIndex>=0){
            Double ratio = this.levelColumnZero.get(this.selectedIndex);
            for(Player player:getServer().getOnlinePlayers()){
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20*(1-ratio/100));
            }
        }else{
            for(Player player:getServer().getOnlinePlayers()){
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
            }
        }
    }

    @Override
    public void stop() {

    }
}
