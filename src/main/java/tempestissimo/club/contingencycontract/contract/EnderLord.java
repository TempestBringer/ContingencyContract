package tempestissimo.club.contingencycontract.contract;

import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class EnderLord extends Contract implements Listener {
    public EnderLord(Configuration config, ContingencyContract plugin) {
        super("EnderLord", config, plugin);
    }

    @EventHandler
    public void onEnderDragonSpawn(CreatureSpawnEvent e){
        if (!plugin.ctrl.gameIsOn){
            return;
        }
        if (this.selectedIndex<0)
            return;
        if(e.getEntity().getType().equals(EntityType.ENDER_DRAGON)){
            EnderDragon enderDragon = (EnderDragon)e.getEntity();
            if (enderDragon.getDragonBattle()!=null){
                enderDragon.getDragonBattle().getBossBar().setColor(BarColor.RED);
                enderDragon.getDragonBattle().getBossBar().setStyle(BarStyle.SOLID);
            }

            Double health = enderDragon.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()*(1+this.levelColumnZero.get(this.selectedIndex)/100);
            enderDragon.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
            enderDragon.addPotionEffect(new PotionEffect(PotionEffectType.HEAL,200,100));
            enderDragon.setHealth(health);
            enderDragon.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(enderDragon.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue()*(1+this.levelColumnOne.get(this.selectedIndex)/100));
        }
    }

    @EventHandler
    public void onDragonSufferExplode(EntityDamageEvent e){
        if (!plugin.ctrl.gameIsOn){
            return;
        }
        if (this.selectedIndex<0)
            return;
        if (e.getEntity().getType().equals(EntityType.ENDER_DRAGON)){
            if (e.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)||e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)){
                e.setDamage(0);
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
