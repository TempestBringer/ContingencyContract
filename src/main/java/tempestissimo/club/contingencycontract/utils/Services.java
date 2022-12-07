package tempestissimo.club.contingencycontract.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tempestissimo.club.contingencycontract.ContingencyContract;
import tempestissimo.club.contingencycontract.contract.Contract;

import java.util.ArrayList;

import static org.bukkit.Bukkit.getServer;

public class Services {
    public ContingencyContract plugin;
    public Configuration config;

    /**
     * 启动游戏
     */
    public void startContracts(){
        for (Contract contract:plugin.contracts){
            contract.start();
        }
    }


    /**
     * 结束游戏
     */
    public void stopContracts(){
        for (Contract contract:plugin.contracts){
            contract.stop();
        }
    }

    /**
     * Change a contract's level.
     * @param player
     * @param contractIndex
     * @param level
     */
    public void changeContracts(Player player, Integer contractIndex, Integer level){
        if (contractIndex>=plugin.contracts.size()){
            indexOutOfBoundary(player);
            return;
        }
        if (!plugin.contracts.get(contractIndex).visible){
            changingAnInvisibleContract(player);
            return;
        }
        plugin.ctrl.changeContract(player, contractIndex, level);
    }

    public void broadcastContractCancel(Player player, Integer contractIndex, Integer level){
        ArrayList<TextComponent> textComponents = pluginNamePrefix();
        textComponents.add(getTextComponent("玩家", ChatColor.WHITE));
        textComponents.add(getTextComponent(player.getName(), ChatColor.RED));
        textComponents.add(getTextComponent("取消了合约 ", ChatColor.WHITE));
        textComponents.add(getTextComponent("[", ChatColor.RED));
        TextComponent contract = getTextComponent(plugin.contracts.get(contractIndex).name.concat(numberToRoma((plugin.contracts.get(contractIndex).levelClass.get(level)))), ChatColor.RED);
        ArrayList<TextComponent> hoverText = getHoverText(contractIndex,level);
        contract.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,hoverText.toArray(new TextComponent[hoverText.size()])));
        textComponents.add(contract);
        textComponents.add(getTextComponent("]", ChatColor.RED));
        for(Player tell:getServer().getOnlinePlayers()){
            tell.spigot().sendMessage(textComponents.toArray(new TextComponent[textComponents.size()]));
            sendTotalContractLevel(tell);
        }
    }

    public void broadcastContractSelect(Player player, Integer contractIndex, Integer level){
        ArrayList<TextComponent> textComponents = pluginNamePrefix();
        textComponents.add(getTextComponent("玩家", ChatColor.WHITE));
        textComponents.add(getTextComponent(player.getName(), ChatColor.RED));
        textComponents.add(getTextComponent("选择了合约 ", ChatColor.WHITE));
        textComponents.add(getTextComponent("[", ChatColor.RED));
        TextComponent contract = getTextComponent(plugin.contracts.get(contractIndex).name.concat(numberToRoma((plugin.contracts.get(contractIndex).levelClass.get(level)))), ChatColor.RED);
        ArrayList<TextComponent> hoverText = getHoverText(contractIndex,level);
        contract.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,hoverText.toArray(new TextComponent[hoverText.size()])));
        textComponents.add(contract);
        textComponents.add(getTextComponent("]", ChatColor.RED));
        for(Player tell:getServer().getOnlinePlayers()){
            tell.spigot().sendMessage(textComponents.toArray(new TextComponent[textComponents.size()]));
            sendTotalContractLevel(tell);
        }
    }

    public void gameIsRunning(Player player){
        ArrayList<TextComponent> result = pluginNamePrefix();
        result.add(getTextComponent(config.getString("Information.GameIsRunning.".concat(config.getString("General.Language"))),ChatColor.RED));
        player.spigot().sendMessage(result.toArray(new TextComponent[result.size()]));
    }

    public void gameWaitReset(){
        ArrayList<TextComponent> result = pluginNamePrefix();
        result.add(getTextComponent(config.getString("Information.GameWaitReset.".concat(config.getString("General.Language"))),ChatColor.GREEN));
        for (Player player:getServer().getOnlinePlayers()){
            player.spigot().sendMessage(result.toArray(new TextComponent[result.size()]));
        }
    }

    public void contractLocked(Player player){
        ArrayList<TextComponent> result = pluginNamePrefix();
        result.add(getTextComponent(config.getString("Information.ContractLocked.".concat(config.getString("General.Language"))),ChatColor.RED));
        player.spigot().sendMessage(result.toArray(new TextComponent[result.size()]));
    }


    public void broadcastGameStart(Player starter){
        ArrayList<TextComponent> result = pluginNamePrefix();
        result.add(getTextComponent(starter.getName().concat(" ").concat(config.getString("Information.GameStart.".concat(config.getString("General.Language")))),ChatColor.GREEN));
        for (Player player:getServer().getOnlinePlayers()){
            player.spigot().sendMessage(result.toArray(new TextComponent[result.size()]));
        }
    }

    public void gameHadStarted(Player player){
        ArrayList<TextComponent> result = pluginNamePrefix();
        result.add(getTextComponent(config.getString("Information.GameHadStarted.".concat(config.getString("General.Language"))),ChatColor.RED));
        player.spigot().sendMessage(result.toArray(new TextComponent[result.size()]));
    }

    public void gameHadStopped(Player player){
        ArrayList<TextComponent> result = pluginNamePrefix();
        result.add(getTextComponent(config.getString("Information.gameHadStopped.".concat(config.getString("General.Language"))),ChatColor.RED));
        player.spigot().sendMessage(result.toArray(new TextComponent[result.size()]));
    }

    public void broadcastGameStop(Player stopper){
        ArrayList<TextComponent> result = pluginNamePrefix();
        result.add(getTextComponent(config.getString("Information.GameStop.".concat(config.getString("General.Language"))),ChatColor.GREEN));
        for (Player player:getServer().getOnlinePlayers()){
            player.spigot().sendMessage(result.toArray(new TextComponent[result.size()]));
        }
    }

    /**
     * 私聊：有一项投票正在进行
     * @param player
     */
    public void aVoteIsAlreadyRunning(Player player){
        ArrayList<TextComponent> result = pluginNamePrefix();
        result.add(getTextComponent(config.getString("Information.AVoteIsRunning.".concat(config.getString("General.Language"))),ChatColor.RED));
        player.spigot().sendMessage(result.toArray(new TextComponent[result.size()]));
    }

    /**
     * 广播：开始进行终止合约投票
     * @param starter
     * @param seconds
     */
    public void broadcastStartGameStopVote(Player starter, Integer seconds){
        ArrayList<TextComponent> result = pluginNamePrefix();
        TextComponent playerName = getTextComponent(starter.getName(), ChatColor.RED);
        result.add(playerName);
        TextComponent text = getTextComponent(config.getString("Information.StartGameStopVote.".concat(config.getString("General.Language"))), ChatColor.WHITE);
        result.add(text);
        TextComponent  button = getTextComponent("[".concat(config.getString("Information.StartGameStopVoteButton.".concat(config.getString("General.Language")))).concat("]"),ChatColor.GREEN);
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/cc vote stop"));
        result.add(button);
        for (Player player:getServer().getOnlinePlayers()){
            player.spigot().sendMessage(result.toArray(new TextComponent[result.size()]));
        }
    }

    public void broadcastPlayerJoinVote(Player joiner,Integer seconds){
        ArrayList<TextComponent> result = pluginNamePrefix();
        TextComponent playerName = getTextComponent(joiner.getName(), ChatColor.RED);
        result.add(playerName);
        TextComponent text = getTextComponent(config.getString("Information.StartPlayerJoinVote.".concat(config.getString("General.Language"))), ChatColor.WHITE);
        result.add(text);
        TextComponent  button = getTextComponent("[".concat(config.getString("Information.StartGameResetVoteButton.".concat(config.getString("General.Language")))).concat("]"),ChatColor.GREEN);
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/cc vote join"));
        result.add(button);
        for (Player player:getServer().getOnlinePlayers()){
            player.spigot().sendMessage(result.toArray(new TextComponent[result.size()]));
        }
    }

    public void noVoteForSelf(Player player){

    }


    /**
     * 广播：开始进行地图重置投票
     * @param starter
     * @param seconds
     */
    public void broadcastStartGameResetVote(Player starter, Integer seconds){
        ArrayList<TextComponent> result = pluginNamePrefix();
        TextComponent playerName = getTextComponent(starter.getName(), ChatColor.RED);
        result.add(playerName);
        TextComponent text = getTextComponent(config.getString("Information.StartGameResetVote.".concat(config.getString("General.Language"))), ChatColor.WHITE);
        result.add(text);
        TextComponent  button = getTextComponent("[".concat(config.getString("Information.StartGameResetVoteButton.".concat(config.getString("General.Language")))).concat("]"),ChatColor.GREEN);
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/cc vote reset"));
        result.add(button);
        for (Player player:getServer().getOnlinePlayers()){
            player.spigot().sendMessage(result.toArray(new TextComponent[result.size()]));
        }
    }

    /**
     * 广播：投票人数不足
     */
    public void voteNotEnough(){
        ArrayList<TextComponent> result = pluginNamePrefix();
        result.add(getTextComponent(config.getString("Information.VoteNotEnough.".concat(config.getString("General.Language"))), ChatColor.RED));
        for (Player player:getServer().getOnlinePlayers()){
            player.spigot().sendMessage(result.toArray(new TextComponent[result.size()]));
        }
    }

    /**
     * 私聊：非生存玩家投票
     * @param player
     */
    public void voterNotSurvival(Player player){
        ArrayList<TextComponent> result = pluginNamePrefix();
        result.add(getTextComponent(config.getString("Information.VoterNotSurvival.".concat(config.getString("General.Language"))),ChatColor.RED));
        player.spigot().sendMessage(result.toArray(new TextComponent[result.size()]));
    }
    /**
     * 私聊：非生存玩家投票
     * @param player
     */
    public void voterNotSurvivalOrAdventure(Player player){
        ArrayList<TextComponent> result = pluginNamePrefix();
        result.add(getTextComponent(config.getString("Information.voterNotSurvivalOrAdventure.".concat(config.getString("General.Language"))),ChatColor.RED));
        player.spigot().sendMessage(result.toArray(new TextComponent[result.size()]));
    }
    /**
     * 私聊：非生存玩家创建投票
     * @param player
     */
    public void voteCreatorNotSurvival(Player player){
        ArrayList<TextComponent> result = pluginNamePrefix();
        result.add(getTextComponent(config.getString("Information.VoteCreatorNotSurvival.".concat(config.getString("General.Language"))),ChatColor.RED));
        player.spigot().sendMessage(result.toArray(new TextComponent[result.size()]));

    }

    /**
     * 私聊：非生存玩家创建投票
     * @param player
     */
    public void voteCreatorNotSurvivalOrAdventure(Player player){
        ArrayList<TextComponent> result = pluginNamePrefix();
        result.add(getTextComponent(config.getString("Information.voteCreatorNotSurvivalOrAdventure.".concat(config.getString("General.Language"))),ChatColor.RED));
        player.spigot().sendMessage(result.toArray(new TextComponent[result.size()]));

    }

    /**
     * 私聊： 投票成功
     * @param player
     */
    public void voteSuccess(Player player){
        ArrayList<TextComponent> result = pluginNamePrefix();
        result.add(getTextComponent(config.getString("Information.VoteSuccess.".concat(config.getString("General.Language"))),ChatColor.RED));
        player.spigot().sendMessage(result.toArray(new TextComponent[result.size()]));
    }

    /**
     * 私聊：无进行的投票
     * @param player
     */
    public void noVoteIsRunning(Player player){
        ArrayList<TextComponent> result = pluginNamePrefix();
        result.add(getTextComponent(config.getString("Information.NoVoteIsRunning.".concat(config.getString("General.Language"))),ChatColor.RED));
        player.spigot().sendMessage(result.toArray(new TextComponent[result.size()]));
    }



    /**
     * Send Message When Player Need Command Help
     * @param player
     */
    public void sendPluginIntroduction(Player player){
        ArrayList<TextComponent> result = pluginNamePrefix();
        player.spigot().sendMessage(result.toArray(new TextComponent[result.size()]));
        result = new ArrayList<>();
        result.add(getTextComponent("  - 简介 一款模仿“危机合约”玩法设计的插件，玩家可自选tag来增加游戏难度",ChatColor.WHITE));
        result.add(getTextComponent("  - 使用 /cc list选择词条后 /cc start以开始游戏 /cc stop以停止游戏",ChatColor.WHITE));
        result.add(getTextComponent("  - 人数 不限",ChatColor.WHITE));
        result.add(getTextComponent("  - 开发 TempestZYTux",ChatColor.WHITE));
        for (TextComponent text : result){
            player.spigot().sendMessage(text);
        }
    }
    /**
     * Send Message When Player Need Command Help
     * @param player
     */
    public void sendCommandIntroduction(Player player){
        ArrayList<TextComponent> result = pluginNamePrefix();
        player.spigot().sendMessage(result.toArray(new TextComponent[result.size()]));
        result = new ArrayList<>();
        result.add(getTextComponent("  - /cc            插件介绍",ChatColor.WHITE));
        result.add(getTextComponent("  - /cc help       同上",ChatColor.WHITE));
        result.add(getTextComponent("  - /cc list       可用tag列表，使用鼠标点选以及查看详情",ChatColor.WHITE));
        result.add(getTextComponent("  - /cc start      开始一局游戏，选择tag后需要开始游戏才能应用",ChatColor.WHITE));
        result.add(getTextComponent("  - /cc stop       停止一局游戏",ChatColor.WHITE));
        result.add(getTextComponent("  - /cc reset      重置游戏",ChatColor.WHITE));
        for (TextComponent text : result){
            player.spigot().sendMessage(text);
        }
    }

    /**
     * Send contracts to player's chat field.
     * @param player
     */
    public void sendContracts(Player player){
        ArrayList<TextComponent> prefix = pluginNamePrefix();
        player.spigot().sendMessage(prefix.toArray(new TextComponent[prefix.size()]));
        ArrayList<TextComponent> results = new ArrayList<>();
        for (int i=0;i<plugin.contracts.size();i++){
            Contract contract = plugin.contracts.get(i);
            String name = contract.name;
            if (!contract.visible){
                continue;
            }
            for (int j = 0; j < contract.levelClass.size(); j++) {
                TextComponent component = new TextComponent(" ["+name+numberToRoma(contract.levelClass.get(j))+"] ");
                ChatColor color = ChatColor.WHITE;
                if (contract.selectedIndex==j){
                    color=ChatColor.DARK_RED;
                }
                component.setColor(color);
                String runCommand = "/cc select ".concat(String.valueOf(i)).concat(" ").concat(String.valueOf(j));
                component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,runCommand));
                ArrayList<TextComponent> hoverText = getHoverText(i,j);
                component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,hoverText.toArray(new TextComponent[hoverText.size()])));
                results.add(component);
            }
            player.spigot().sendMessage(results.toArray(new TextComponent[results.size()]));
            results = new ArrayList<>();
        }
    }

    public ArrayList<TextComponent> getHoverText(Integer contractIndex, Integer level) {
        ArrayList<TextComponent> results = new ArrayList<>();
        String path = "Contract.".concat(plugin.contracts.get(contractIndex).iname).concat(".localized_description.").concat(config.getString("General.Language"));
        String raw = config.getString(path);
        String[] split = raw.split("<!>");
        for (int i = 0; i < split.length; i++) {
            results.add(getTextComponent(split[i],ChatColor.WHITE));
        }
        if (plugin.contracts.get(contractIndex).levelColumnFour.size()>0&&plugin.contracts.get(contractIndex).levelColumnFour.get(level)!=0.0) {
            results.add(5, getTextComponent(plugin.contracts.get(contractIndex).levelColumnFour.get(level).toString(),ChatColor.RED));
        }
        if (plugin.contracts.get(contractIndex).levelColumnThree.size()>0&&plugin.contracts.get(contractIndex).levelColumnThree.get(level)!=0.0) {
            results.add(4, getTextComponent(plugin.contracts.get(contractIndex).levelColumnThree.get(level).toString(),ChatColor.RED));
        }
        if (plugin.contracts.get(contractIndex).levelColumnTwo.size()>0&&plugin.contracts.get(contractIndex).levelColumnTwo.get(level)!=0.0) {
            results.add(3, getTextComponent(plugin.contracts.get(contractIndex).levelColumnTwo.get(level).toString(),ChatColor.RED));
        }
        if (plugin.contracts.get(contractIndex).levelColumnOne.size()>0&&plugin.contracts.get(contractIndex).levelColumnOne.get(level)!=0.0) {
            results.add(2, getTextComponent(plugin.contracts.get(contractIndex).levelColumnOne.get(level).toString(),ChatColor.RED));
        }
        if (plugin.contracts.get(contractIndex).levelColumnZero.size()>0&&plugin.contracts.get(contractIndex).levelColumnZero.get(level)!=0.0) {
            results.add(1, getTextComponent(plugin.contracts.get(contractIndex).levelColumnZero.get(level).toString(),ChatColor.RED));
        }
        return results;
    }

    /**
     * Number to Special String.
     * @param num
     * @return
     */
    public String numberToRoma(Integer num){
        if (num<0) {
            return "";
        } else if (num==0){
            return "";
        } else if (num==1) {
            return " I";
        } else if (num==2) {
            return " II";
        } else if (num==3) {
            return " III";
        } else if (num==4) {
            return " IV";
        } else if (num==5) {
            return " V";
        } else if (num==6) {
            return " VI";
        } else if (num==7) {
            return " VII";
        } else if (num==8) {
            return " VIII";
        } else if (num==9) {
            return " IX";
        } else if (num==10) {
            return " X";
        } else {
            return " X+";
        }
    }

    public void sendTotalContractLevel(Player player){
        Integer level = calculateTotalContractLevel();
        String text = config.getString("Information.TotalContractLevel.".concat(config.getString("General.Language"))).concat(" ");
        ArrayList<TextComponent> textComponents = pluginNamePrefix();
        textComponents.add(getTextComponent(text,ChatColor.WHITE));
        textComponents.add(getTextComponent(String.valueOf(level),ChatColor.RED));
        player.spigot().sendMessage(textComponents.toArray(new TextComponent[textComponents.size()]));
    }


    /**
     * Get Total Contract Level
     */
    public Integer calculateTotalContractLevel(){
        Integer result = 0;
        for (Contract contract:plugin.contracts){
            if (contract.selectedIndex>=0){
                result+=(contract.levelClass.get(contract.selectedIndex));
            }
        }
        return result;
    }


    /**
     * Plugin's prefix shown in chat field.
     * @return
     */
    public ArrayList<TextComponent> pluginNamePrefix(){
        ArrayList<TextComponent> results = new ArrayList<>();
        results.add(getTextComponent("/* ", ChatColor.RED));
        results.add(getTextComponent("危机合约",ChatColor.WHITE));
        results.add(getTextComponent(" */   ",ChatColor.RED));
        return results;
    }

    /**
     * Generate text component using string and chat color.
     * @param text
     * @param color
     * @return
     */
    public TextComponent getTextComponent(String text, ChatColor color){
        TextComponent component = new TextComponent(text);
        component.setColor(color);
        return component;
    }


    /**
     * Tell player when picked up an outdated item.
     * @param player
     */
    public void pickupAPastBubble(Player player, ItemStack itemStack,Integer second){
        ArrayList<TextComponent> results = pluginNamePrefix();
        String[] text = config.getString("Information.PickUpABubble.".concat(config.getString("General.Language"))).split("/");
        results.add(getTextComponent(text[0],ChatColor.WHITE));
        results.add(getTextComponent(String.valueOf(itemStack.getAmount()),ChatColor.RED));
        results.add(getTextComponent(text[1],ChatColor.WHITE));
        results.add(getTextComponent(itemStack.getType().name(),ChatColor.RED));
        results.add(getTextComponent(text[2],ChatColor.WHITE));
        results.add(getTextComponent(String.valueOf(second),ChatColor.RED));
        results.add(getTextComponent(text[3],ChatColor.WHITE));
        player.spigot().sendMessage(results.toArray(new TextComponent[results.size()]));
    }

    /**
     * Error: Changing An Invisible Contract.
     * @param player
     */
    public void changingAnInvisibleContract(Player player){
        ArrayList<TextComponent> results = pluginNamePrefix();
        String text = config.getString("Information.ChangingAnInvisibleContract.".concat(config.getString("General.Language")));
        results.add(getTextComponent(text,ChatColor.RED));
        player.spigot().sendMessage(results.toArray(new TextComponent[results.size()]));
    }

    /**
     * Error: Not Enough Arguments.
     * @param player
     */
    public void notEnoughArguments(Player player){
        ArrayList<TextComponent> results = pluginNamePrefix();
        String text = config.getString("Information.NotEnoughArguments.".concat(config.getString("General.Language")));
        results.add(getTextComponent(text,ChatColor.RED));
        player.spigot().sendMessage(results.toArray(new TextComponent[results.size()]));
    }

    /**
     * Error: Too Many Arguments.
     * @param player
     */
    public void tooManyArguments(Player player){
        ArrayList<TextComponent> results = pluginNamePrefix();
        String text = config.getString("Information.TooManyArguments.".concat(config.getString("General.Language")));
        results.add(getTextComponent(text,ChatColor.RED));
        player.spigot().sendMessage(results.toArray(new TextComponent[results.size()]));
    }
    /**
     * Error: Index out of boundary.
     * @param player
     */
    public void indexOutOfBoundary(Player player){
        ArrayList<TextComponent> results = pluginNamePrefix();
        String text = config.getString("Information.IndexOutOfBoundary.".concat(config.getString("General.Language")));
        results.add(getTextComponent(text,ChatColor.RED));
        player.spigot().sendMessage(results.toArray(new TextComponent[results.size()]));
    }

    /**
     * Error: Wrong Argument Type.
     * @param player
     */
    public void wrongArgumentType(Player player){
        ArrayList<TextComponent> results = pluginNamePrefix();
        String text = config.getString("Information.WrongArgumentType.".concat(config.getString("General.Language")));
        results.add(getTextComponent(text,ChatColor.RED));
        player.spigot().sendMessage(results.toArray(new TextComponent[results.size()]));
    }

    /**
     * Error: Should send command as player.
     * @param sender
     */
    public void infoShouldSendMessageAsPlayer(CommandSender sender){
        ArrayList<TextComponent> results = pluginNamePrefix();
        String text = config.getString("Information.ShouldSendMessageAsPlayer.".concat(config.getString("General.Language")));
        results.add(getTextComponent(text,ChatColor.RED));
        sender.spigot().sendMessage(results.toArray(new TextComponent[results.size()]));
    }



    /**
     * Constructor.
     * @param plugin
     * @param config
     */
    public Services(ContingencyContract plugin, Configuration config) {
        this.plugin = plugin;
        this.config = config;
    }
}
