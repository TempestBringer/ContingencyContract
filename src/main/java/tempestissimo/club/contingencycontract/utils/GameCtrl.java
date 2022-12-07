package tempestissimo.club.contingencycontract.utils;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import tempestissimo.club.contingencycontract.ContingencyContract;

import static org.bukkit.Bukkit.getServer;

public class GameCtrl implements Listener {
    public ContingencyContract plugin;
    public Configuration config;

    public Boolean gameIsOn;
    public Long startTime;
    public Long endTime;

    public Integer deathCount;
    public Integer maxReDeploy;

    public Boolean gameSuccess;
    public Boolean gameFail;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        if (gameIsOn){
            deathCount+=1;
        }
        if (maxReDeploy - deathCount<0){
            //gameFail
            this.gameFail=true;
            this.gameSuccess = false;
            this.gameIsOn = false;
            this.maxReDeploy = 999;
            this.endTime = System.currentTimeMillis();
        }
    }

    public void onPlayerTemporaryLeave(Player player){

    }

    // No wander before game start
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        if (gameIsOn){

        }else{
            //game is off
            if ((!gameFail)&&(!gameSuccess)){
                //not on
                if ((e.getTo().getWorld()!=plugin.location.getLobbySpawnPoint().getWorld())){
                    e.setTo(plugin.location.getLobbySpawnPoint());
                }
            } else if (gameSuccess&&(!gameFail)) {
                //gameSuccess
                if ((e.getTo().getWorld()!=plugin.location.getLobbySpawnPoint().getWorld())){
                    e.setTo(plugin.location.getLobbySpawnPoint());
                }
            } else if ((!gameSuccess)&& gameFail) {
                //gameFail
                if ((e.getTo().getWorld()!=plugin.location.getLobbySpawnPoint().getWorld())){
                    e.setTo(plugin.location.getLobbySpawnPoint());
                }
            } else{
                //Error
            }

        }
    }

    @EventHandler
    public void onPlayerGetDragonEgg(PlayerAdvancementDoneEvent e){
        if (gameIsOn){
            Advancement advancement = e.getAdvancement();
            if (advancement.getKey().getNamespace().equals("minecraft")&&advancement.getKey().getKey().equals("end/dragon_egg")){
                gameIsOn = false;
                gameSuccess = true;
                gameFail = false;
                for (Player player:getServer().getOnlinePlayers()){
                    player.setGameMode(GameMode.ADVENTURE);
                    player.teleport(plugin.location.getLobbySpawnPoint());
                }

            }
        }
    }

    /**
     * Action After Player Join Game
     * @param e
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if (gameIsOn){
            e.getPlayer().setGameMode(GameMode.SURVIVAL);
            if (e.getPlayer().getWorld().getName().equals(config.getString("Position.PlayerJoin.world"))){
                e.getPlayer().teleport(plugin.location.getOverWorldSpawnPoint());
            }
        }else{
            e.getPlayer().setGameMode(GameMode.ADVENTURE);
            e.getPlayer().teleport(plugin.location.getLobbySpawnPoint());
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
                existPlayer.teleport(plugin.location.getOverWorldSpawnPoint());
                existPlayer.setGameMode(GameMode.SURVIVAL);
            }
            plugin.location.getOverWorldSpawnPoint().getWorld().setTime(0);
        }
    }

    public void stopGame(Player player){
        if (gameIsOn){
            plugin.vote.voteForGameStop(player);
        }else{//game is off
            plugin.service.gameHadStopped(player);
            this.maxReDeploy = 999;
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
            plugin.vote.voteForGameReset(player);
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
        this.maxReDeploy = 999;
    }
}
