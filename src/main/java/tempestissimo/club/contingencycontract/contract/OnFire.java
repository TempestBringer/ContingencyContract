package tempestissimo.club.contingencycontract.contract;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

import java.util.ArrayList;

public class OnFire extends Contract implements Listener {
    public OnFire(Configuration config, ContingencyContract plugin) {
        super("OnFire", config, plugin);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        if (!plugin.ctrl.gameIsOn){
            return;
        }
        if (this.selectedIndex<0)
            return;
        Boolean flag = false;
        //near fire test
        Integer radius = this.levelColumnZero.get(this.selectedIndex).intValue();
        ArrayList<Block> nearByBlocks = plugin.worldManage.getNearByBlocks(e.getPlayer(), radius);
        for (Block block:nearByBlocks){
            if (block.getType().equals(Material.LAVA)||block.getType().equals(Material.FIRE)||block.getType().equals(Material.LAVA_CAULDRON)){
                flag = true;
                break;
            }
        }
        if (flag){
            e.getPlayer().getWorld().getBlockAt(e.getPlayer().getLocation()).setType(Material.FIRE);
        }
        for (int i=0;i<36;i++){
            if (e.getPlayer().getInventory().getItem(i)!=null && e.getPlayer().getInventory().getItem(i).getType().equals(Material.LAVA_BUCKET)){
                flag = true;
                break;
            }
        }
        if (flag){
            e.getPlayer().getWorld().getBlockAt(e.getPlayer().getLocation()).setType(Material.FIRE);
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
