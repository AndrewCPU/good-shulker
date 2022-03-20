package net.andrewcpu.goodshulk.config;

import net.andrewcpu.goodshulk.ShulkDrive;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.Locale;

public class ConfigManager {
    public static boolean usePermissions() {
        return ShulkDrive.getInstance().getConfig().getBoolean(ConfigPaths.usePermissions);
    }

    public static boolean canUseGoodShulk(Player player) {
        return (usePermissions() && player.hasPermission(PermissionDirectory.USAGE_PERMISSION)) || !usePermissions();
    }

    public static boolean canUseConfig(CommandSender sender) {
        return (sender.hasPermission(PermissionDirectory.USAGE_PERMISSION));
    }

    public static ClickType getOpenMethod(){
        return ClickType.valueOf(ShulkDrive.getInstance().getConfig().getString(ConfigPaths.openMethod));
    }

    public static String getDefaultBoxName() {
        return ChatColor.translateAlternateColorCodes('&', ShulkDrive.getInstance().getConfig().getString(ConfigPaths.defaultBoxName));
    }

    public static void setDefaultBoxName(String value){
        ShulkDrive.getInstance().getConfig().set(ConfigPaths.defaultBoxName, value);
        save();
    }

    public static boolean setOpenMethod(String type){
        if(ClickType.valueOf(type.toUpperCase(Locale.ROOT)) == null){
            return false;
        }
        ShulkDrive.getInstance().getConfig().set(ConfigPaths.openMethod, type.toUpperCase(Locale.ROOT));
        save();
        return true;
    }

    public static void setUsesPermissions(boolean value){
        ShulkDrive.getInstance().getConfig().set(ConfigPaths.usePermissions, value);
        save();
    }


    private static void save(){
        ShulkDrive.getInstance().saveConfig();
        ShulkDrive.getInstance().reloadConfig();
    }
}
