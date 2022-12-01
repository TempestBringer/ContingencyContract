package tempestissimo.club.contingencycontract.contract;

import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class Tasteless extends Contract implements Listener {
    public Tasteless(Configuration config, ContingencyContract plugin) {
        super("Tasteless", config, plugin);
    }

    @EventHandler
    public void onHungerDrop(FoodLevelChangeEvent e){
        if (!plugin.ctrl.gameIsOn){
            return;
        }
        if (this.selectedIndex<0){
            return;
        }
        int cur = e.getEntity().getFoodLevel();
        int next = e.getFoodLevel();
        int delta = cur-next;
        double ratio = this.levelColumnZero.get(this.selectedIndex)/100;
        if (delta>0){
            e.setFoodLevel((int) (cur-(1+ratio)*delta));
        }

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
