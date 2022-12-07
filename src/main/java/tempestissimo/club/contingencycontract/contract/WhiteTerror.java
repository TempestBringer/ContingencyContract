package tempestissimo.club.contingencycontract.contract;

import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Ghast;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.projectiles.ProjectileSource;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class WhiteTerror extends Contract implements Listener {
    public WhiteTerror(Configuration config, ContingencyContract plugin) {
        super("WhiteTerror", config, plugin);
    }

    @EventHandler
    public void onGhastSpawn(CreatureSpawnEvent e){
        if (!plugin.ctrl.gameIsOn){
            return;
        }
        if (this.selectedIndex<0)
            return;
        if(e.getEntity().getType().equals(EntityType.GHAST)){
            Ghast ghast = (Ghast)e.getEntity();
            if (ghast.getAttribute(Attribute.GENERIC_MAX_HEALTH)!=null){
                ghast.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(ghast.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()*(1+this.levelColumnZero.get(this.selectedIndex)/100));
                ghast.setHealth(ghast.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
            }
            if (ghast.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)!=null){
                ghast.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(ghast.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue()*(1+this.levelColumnOne.get(this.selectedIndex)/100));
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
