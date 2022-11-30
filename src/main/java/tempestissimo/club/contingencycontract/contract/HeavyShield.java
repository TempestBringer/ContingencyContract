package tempestissimo.club.contingencycontract.contract;

import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class HeavyShield extends Contract implements Listener {
    public HeavyShield(Configuration config, ContingencyContract plugin) {
        super("HeavyShield", config, plugin);
    }

    @EventHandler
    public void onUseShield(PlayerItemDamageEvent e){
        if (this.selectedIndex<0){
            return;
        }
        if (e.getItem().getType().equals(Material.SHIELD)){
            Player player = e.getPlayer();
            player.setCooldown(Material.SHIELD, (int) (this.levelColumnZero.get(this.selectedIndex)*20));
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            ItemStack itemInOffHand = player.getInventory().getItemInOffHand();
            player.getInventory().setItemInMainHand(itemInOffHand);
            player.getInventory().setItemInOffHand(itemInMainHand);
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
