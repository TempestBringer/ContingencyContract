package tempestissimo.club.contingencycontract.contract;

import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class CommonFix implements Listener {
    public ContingencyContract plugin;
    public Configuration config;

    @EventHandler
    public void onWaterStuckDragon(PlayerBucketEmptyEvent e){
        if (!plugin.ctrl.gameIsOn){
            return;
        }
        if (e.getPlayer().getWorld().getName().contains("end")){

            int x = e.getBlock().getX();
            int y = e.getBlock().getY();
            int z = e.getBlock().getZ();
            if (y>80&&Math.abs(x)+Math.abs(z)<10){
                e.setCancelled(true);
            }
        }
    }

    public CommonFix(ContingencyContract plugin, Configuration config) {
        this.plugin = plugin;
        this.config = config;
    }
}
