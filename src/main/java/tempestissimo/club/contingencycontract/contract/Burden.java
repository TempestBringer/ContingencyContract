package tempestissimo.club.contingencycontract.contract;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.PlayerInventory;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class Burden extends Contract implements Listener {
    public Burden(Configuration config, ContingencyContract plugin) {
        super("Burden", config, plugin);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        if (!plugin.ctrl.gameIsOn){
            return;
        }
        if (this.selectedIndex<0){
            e.getPlayer().getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1);
            return;
        }
        Double ratio = this.levelColumnZero.get(this.selectedIndex);
        PlayerInventory inventory = e.getPlayer().getInventory();
        Integer available=0;
        Integer empty=0;
        for (int i = 0; i < 36; i++) {
            if (inventory.getItem(i)==null){
                empty++;available++;
            } else if (!inventory.getItem(i).getType().equals(Material.BARRIER)){
                available++;
            }
        }
        e.getPlayer().getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1*(1-ratio*(available-empty)/available));

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
