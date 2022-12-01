package tempestissimo.club.contingencycontract.contract;

import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import tempestissimo.club.contingencycontract.ContingencyContract;

import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class CovertOperation extends Contract implements Listener {
    public CovertOperation(Configuration config, ContingencyContract plugin) {
        super("CovertOperation", config, plugin);
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent e){
        if (!plugin.ctrl.gameIsOn){
            return;
        }
        if (this.selectedIndex<0){
            return;
        }
        if (e.getCurrentItem()==null){
            return;
        }
        if (e.getCursor()==null){
            return;
        }
        if (e.getCurrentItem().getType().equals(Material.BARRIER)){
            e.setCancelled(true);
        }if (e.getCursor().getType().equals(Material.BARRIER)){
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void onInventoryEvent(InventoryMoveItemEvent e){
        if (!plugin.ctrl.gameIsOn){
            return;
        }
        if (this.selectedIndex<0){
            return;
        }
        if (e.getItem().getType().equals(Material.BARRIER)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent e){
        if (!plugin.ctrl.gameIsOn){
            return;
        }
        if (this.selectedIndex<0){
            return;
        }
        if (e.getItemDrop().getItemStack().getType().equals(Material.BARRIER)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e){
        if (!plugin.ctrl.gameIsOn){
            return;
        }
        if (this.selectedIndex<0){
            return;
        }
        Double ratio = this.levelColumnZero.get(this.selectedIndex)/100;
        Player player = e.getPlayer();
        PlayerInventory inventory = player.getInventory();
        for (int i = (int) (36*(1-ratio)); i < 36; i++) {
            inventory.setItem(i,new ItemStack(Material.BARRIER));
        }
    }


    /**
     * 开始时向背包装入占位物品
     */
    @Override
    public void start() {
        if (this.selectedIndex<0){
            return;
        }
        Double ratio = this.levelColumnZero.get(this.selectedIndex)/100;
        for (Player player:getServer().getOnlinePlayers()){
            PlayerInventory inventory = player.getInventory();
            for (int i = (int) (36*(1-ratio)); i < 36; i++) {
                inventory.setItem(i,new ItemStack(Material.BARRIER));
            }
        }
    }

    @Override
    public void stop() {
        if (this.selectedIndex<0){
            return;
        }
        Double ratio = this.levelColumnZero.get(this.selectedIndex)/100;
        for (Player player:getServer().getOnlinePlayers()){
            PlayerInventory inventory = player.getInventory();
            for (int i = 0; i < 36; i++) {
                inventory.setItem(i,new ItemStack(Material.AIR));
            }
        }
    }
}
