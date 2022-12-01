package tempestissimo.club.contingencycontract.contract;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class Bubble extends Contract implements Listener {
    public Bubble(Configuration config, ContingencyContract plugin) {
        super("Bubble", config, plugin);
    }

    @EventHandler
    public void onItemPickUp(EntityPickupItemEvent e){
        if (!plugin.ctrl.gameIsOn){
            return;
        }
        if(this.selectedIndex<0){
            return;
        }
        Double upLimit = 5*60*20*(1-this.levelColumnZero.get(this.selectedIndex)/100);
        Integer ticksLived = e.getItem().getTicksLived();
        if (ticksLived>upLimit){
            e.setCancelled(true);
            e.getItem().remove();
            if(e.getEntity().getType().equals(EntityType.PLAYER)){
                plugin.service.pickupAPastBubble((Player)e.getEntity(),e.getItem().getItemStack(), (int) ((ticksLived-upLimit)/20));
            }
        }


    }


    @EventHandler
    public void onInvPickUpItem(InventoryPickupItemEvent e){
        if (!plugin.ctrl.gameIsOn){
            return;
        }
        if(this.selectedIndex<0){
            return;
        }
        Double upLimit = 5*60*20*(1-this.levelColumnZero.get(this.selectedIndex)/100);
        Integer ticksLived = e.getItem().getTicksLived();
        if (ticksLived>upLimit){
            e.setCancelled(true);
            e.getItem().remove();
        }


    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
