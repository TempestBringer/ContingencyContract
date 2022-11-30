package tempestissimo.club.contingencycontract.contract;

import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class PoorTools extends Contract implements Listener {
    public PoorTools(Configuration config, ContingencyContract plugin) {
        super("PoorTools", config, plugin);
    }

    @EventHandler
    public void onUseTool(PlayerItemDamageEvent e){
        if (this.selectedIndex<0){
            return;
        }
        Double ratio = this.levelColumnZero.get(this.selectedIndex);
        e.setDamage((int) (e.getDamage()*(1+ratio/100)));

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
