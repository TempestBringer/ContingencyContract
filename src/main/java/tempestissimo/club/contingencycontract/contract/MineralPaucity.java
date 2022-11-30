package tempestissimo.club.contingencycontract.contract;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class MineralPaucity extends Contract implements Listener {
    public MineralPaucity(Configuration config, ContingencyContract plugin) {
        super("MineralPaucity", config, plugin);
    }

    @EventHandler
    public void onBreakMineral(BlockBreakEvent e){
        if (this.selectedIndex<0){
            return;
        }
        Double ratio = this.levelColumnZero.get(this.selectedIndex)/100;
        Material type = e.getBlock().getType();
        if (Math.random()<ratio){
            if (type.equals(Material.COAL_ORE)||type.equals(Material.DEEPSLATE_COAL_ORE)||
                    type.equals(Material.IRON_ORE)||type.equals(Material.DEEPSLATE_IRON_ORE)||
                    type.equals(Material.COPPER_ORE)||type.equals(Material.DEEPSLATE_COPPER_ORE)||
                    type.equals(Material.GOLD_ORE)||type.equals(Material.DEEPSLATE_GOLD_ORE)||
                    type.equals(Material.REDSTONE_ORE)||type.equals(Material.DEEPSLATE_REDSTONE_ORE)||
                    type.equals(Material.EMERALD_ORE)||type.equals(Material.DEEPSLATE_EMERALD_ORE)||
                    type.equals(Material.LAPIS_ORE)||type.equals(Material.DEEPSLATE_LAPIS_ORE)||
                    type.equals(Material.DIAMOND_ORE)||type.equals(Material.DEEPSLATE_DIAMOND_ORE)||
                    type.equals(Material.NETHER_GOLD_ORE)||type.equals(Material.NETHER_QUARTZ_ORE)||
                    type.equals(Material.ANCIENT_DEBRIS)){
                e.setDropItems(false);
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
