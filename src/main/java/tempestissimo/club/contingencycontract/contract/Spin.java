package tempestissimo.club.contingencycontract.contract;

import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class Spin extends Contract implements Listener {
    public Spin(Configuration config, ContingencyContract plugin) {
        super("Spin", config, plugin);
    }

    @EventHandler
    public void onPlayerMove(PlayerVelocityEvent e){
        if (this.selectedIndex<0){
            return;
        }
        Double ratio = this.levelColumnZero.get(this.selectedIndex);
        Double yaw = e.getPlayer().getLocation().getYaw()+(Math.random() - 0.5) * 2 * ratio;
        Double pitch = e.getPlayer().getLocation().getPitch()+(Math.random() - 0.5) * 2 * ratio;
        e.getPlayer().setRotation(yaw.floatValue(),pitch.floatValue());
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
