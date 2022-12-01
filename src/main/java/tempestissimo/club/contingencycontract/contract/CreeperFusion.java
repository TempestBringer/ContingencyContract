package tempestissimo.club.contingencycontract.contract;

import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class CreeperFusion extends Contract implements Listener {


    public CreeperFusion(Configuration config, ContingencyContract plugin) {
        super("CreeperFusion", config, plugin);
    }

    @EventHandler
    public void onCreeperSpawn(CreatureSpawnEvent e){
        if (!plugin.ctrl.gameIsOn){
            return;
        }
        if (this.selectedIndex<0)
            return;
        if(e.getEntity().getType().equals(EntityType.CREEPER)){
            Creeper creeper = (Creeper)e.getEntity();
            creeper.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(creeper.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()*(1+this.levelColumnZero.get(this.selectedIndex)/100));
            creeper.setHealth(creeper.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
            creeper.setMaxFuseTicks((int) (creeper.getMaxFuseTicks()*(1-this.levelColumnOne.get(this.selectedIndex)/100)));
            creeper.setExplosionRadius((int) (creeper.getExplosionRadius()*(1+this.levelColumnTwo.get(this.selectedIndex)/100)));
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
