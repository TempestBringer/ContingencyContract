package tempestissimo.club.contingencycontract.utils;

import org.bukkit.GameMode;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tempestissimo.club.contingencycontract.ContingencyContract;

import java.util.HashMap;

import static org.bukkit.Bukkit.getServer;

public class Voter {
    public Boolean stopIsVoting;
    public ContingencyContract plugin;
    public Configuration config;
    public BukkitRunnable voteStopThread;
    public HashMap<String,Boolean> voteStopPool;

    public void playerVoteGameStop(Player player){
        if (!stopIsVoting){
            plugin.service.noVoteIsRunning(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL)){
            voteStopPool.put(player.getName(),true);
            plugin.service.voteSuccess(player);
        }else{
            plugin.service.voterNotSurvival(player);
        }
    }

    public void voteForGameStop(Player player){
        if (stopIsVoting){
            plugin.service.aVoteIsAlreadyRunning(player);
        }else if(!player.getGameMode().equals(GameMode.SURVIVAL)){
            plugin.service.voteCreatorNotSurvival(player);
        }else{
            stopIsVoting = true;
            voteStopPool = new HashMap<>();
            int seconds = config.getInt("Vote.Time.stop");
            double ratio = config.getDouble("Vote.Ratio.stop");
            plugin.service.broadcastStartGameStopVote(player,seconds);

            voteStopThread = new BukkitRunnable() {
                @Override
                public void run() {
                    int vote = voteStopPool.keySet().size();
                    int total = 0;
                    for (Player allPlayer:getServer().getOnlinePlayers()){
                        if (allPlayer.getGameMode().equals(GameMode.SURVIVAL)){
                            total++;
                        }
                    }
                    if (vote>ratio*total){
                        // ok
                        plugin.service.broadcastGameStop(player);
                        plugin.service.stopContracts();
                        plugin.ctrl.gameIsOn = false;
                        plugin.ctrl.endTime = System.currentTimeMillis();
                        for (Player p:getServer().getOnlinePlayers()){
                            p.setGameMode(GameMode.ADVENTURE);
                        }
                    }else{
                        // notOK
                        plugin.service.voteNotEnough();

                    }
                    stopIsVoting = false;
                    this.cancel();
                }
            };
            voteStopThread.runTaskLater(plugin, seconds*20);
        }

    }

    public Boolean resetIsVoting;
    public BukkitRunnable voteResetThread;
    public HashMap<String,Boolean> voteResetPool;

    public void playerVoteGameReset(Player player){
        if (!resetIsVoting){
            plugin.service.noVoteIsRunning(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL)||player.getGameMode().equals(GameMode.ADVENTURE)){
            voteResetPool.put(player.getName(),true);
            plugin.service.voteSuccess(player);
        }else{
            plugin.service.voterNotSurvivalOrAdventure(player);
        }
    }

    public void voteForGameReset(Player player){
        if (resetIsVoting){
            plugin.service.aVoteIsAlreadyRunning(player);
        }else if((!player.getGameMode().equals(GameMode.SURVIVAL)&&(!player.getGameMode().equals(GameMode.ADVENTURE)))){
            plugin.service.voteCreatorNotSurvivalOrAdventure(player);
        }else{
            resetIsVoting = true;
            voteResetPool = new HashMap<>();
            int seconds = config.getInt("Vote.Time.reset");
            double ratio = config.getDouble("Vote.Ratio.reset");
            plugin.service.broadcastStartGameResetVote(player,seconds);

            voteResetThread = new BukkitRunnable() {
                @Override
                public void run() {
                    int vote = voteResetPool.keySet().size();
                    int total = 0;
                    for (Player allPlayer:getServer().getOnlinePlayers()){
                        if (allPlayer.getGameMode().equals(GameMode.SURVIVAL)||allPlayer.getGameMode().equals(GameMode.ADVENTURE)){
                            total++;
                        }
                    }
                    if (vote>ratio*total){
                        // ok
                        plugin.service.gameWaitReset();
                        getServer().shutdown();
                    }else{
                        // notOK
                        plugin.service.voteNotEnough();
                    }
                    resetIsVoting = false;
                    this.cancel();
                }
            };
            voteResetThread.runTaskLater(plugin, seconds*20);
        }

    }

    public Boolean joinIsVoting;
    public BukkitRunnable voteJoinThread;
    public HashMap<String,Boolean> voteJoinPool;

    public void playerVoteJoin(Player player){
        if (!joinIsVoting){
            plugin.service.noVoteIsRunning(player);
            return;
        }
        if (player.getGameMode().equals(GameMode.SURVIVAL)){
            voteJoinPool.put(player.getName(),true);
            plugin.service.voteSuccess(player);
        }else{
            plugin.service.voterNotSurvival(player);
        }
    }

    public void voteForJoin(Player player){
        if (joinIsVoting){
            plugin.service.aVoteIsAlreadyRunning(player);
        }else if(!player.getGameMode().equals(GameMode.SURVIVAL)){
            plugin.service.voteCreatorNotSurvival(player);
        }else{
            joinIsVoting = true;
            voteJoinPool = new HashMap<>();
            int seconds = config.getInt("Vote.Time.join");
            double ratio = config.getDouble("Vote.Ratio.join");
//            plugin.service.broadcastStartGameStopVote(player,seconds);

            voteJoinThread = new BukkitRunnable() {
                @Override
                public void run() {
                    int vote = voteJoinPool.keySet().size();
                    int total = 0;
                    for (Player allPlayer:getServer().getOnlinePlayers()){
                        if (allPlayer.getGameMode().equals(GameMode.SURVIVAL)){
                            total++;
                        }
                    }
                    if (vote>ratio*total){
                        // ok
//                        plugin.service.broadcastGameStop(player);
//                        plugin.service.stopContracts();
//                        plugin.ctrl.gameIsOn = false;
//                        plugin.ctrl.endTime = System.currentTimeMillis();
                    }else{
                        // notOK
                        plugin.service.voteNotEnough();

                    }
                    joinIsVoting = false;
                    this.cancel();
                }
            };
            voteJoinThread.runTaskLater(plugin, seconds*20);
        }

    }



    public Voter(ContingencyContract plugin, Configuration config) {
        this.plugin = plugin;
        this.config = config;
        this.stopIsVoting = false;
        this.resetIsVoting = false;
        this.joinIsVoting = false;
    }
}
