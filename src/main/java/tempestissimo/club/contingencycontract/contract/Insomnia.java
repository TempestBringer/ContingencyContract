package tempestissimo.club.contingencycontract.contract;

import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.TimeSkipEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class Insomnia extends Contract implements Listener {
    public Insomnia(Configuration config, ContingencyContract plugin) {
        super("Insomnia", config, plugin);
    }

    @EventHandler
    public void onSkipNight(TimeSkipEvent e){
        if (!plugin.ctrl.gameIsOn){
            return;
        }
        if (this.selectedIndex<0){
            return;
        }
        e.setCancelled(true);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
