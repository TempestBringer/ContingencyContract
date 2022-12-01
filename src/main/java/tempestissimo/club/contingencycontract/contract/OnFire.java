package tempestissimo.club.contingencycontract.contract;

import org.bukkit.configuration.Configuration;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class OnFire extends Contract implements Listener {
    public OnFire(String name, Configuration config, ContingencyContract plugin) {
        super(name, config, plugin);
    }

    public void onPlayerMove(PlayerMoveEvent e){
        if (!plugin.ctrl.gameIsOn){
            return;
        }
        if (this.selectedIndex<0)
            return;
        Double radius = this.levelColumnZero.get(this.selectedIndex);

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
