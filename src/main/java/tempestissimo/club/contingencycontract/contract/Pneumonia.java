package tempestissimo.club.contingencycontract.contract;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class Pneumonia extends Contract implements Listener {

    public Pneumonia(Configuration config, ContingencyContract plugin) {
        super("Pneumonia", config, plugin);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        if (!plugin.ctrl.gameIsOn){
            return;
        }
        Player player = e.getPlayer();

        if (this.selectedIndex<0){
            player.setMaximumAir(300);
        }else{
            Double ratio = this.levelColumnZero.get(this.selectedIndex)/100;
            int maxAir = (int) (300.0*(1.0-ratio));
            player.setMaximumAir(maxAir);
            player.setRemainingAir(maxAir);
        }


    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
