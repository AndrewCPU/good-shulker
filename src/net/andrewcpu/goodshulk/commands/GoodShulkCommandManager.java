package net.andrewcpu.goodshulk.commands;

import net.andrewcpu.goodshulk.ShulkDrive;
import net.andrewcpu.goodshulk.config.ConfigManager;
import net.andrewcpu.goodshulk.config.PermissionDirectory;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class GoodShulkCommandManager implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(command.getName().equals("goodshulk")){
            if(args.length == 0){
                sendHelp(commandSender);
                return true;
            }
            if(args[0].equalsIgnoreCase("set")){
                doSet(commandSender, args);
            }
            else if(args[0].equalsIgnoreCase("get")){
                doGet(commandSender, args);
            }
            else if(args[0].equalsIgnoreCase("reload")){
                ShulkDrive.getInstance().reloadConfig();
            }
            else{
                sendHelp(commandSender);
                return true;
            }
            return true;
        }
        return false;
    }

    private int countBefore(String s, String target){
        int targetCount = 0;
        for(int i =0; i<s.length(); i++){
            if(s.charAt(i) == target.charAt(0)){
                targetCount++;
            }
        }
        return targetCount;
    }

    private String parseCommand(String command, ChatColor defaultColor){
        String output = "";
        String[] chars = command.split("");
        for(String s : chars){
            if(s.equals("[")){
                output += defaultColor + s + ChatColor.YELLOW;
            }
            else if(s.equals("]")){
                output += defaultColor + s;
            }
            else if(s.equals("|")){
                output += ChatColor.GRAY + s + ChatColor.YELLOW;
            }
            else if(s.equals("\"")){
                if(countBefore(output, "\"") % 2 == 0){
                    output += defaultColor + s + ChatColor.LIGHT_PURPLE;
                }
                else{
                    output += defaultColor + s;
                }
            }
            else if(s.equals(" ")){
                int countBefore = countBefore(output, " ");
                if(countBefore == 0){
                    output += s + ChatColor.GREEN;
                }
                else if(countBefore == 1){
                    output += s + ChatColor.AQUA;
                }
                else{
                    if(countBefore(output, "\"") % 2 == 0 ){
                        if(countBefore(output, "[")  == countBefore(output, "]") ){
                            output += s + defaultColor;
                        }
                        else{
                            output += s + ChatColor.YELLOW;
                        }
                    }
                    else{
                        output += s + ChatColor.LIGHT_PURPLE;
                    }
                }

            }
            else{
                output += s;
            }
        }
        return output;
    }


    private void sendHelp(CommandSender sender){
        HashMap<String, String> commandUsage = new HashMap<>();
        commandUsage.put("/goodshulk set default_name \"" + ConfigManager.getDefaultBoxName() + "\"", "Set the default Shulker Box name if the item doesn't have a Display Name");
        commandUsage.put("/goodshulk set open_method [LEFT | SHIFT_LEFT | RIGHT | SHIFT_RIGHT | MIDDLE | DOUBLE_CLICK | CONTROL_DROP]", "Set's the default inventory action which triggers opening the Shulker Box");
        commandUsage.put("/goodshulk set uses_permissions [true | false]", "Allow only users with [" + PermissionDirectory.USAGE_PERMISSION + "] to use GoodShulk?");
        commandUsage.put("/goodshulk get default_name", "Get the current default_name of the Shulker Box inventory");
        commandUsage.put("/goodshulk get open_method", "Get the current open method of the Shulker Box");
        commandUsage.put("/goodshulk get uses_permissions", "Get the current uses_permissions value");
        List<String> keys = commandUsage.keySet().stream().sorted().collect(Collectors.toList());
        sender.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "GoodShulk Usage");
        for(String key : keys){
            sender.sendMessage(parseCommand(key, ChatColor.WHITE));
            sender.sendMessage(ChatColor.GRAY + (commandUsage.get(key)));
            sender.sendMessage("");
        }
    }


    public void doSet(CommandSender sender, String[] args){
        if(args.length < 3){
            sendHelp(sender);
        }
        else{
            String key = args[1];
            String value = String.join(" ", Arrays.stream(args).toList().subList(2, args.length));
            value = value.replaceAll("\"", "");
            if(key.equalsIgnoreCase("default_name")){
                ConfigManager.setDefaultBoxName(value);
                sender.sendMessage(ChatColor.GREEN + "Updated default name to " + ChatColor.LIGHT_PURPLE + ChatColor.translateAlternateColorCodes('&', value));
            }
            else if(key.equalsIgnoreCase("open_method")){
                if(ConfigManager.setOpenMethod(value)){
                    sender.sendMessage(ChatColor.YELLOW + "Successfully updated open_method to " + ChatColor.GREEN + value);
                }
                else{
                    sender.sendMessage(ChatColor.RED + "Invalid value.");
                }
            }
            else if(key.equalsIgnoreCase("uses_permissions")){
                boolean b;
                try{
                    b = Boolean.parseBoolean(value);
                }catch (Exception ex){
                    sender.sendMessage(ChatColor.RED + "Invalid value.");
                    return;
                }
                ConfigManager.setUsesPermissions(b);
            }
            else{
                sendHelp(sender);
            }
        }
    }

    public void doGet(CommandSender sender, String[] args){
        if(args.length != 2){
            sendHelp(sender);
        }
        else{
            String target = args[1];
            if(target.equalsIgnoreCase("open_method")){
                sender.sendMessage(ChatColor.GREEN + "Current Open Method: " + ChatColor.YELLOW + ConfigManager.getOpenMethod().toString().substring(ConfigManager.getOpenMethod().toString().indexOf(".") + 1));
            }
            else if(target.equalsIgnoreCase("uses_permissions")){
                sender.sendMessage(ChatColor.GREEN + "Current Value of Uses Permissions: " + ChatColor.YELLOW + ConfigManager.usePermissions());
            }
            else if(target.equalsIgnoreCase("default_name")){
                sender.sendMessage(ChatColor.GREEN + "Current Default Inventory Display Name: " + ChatColor.YELLOW +  ConfigManager.getDefaultBoxName());
            }
        }
    }

}
