package tempestissimo.club.contingencycontract.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import tempestissimo.club.contingencycontract.ContingencyContract;

import static org.bukkit.Bukkit.getServer;

public class WorldLocation {
    public ContingencyContract plugin;
    public Configuration config;

    public Location getLobbySpawnPoint(){
        return getWorldSpawnPoint("Position.PlayerJoin.");
    }

    public Location getOverWorldSpawnPoint(){
        return getWorldSpawnPoint("Position.OverWorld.");
    }

    public Location getNetherSpawnPoint(){
        return getWorldSpawnPoint("Position.Nether.");
    }

    public Location getEndSpawnPoint(){
        return getWorldSpawnPoint("Position.End.");
    }

    public Location getWorldSpawnPoint(String path){
        String worldName = config.getString(path.concat("world"));
        World world = getServer().getWorld(worldName);
        if (world!=null){
            return world.getSpawnLocation();
        }
        return null;
    }

    public WorldLocation(ContingencyContract plugin, Configuration config) {
        this.plugin = plugin;
        this.config = config;
    }
}
