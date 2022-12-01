package tempestissimo.club.contingencycontract.utils;

import org.bukkit.configuration.Configuration;
import tempestissimo.club.contingencycontract.ContingencyContract;

public class GameCtrl {
    public ContingencyContract plugin;
    public Configuration config;

    public Boolean gameIsOn;
    public Long startTime;
    public Long endTime;

    public void startGame(){
        if (gameIsOn){
            return;
        }else{//game is off
            plugin.service.startContracts();
            this.gameIsOn = true;
        }
    }

    public void stopGame(){
        if (gameIsOn){
            plugin.service.stopContracts();
            this.gameIsOn = false;
        }else{//game is off
            return;
        }
    }

    public GameCtrl(ContingencyContract plugin, Configuration config) {
        this.plugin = plugin;
        this.config = config;
        this.gameIsOn = false;
        this.startTime = System.currentTimeMillis();
        this.endTime = System.currentTimeMillis();
    }
}
