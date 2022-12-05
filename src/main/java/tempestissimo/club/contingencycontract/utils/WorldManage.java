package tempestissimo.club.contingencycontract.utils;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import tempestissimo.club.contingencycontract.ContingencyContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.bukkit.Bukkit.*;

public class WorldManage {

    public ContingencyContract plugin;
    public Configuration config;


    public HashMap<String, World> worldHashMap;


    public void getWorldBriefIntroduction(){

    }

    public void generateBackUpWorlds(){

    }

    public ArrayList<Block> getNearByBlocks(Player player, Integer distance){
        ArrayList<Block> blockList = new ArrayList<>();
        Location base = player.getLocation();
        Integer fromX = base.getBlockX() - distance;
        Integer fromY = base.getBlockY() - distance;
        Integer fromZ = base.getBlockZ() - distance;
        Integer toX = base.getBlockX() + distance;
        Integer toY = base.getBlockY() + distance;
        Integer toZ = base.getBlockZ() + distance;
        for (int i = fromX;i<=toX;i++){
            for (int j = fromY;j<=toY;j++){
                for (int k = fromZ;k<=toZ;k++){
                    Block block = player.getWorld().getBlockAt(i, j, k);
                    blockList.add(block);
                }
            }
        }
        return blockList;
    }

    public void loadAllWorld() {
        List<String> worldList = config.getStringList("WorldGenerate.LoadWorld");
        for (String world : worldList) {
            if (getServer().getWorld(world) != null) {
                getLogger().info("World '".concat(world).concat("' loading"));
                worldHashMap.put(world, getServer().getWorld(world));
            } else {
                getLogger().info("World '".concat(world).concat("' not exist, creating"));
                generateWorld(world, seedGenerator());
                worldHashMap.put(world, getServer().getWorld(world));
            }
        }
    }

    public void getWorldList(){
        List<World> worlds = getServer().getWorlds();
        getLogger().info("Server Has worlds:");
        for (World world:worlds){
            getLogger().info(" - ".concat(world.getName()));
        }
    }

    public long seedGenerator(){
        Double seed = 1E17 - Math.random()*1E15;
        return seed.longValue();
    }

    public void removeWorld(String worldName){
        for (Chunk chunk:getWorld(worldName).getLoadedChunks()){
            chunk.unload(true);
        }
        getServer().unloadWorld(worldName,true);
    }

    public void teleportWorld(String worldName, Player player){
        player.teleport(getWorld(worldName).getSpawnLocation());
    }

    public void generateWorld(String worldName, long seed, World.Environment env, WorldType worldType){
        WorldCreator creator = new WorldCreator(worldName);
        creator.seed(seed);
        creator.environment(env);
        creator.type(worldType);
        ChunkGenerator chunkGenerator = creator.generator();
        getServer().createWorld(creator);
    }

    public void generateWorld(String worldName, long seed){
        WorldCreator creator = new WorldCreator(worldName);
        creator.seed(seed);
        getServer().createWorld(creator);
    }

    public void generateWorld(String worldName){
        WorldCreator creator = new WorldCreator(worldName);
        creator.environment(World.Environment.NORMAL);
        getServer().createWorld(creator);
    }

    public void generateWorld(){
        WorldCreator creator = new WorldCreator("world");
        creator.environment(World.Environment.NORMAL);
        getServer().createWorld(creator);
    }

    public void generateNether(String worldName){
        WorldCreator creator = new WorldCreator(worldName);
        creator.environment(World.Environment.NETHER);
        getServer().createWorld(creator);
    }
    public void generateNether(){
        WorldCreator creator = new WorldCreator("world_nether");
        creator.environment(World.Environment.NETHER);
        getServer().createWorld(creator);
    }

    public void generateTheEnd(String worldName){
        WorldCreator creator = new WorldCreator(worldName);
        creator.environment(World.Environment.THE_END);
        getServer().createWorld(creator);
    }
    public void generateTheEnd(){
        WorldCreator creator = new WorldCreator("world_the_end");
        creator.environment(World.Environment.THE_END);
        getServer().createWorld(creator);
    }

    public WorldManage(ContingencyContract plugin, Configuration config) {
        this.plugin = plugin;
        this.config = config;
        this.worldHashMap = new HashMap<>();
        this.loadAllWorld();
    }
}
