package tempestissimo.club.contingencycontract.contract;

import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class FromEnd extends Contract implements Listener {
    public FromEnd(Configuration config, ContingencyContract plugin) {
        super("FromEnd", config, plugin);
    }

    @EventHandler
    public void onEnderManSpawn(CreatureSpawnEvent e){
        if (this.selectedIndex<0)
            return;
        if(e.getEntity().getType().equals(EntityType.ENDERMAN)){
            Enderman enderman = (Enderman)e.getEntity();
            enderman.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(enderman.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()*(1+this.levelColumnZero.get(this.selectedIndex)/100));
            enderman.setHealth(enderman.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
            enderman.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(enderman.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue()*(1+this.levelColumnOne.get(this.selectedIndex)/100));
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
