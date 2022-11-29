package tempestissimo.club.contingencycontract.contract;

import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class MechanicalDisaster extends Contract implements Listener {
    public MechanicalDisaster(Configuration config, ContingencyContract plugin) {
        super("MechanicalDisaster", config, plugin);
    }

    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent e){
        if (this.selectedIndex>=0){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent e){
        if (this.selectedIndex>=0){
            e.setCancelled(true);
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
