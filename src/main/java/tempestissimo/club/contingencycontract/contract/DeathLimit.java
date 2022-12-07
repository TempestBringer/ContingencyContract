package tempestissimo.club.contingencycontract.contract;

import org.bukkit.configuration.Configuration;
import org.bukkit.event.Listener;
import tempestissimo.club.contingencycontract.ContingencyContract;

import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class DeathLimit extends Contract implements Listener {
    public DeathLimit(Configuration config, ContingencyContract plugin) {
        super("DeathLimit", config, plugin);
    }

    @Override
    public void start() {
        if (this.selectedIndex<0){
            plugin.ctrl.maxReDeploy = getServer().getOnlinePlayers().size() * 60;
        }else{
            Integer perPlayerLimit = this.levelColumnZero.get(this.selectedIndex).intValue();
            plugin.ctrl.maxReDeploy = getServer().getOnlinePlayers().size() * perPlayerLimit;
        }
    }

    @Override
    public void stop() {

    }
}
