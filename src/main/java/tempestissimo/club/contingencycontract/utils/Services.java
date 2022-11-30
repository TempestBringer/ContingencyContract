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
        if (plugin.contracts.get(contractIndex).selectedIndex==level){
            plugin.contracts.get(contractIndex).selectedIndex = -1;
            broadcastContractCancel(player,contractIndex,level);
        }else{
            plugin.contracts.get(contractIndex).selectedIndex = level;
            broadcastContractSelect(player,contractIndex,level);

        }
    }

    public void broadcastContractCancel(Player player, Integer contractIndex, Integer level){
        ArrayList<TextComponent> textComponents = pluginNamePrefix();
        textComponents.add(getTextComponent("玩家", ChatColor.WHITE));
        textComponents.add(getTextComponent(player.getName(), ChatColor.RED));
        textComponents.add(getTextComponent("取消了合约 ", ChatColor.WHITE));
        textComponents.add(getTextComponent("[", ChatColor.RED));
        TextComponent contract = getTextComponent(plugin.contracts.get(contractIndex).name.concat(numberToRoma((level+1))), ChatColor.RED);
        ArrayList<TextComponent> hoverText = getHoverText(contractIndex,level);
        contract.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,hoverText.toArray(new TextComponent[hoverText.size()])));
        textComponents.add(contract);
        textComponents.add(getTextComponent("]", ChatColor.RED));
        for(Player tell:getServer().getOnlinePlayers()){
            tell.spigot().sendMessage(textComponents.toArray(new TextComponent[textComponents.size()]));
        }
    }

    public void broadcastContractSelect(Player player, Integer contractIndex, Integer level){
        ArrayList<TextComponent> textComponents = pluginNamePrefix();
        textComponents.add(getTextComponent("玩家", ChatColor.WHITE));
        textComponents.add(getTextComponent(player.getName(), ChatColor.RED));
        textComponents.add(getTextComponent("选择了合约 ", ChatColor.WHITE));
        textComponents.add(getTextComponent("[", ChatColor.RED));
        TextComponent contract = getTextComponent(plugin.contracts.get(contractIndex).name.concat(numberToRoma((level+1))), ChatColor.RED);
        ArrayList<TextComponent> hoverText = getHoverText(contractIndex,level);
        contract.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,hoverText.toArray(new TextComponent[hoverText.size()])));
        textComponents.add(contract);
        textComponents.add(getTextComponent("]", ChatColor.RED));
        for(Player tell:getServer().getOnlinePlayers()){
            tell.spigot().sendMessage(textComponents.toArray(new TextComponent[textComponents.size()]));
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
            for (int j = 0; j < contract.levelColumnZero.size(); j++) {
                TextComponent component = new TextComponent(" ["+name+numberToRoma(j+1)+"] ");
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
        if (plugin.contracts.get(contractIndex).levelColumnFour.size()>0) {
            results.add(5, getTextComponent(plugin.contracts.get(contractIndex).levelColumnFour.get(level).toString(),ChatColor.RED));
        }
        if (plugin.contracts.get(contractIndex).levelColumnThree.size()>0) {
            results.add(4, getTextComponent(plugin.contracts.get(contractIndex).levelColumnThree.get(level).toString(),ChatColor.RED));
        }
        if (plugin.contracts.get(contractIndex).levelColumnTwo.size()>0) {
            results.add(3, getTextComponent(plugin.contracts.get(contractIndex).levelColumnTwo.get(level).toString(),ChatColor.RED));
        }
        if (plugin.contracts.get(contractIndex).levelColumnOne.size()>0) {
            results.add(2, getTextComponent(plugin.contracts.get(contractIndex).levelColumnOne.get(level).toString(),ChatColor.RED));
        }
        if (plugin.contracts.get(contractIndex).levelColumnZero.size()>0) {
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
        String[] text = config.getString("Information.ChangingAnInvisibleContract.".concat(config.getString("General.Language"))).split("/");
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
