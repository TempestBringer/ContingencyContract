package tempestissimo.club.contingencycontract.utils;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Honor implements Listener {

    @EventHandler
    public void onPlayerClickHead(PlayerInteractEvent e){
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){

        }

    }
}
