package tempestissimo.club.contingencycontract.contract;

import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.Configuration;

import org.bukkit.entity.Blaze;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class BurnOut extends Contract implements Listener {
    public BurnOut(Configuration config, ContingencyContract plugin) {
        super("BurnOut", config, plugin);
    }

    @EventHandler
    public void onBlazeSpawn(CreatureSpawnEvent e){
        if (this.selectedIndex<0)
            return;
        if(e.getEntity().getType().equals(EntityType.BLAZE)){
            Blaze blaze = (Blaze)e.getEntity();
            blaze.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(blaze.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()*(1+this.levelColumnZero.get(this.selectedIndex)/100));
            blaze.setHealth(blaze.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
            blaze.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(blaze.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue()*(1+this.levelColumnOne.get(this.selectedIndex)/100));
            blaze.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(this.levelColumnTwo.get(this.selectedIndex));
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
