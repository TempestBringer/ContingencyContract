package tempestissimo.club.contingencycontract.utils;

import org.bukkit.GameMode;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

import static org.bukkit.Bukkit.getServer;

public class GameCtrl implements Listener {
    public ContingencyContract plugin;
    public Configuration config;

    public Boolean gameIsOn;
    public Long startTime;
    public Long endTime;

    public Integer deathCount;

    public Boolean gameSuccess;
    public Boolean gameFail;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        if (gameIsOn){
            deathCount+=1;
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if (gameIsOn){
            e.getPlayer().setGameMode(GameMode.SURVIVAL);
        }else{
            e.getPlayer().setGameMode(GameMode.SPECTATOR);
        }
    }

    public void startGame(Player player){
        if (gameIsOn){
            plugin.service.gameHadStarted(player);
            return;
        }else{//game is off
            plugin.service.broadcastGameStart(player);
            plugin.service.startContracts();
            this.gameIsOn = true;
            startTime = System.currentTimeMillis();
            deathCount = 0;
            gameSuccess = false;
            gameFail = false;
            for (Player existPlayer:getServer().getOnlinePlayers()){
                existPlayer.setGameMode(GameMode.SURVIVAL);
            }
        }
    }

    public void stopGame(Player player){
        if (gameIsOn){
            plugin.service.broadcastGameStop();
            plugin.service.stopContracts();
            this.gameIsOn = false;
            endTime = System.currentTimeMillis();
        }else{//game is off
            plugin.service.gameHadStopped(player);
            return;
        }
    }

    public void changeContract(Player player, Integer contractIndex, Integer level){
        if (gameIsOn){
            plugin.service.contractLocked(player);
            return;
        }else{
            if (plugin.contracts.get(contractIndex).selectedIndex==level){
                plugin.contracts.get(contractIndex).selectedIndex = -1;
                plugin.service.broadcastContractCancel(player,contractIndex,level);
                plugin.service.sendContracts(player);

            }else{
                plugin.contracts.get(contractIndex).selectedIndex = level;
                plugin.service.broadcastContractSelect(player,contractIndex,level);
                plugin.service.sendContracts(player);

            }
        }

    }

    public void resetGame(Player player){
        if (gameIsOn){
            plugin.service.gameIsRunning(player);
        }else{
            plugin.service.gameWaitReset();
            getServer().shutdown();
        }
    }

    public GameCtrl(ContingencyContract plugin, Configuration config) {
        this.plugin = plugin;
        this.config = config;
        this.gameIsOn = false;
        this.startTime = System.currentTimeMillis();
        this.endTime = System.currentTimeMillis();
        this.deathCount = 0;
        this.gameSuccess = false;
        this.gameFail = false;
    }
}
