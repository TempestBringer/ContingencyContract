package tempestissimo.club.contingencycontract.contract;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.Listener;
import tempestissimo.club.contingencycontract.ContingencyContract;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class ForeverNight extends Contract implements Listener {
    public ForeverNight(Configuration config, ContingencyContract plugin) {
        super("ForeverNight", config, plugin);
    }

    @Override
    public void start() {
        if (this.selectedIndex<0)
            return;
        for (World world:getServer().getWorlds()){
            if (world.getName().contains("world")){
                world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE,false);
                world.setTime(18000);
                getLogger().info(world.getName().concat(" time has been set to 18000"));
            }

        }
    }

    @Override
    public void stop() {
        for (World world:getServer().getWorlds()){
            if (world.getName().contains("world")) {
                world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
            }
        }
    }
}
