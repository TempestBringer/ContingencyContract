package tempestissimo.club.contingencycontract;

import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;
import tempestissimo.club.contingencycontract.contract.*;
import tempestissimo.club.contingencycontract.utils.Commands;
import tempestissimo.club.contingencycontract.utils.Services;

import java.util.ArrayList;

public final class ContingencyContract extends JavaPlugin {
    public Configuration config;
    public ContingencyContract plugin;
    public ArrayList<Contract> contracts = new ArrayList<>();
    public Services service;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.plugin = this;
        this.saveDefaultConfig();
        this.config = this.getConfig();
        //
        this.loadContracts();
        getServer().getPluginCommand("cc").setExecutor(new Commands(this.plugin,this.config));
        this.service = new Services(plugin,config);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Load contracts from config.
     */
    public void loadContracts(){
        contracts.add(new Erode(config,plugin));
        contracts.add(new HighValueTarget(config,plugin));
        contracts.add(new Stimulation(config,plugin));
        contracts.add(new CreeperFusion(config,plugin));
        contracts.add(new SharpArrow(config,plugin));
        contracts.add(new MechanicalDisaster(config,plugin));
        contracts.add(new Silence(config,plugin));
        contracts.add(new ShareDamage(config,plugin));
        contracts.add(new FireObsessed(config,plugin));
        contracts.add(new CovertOperation(config,plugin));
        contracts.add(new ThickMob(config,plugin));
        contracts.add(new Burden(config,plugin));
        getServer().getPluginManager().registerEvents(contracts.get(0),this);
        getServer().getPluginManager().registerEvents(contracts.get(1),this);
        getServer().getPluginManager().registerEvents(contracts.get(2),this);
        getServer().getPluginManager().registerEvents(contracts.get(3),this);
        getServer().getPluginManager().registerEvents(contracts.get(4),this);
        getServer().getPluginManager().registerEvents(contracts.get(5),this);
        getServer().getPluginManager().registerEvents(contracts.get(6),this);
        getServer().getPluginManager().registerEvents(contracts.get(7),this);
        getServer().getPluginManager().registerEvents(contracts.get(8),this);
        getServer().getPluginManager().registerEvents(contracts.get(9),this);
        getServer().getPluginManager().registerEvents(contracts.get(10),this);
        getServer().getPluginManager().registerEvents(contracts.get(11),this);
    }

}
