package tempestissimo.club.contingencycontract.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import tempestissimo.club.contingencycontract.ContingencyContract;

import java.util.ArrayList;
import java.util.List;

public class Commands implements CommandExecutor, TabCompleter {
    public ContingencyContract plugin;
    public Configuration config;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("cc")){
            if (!(sender instanceof Player)){
                plugin.service.infoShouldSendMessageAsPlayer(sender);
                return true;
            }
            Player player = (Player) sender;
            if (args.length==0){
                plugin.service.sendPluginIntroduction(player);
                plugin.service.sendCommandIntroduction(player);
            }else{ // args[0] exist
                if (args[0].equalsIgnoreCase("select")){
                    if (args.length<3){
                        plugin.service.notEnoughArguments(player);
                        return true;
                    } else if (args.length>3) {
                        plugin.service.tooManyArguments(player);
                        return true;
                    } else{
                        if (StringUtils.isNumeric(args[1])&& StringUtils.isNumeric(args[2])){
                            Integer contractIndex = Integer.valueOf(args[1]);
                            Integer level = Integer.valueOf(args[2]);
                            plugin.service.changeContracts(player,contractIndex,level);
                            return true;
                        }else{
                            plugin.service.wrongArgumentType(player);
                            return true;
                        }
                    }
                }else if (args[0].equalsIgnoreCase("list")){
                    plugin.service.sendContracts(player);
                    return true;
                }else if (args[0].equalsIgnoreCase("help")){
                    plugin.service.sendPluginIntroduction(player);
                    plugin.service.sendCommandIntroduction(player);
                }else if (args[0].equalsIgnoreCase("start")){
                    plugin.ctrl.startGame(player);
                    return true;
                }else if (args[0].equalsIgnoreCase("stop")){
                    plugin.ctrl.stopGame(player);
                    return true;
                }else if (args[0].equalsIgnoreCase("reset")){
                    plugin.ctrl.resetGame(player);
                    return true;
                }else if (args[0].equalsIgnoreCase("vote")){
                    if (args.length<2){
                        plugin.service.notEnoughArguments(player);
                        return true;
                    } else if (args.length>2) {
                        plugin.service.tooManyArguments(player);
                        return true;
                    } else{
                        if (args[1].equalsIgnoreCase("stop")){
                            plugin.vote.playerVoteGameStop(player);
                        } else if (args[1].equalsIgnoreCase("reset")) {
                            plugin.vote.playerVoteGameReset(player);
                        }
                    }
                    return true;
                }
//                else if (args[0].equalsIgnoreCase("create")){
//                    plugin.world.createWorldGroup();
//                    return true;
//                }else if (args[0].equalsIgnoreCase("remove")){
//                    plugin.world.unloadWorldGroup();
//                    return true;
//                }

            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> results = new ArrayList<>();
        results.add("start");
        results.add("stop");
        results.add("list");
        results.add("help");
        results.add("reset");
        return results;

    }





    public Commands(ContingencyContract plugin, Configuration config) {
        this.plugin = plugin;
        this.config = config;
    }
}
