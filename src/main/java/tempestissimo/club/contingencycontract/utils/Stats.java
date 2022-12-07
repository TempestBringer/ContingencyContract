package tempestissimo.club.contingencycontract.utils;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import tempestissimo.club.contingencycontract.ContingencyContract;

import static org.bukkit.Bukkit.getServer;

public class Stats {
    public ContingencyContract plugin;
    public Configuration config;

    public ScoreboardManager manager;
    public Scoreboard pluginScoreboard;
    public BukkitRunnable thread;

    public Integer reDeploy;

    public void firstRunCheck(){
        if(pluginScoreboard.getObjective("stats")==null){
            scoreBoardInit("stats", "合约进行中");
        }

    }

    public Objective scoreBoardInit(String scoreboardName,String displayName){
        Objective sb = pluginScoreboard.registerNewObjective(scoreboardName, Criteria.DUMMY, displayName);
        return sb;
    }

    public String getTranslate(String source){
        return config.getString("Status.".concat(source).concat(".").concat(config.getString("General.Language")));
    }

    public Stats(ContingencyContract plugin, Configuration config) {
        this.plugin = plugin;
        this.config = config;
        this.manager = getServer().getScoreboardManager();
        this.pluginScoreboard = getServer().getScoreboardManager().getNewScoreboard();
        this.firstRunCheck();
        this.thread = new BukkitRunnable() {
            @Override
            public void run() {
                if (plugin.ctrl.gameIsOn){
                    //Check
                    Objective stats = pluginScoreboard.getObjective("stats");
                    if (stats ==null){
                        pluginScoreboard.registerNewObjective("stats",Criteria.DUMMY,"合约进行中");
                    }
                    stats.setDisplayName("合约进行中");
                    // Total level
                    Integer contractTotalLevel = plugin.service.calculateTotalContractLevel();
                    Score scoreLevel = stats.getScore("合约等级");
                    scoreLevel.setScore(contractTotalLevel);
                    // Timer
                    long seconds = (System.currentTimeMillis()-plugin.ctrl.startTime)/1000;
                    Score scoreTime = stats.getScore("耗时");
                    scoreTime.setScore(Math.toIntExact(seconds));
                    // DeathCount
                    Integer deathCount = plugin.ctrl.maxReDeploy - plugin.ctrl.deathCount;
                    Score scoreDeath = stats.getScore("再部署");
                    scoreDeath.setScore(deathCount);
                    stats.setDisplaySlot(DisplaySlot.SIDEBAR);
                    for (Player player:getServer().getOnlinePlayers()){
                        player.setScoreboard(stats.getScoreboard());
                    }
                }else{
                    if ((!plugin.ctrl.gameSuccess)&&(!plugin.ctrl.gameFail)){
                        //not running
                        Objective stats = pluginScoreboard.getObjective("stats");
                        if (stats ==null){
                            pluginScoreboard.registerNewObjective("stats",Criteria.DUMMY,"合约未运行");
                        }
                        stats.setDisplayName("合约未运行");
                        Integer contractTotalLevel = plugin.service.calculateTotalContractLevel();
                        Score scoreLevel = stats.getScore("合约等级");
                        scoreLevel.setScore(contractTotalLevel);
                        // Timer
                        Score scoreTime = stats.getScore("耗时");
                        scoreTime.setScore(Math.toIntExact(0));
                        // DeathCount
                        Integer deathCount = plugin.ctrl.maxReDeploy - plugin.ctrl.deathCount;
                        Score scoreDeath = stats.getScore("再部署");
                        scoreDeath.setScore(deathCount);
                        stats.setDisplaySlot(DisplaySlot.SIDEBAR);
                        for (Player player:getServer().getOnlinePlayers()){
                            player.setScoreboard(stats.getScoreboard());
                        }
                    } else if (plugin.ctrl.gameSuccess&&(!plugin.ctrl.gameFail)) {
                        //success
                        Objective stats = pluginScoreboard.getObjective("stats");
                        if (stats ==null){
                            pluginScoreboard.registerNewObjective("stats",Criteria.DUMMY,"合约完成");
                        }
                        stats.setDisplayName("合约完成");

                        stats.setDisplaySlot(DisplaySlot.SIDEBAR);
                        for (Player player:getServer().getOnlinePlayers()){
                            player.setScoreboard(stats.getScoreboard());
                        }
                    } else if ((!plugin.ctrl.gameSuccess)&&plugin.ctrl.gameFail) {
                        //Fail
                        Objective stats = pluginScoreboard.getObjective("stats");
                        if (stats ==null){
                            pluginScoreboard.registerNewObjective("stats",Criteria.DUMMY,"合约失败");
                        }
                        stats.setDisplayName("合约失败");

                        stats.setDisplaySlot(DisplaySlot.SIDEBAR);
                        for (Player player:getServer().getOnlinePlayers()){
                            player.setScoreboard(stats.getScoreboard());
                        }
                    } else if (plugin.ctrl.gameSuccess&&plugin.ctrl.gameFail) {
                        //Error
                    }
                }
            }
        };
        this.thread.runTaskTimer(plugin, 5,5);
    }
}
