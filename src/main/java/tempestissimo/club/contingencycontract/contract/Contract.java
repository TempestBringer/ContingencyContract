package tempestissimo.club.contingencycontract.contract;

import org.bukkit.configuration.Configuration;
import org.bukkit.event.Listener;
import tempestissimo.club.contingencycontract.ContingencyContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Contract's prototype. Extend this class to further customize contracts.
 */
public abstract class Contract implements Listener {
    /**
     * Should player see this contract or not.
     */
    public Boolean visible;

    /**
     * Level the player selected. Negative means not selected.
     */
    public Integer selectedIndex = -1;
    /**
     * How many level is added to total difficulty.
     */
    public ArrayList<Double> levelColumnZero;
    public ArrayList<Double> levelColumnOne;
    public ArrayList<Double> levelColumnTwo;
    public ArrayList<Double> levelColumnThree;
    public ArrayList<Double> levelColumnFour;
    /**
     * Internal name, in English.
     */
    public String iname;
    /**
     * This Contract's name. Translated to "localized" text.
     */
    public String name;
    /**
     * Plugin's Root Config.
     */
    public Configuration config;
    /**
     * Direct to plugin itself.
     */
    public ContingencyContract plugin;

    public Contract(String name, Configuration config, ContingencyContract plugin) {
        this.iname = name;
        this.visible = config.getBoolean("Contract.".concat(name).concat(".visible"));
        this.selectedIndex = -1;
        if (config.contains("Contract.".concat(name).concat(".level0"))){
            this.levelColumnZero = (ArrayList<Double>) config.getDoubleList("Contract.".concat(name).concat(".level0"));
        }else{
            this.levelColumnZero = new ArrayList<>();
        }
        if (config.contains("Contract.".concat(name).concat(".level1"))){
            this.levelColumnOne = (ArrayList<Double>) config.getDoubleList("Contract.".concat(name).concat(".level1"));
        }else{
            this.levelColumnOne = new ArrayList<>();
        }
        if (config.contains("Contract.".concat(name).concat(".level2"))){
            this.levelColumnTwo = (ArrayList<Double>) config.getDoubleList("Contract.".concat(name).concat(".level2"));
        }else{
            this.levelColumnTwo = new ArrayList<>();
        }
        if (config.contains("Contract.".concat(name).concat(".level3"))){
            this.levelColumnThree = (ArrayList<Double>) config.getDoubleList("Contract.".concat(name).concat(".level3"));
        }else{
            this.levelColumnThree = new ArrayList<>();
        }
        if (config.contains("Contract.".concat(name).concat(".level4"))){
            this.levelColumnFour = (ArrayList<Double>) config.getDoubleList("Contract.".concat(name).concat(".level4"));
        }else{
            this.levelColumnFour = new ArrayList<>();
        }
        this.name =  config.getString("Contract.".concat(name).concat(".localized_name.").concat(config.getString("General.Language")));
        this.config = config;
        this.plugin = plugin;
    }

    public abstract void start();
    public abstract void stop();
}
