package net.andrewcpu.goodshulk;

public class ConfigManager {
    public static boolean usePermissions(){
        return ShulkDrive.getInstance().getConfig().getBoolean(ConfigPaths.usePermissions);
    }

    public static String getDefaultBoxName(){
        return ShulkDrive.getInstance().getConfig().getString(ConfigPaths.defaultBoxName);
    }
}
