package tempestissimo.club.contingencycontract.utils;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.configuration.Configuration;
import tempestissimo.club.contingencycontract.ContingencyContract;

import static org.bukkit.Bukkit.getServer;
import static org.bukkit.Bukkit.getWorldContainer;


public class WorldCreate {
    public ContingencyContract plugin;
    public Configuration config;

    public String createWorldGroup(){
        long seed = (long) (Math.random()*1E11);
//        String worldName = String.valueOf(1E13-seed).substring(0,9);
        String worldName = "backup";
        World worldWorld = createWorld(worldName, seed, World.Environment.NORMAL, WorldType.NORMAL);
        World worldNether = createWorld(worldName.concat("_nether"), seed, World.Environment.NETHER, WorldType.NORMAL);
        World worldTheEnd = createWorld(worldName.concat("_the_end"), seed, World.Environment.THE_END, WorldType.NORMAL);
        getServer().
        return worldName;
    }

    public void unloadWorldGroup(){
        String worldName = "backup";
        unloadWorld(worldName);
        unloadWorld(worldName.concat("_nether"));
        unloadWorld(worldName.concat("_the_end"));
    }

    public World createWorld(String worldName, Long seed, World.Environment env, WorldType worldType){
        if (getServer().getWorld(worldName)==null){
            WorldCreator creator = new WorldCreator(worldName);
            creator.seed(seed);
            creator.environment(env);
            creator.type(worldType);
            getServer().createWorld(new WorldCreator(worldName));
        }
        return getServer().getWorld(worldName);
    }

    public World createWorld(String worldName){
        if (getServer().getWorld(worldName)==null){
            WorldCreator creator = new WorldCreator(worldName);
            getServer().createWorld(new WorldCreator(worldName));
        }
        return getServer().getWorld(worldName);
    }

    public void unloadWorld(String worldName){

        World world = getServer().getWorld(worldName);
        if (world==null){
            return;
        }
        getServer().unloadWorld(world,true);
    }

    public WorldCreate(ContingencyContract plugin, Configuration config) {
        this.plugin = plugin;
        this.config = config;
    }
}

