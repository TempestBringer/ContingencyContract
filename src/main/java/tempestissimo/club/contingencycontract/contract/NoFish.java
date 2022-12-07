package tempestissimo.club.contingencycontract.contract;

import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class NoFish extends Contract implements Listener {
    public NoFish(Configuration config, ContingencyContract plugin) {
        super("NoFish", config, plugin);
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent e){
        if (!plugin.ctrl.gameIsOn){
            return;
        }
        if (this.selectedIndex<0)
            return;
        if (e.getCaught()!=null){
            Item caught = (Item) e.getCaught();
            caught.setItemStack(new ItemStack(Material.AIR,1));
        }

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
