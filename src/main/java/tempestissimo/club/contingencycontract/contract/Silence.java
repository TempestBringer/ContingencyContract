package tempestissimo.club.contingencycontract.contract;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

import java.util.Set;

import static org.bukkit.Bukkit.getServer;

public class Silence extends Contract implements Listener {
    public Silence(Configuration config, ContingencyContract plugin) {
        super("Silence", config, plugin);
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e){
        if (this.selectedIndex<0)
            return;
        e.setCancelled(true);
        Player speaker = e.getPlayer();
        Set<Player> recipients = e.getRecipients();
        for (Player player:recipients){
            Double distance = player.getLocation().toVector().distance(speaker.getLocation().toVector());
            if (distance<this.levelColumnZero.get(this.selectedIndex)){
                player.sendRawMessage("<".concat(speaker.getName()).concat("> ".concat(e.getMessage())));
            }
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
