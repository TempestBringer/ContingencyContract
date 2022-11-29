package tempestissimo.club.contingencycontract.contract;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class FireObsessed extends Contract implements Listener {
    public FireObsessed(Configuration config, ContingencyContract plugin) {
        super("FireObsessed", config, plugin);
    }

    @EventHandler
    public void firedByBlock(EntityCombustByBlockEvent e){
        if (this.selectedIndex<0){
            return;
        }
        if(e.getEntity().getType().equals(EntityType.PLAYER)){
            Integer duration = e.getDuration();
            Double ratio = this.levelColumnZero.get(this.selectedIndex)/100;
            e.setDuration((int) (duration*(1+ratio)));
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
