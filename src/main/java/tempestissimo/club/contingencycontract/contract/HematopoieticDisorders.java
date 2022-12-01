package tempestissimo.club.contingencycontract.contract;

import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class HematopoieticDisorders extends Contract implements Listener {
    public HematopoieticDisorders(Configuration config, ContingencyContract plugin) {
        super("HematopoieticDisorders", config, plugin);
    }

    @EventHandler
    public void onPlayerRegen(EntityRegainHealthEvent e){
        if (!plugin.ctrl.gameIsOn){
            return;
        }
        if (this.selectedIndex<0){
            return;
        }
        Double ratio = this.levelColumnZero.get(this.selectedIndex)/100;
        Double amount = e.getAmount()*(1-ratio);
        e.setAmount(amount);

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
