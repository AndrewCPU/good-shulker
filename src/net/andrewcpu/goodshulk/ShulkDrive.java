package net.andrewcpu.goodshulk;

import org.bukkit.plugin.java.JavaPlugin;

public class ShulkDrive extends JavaPlugin {
    private static ShulkDrive _instance = null;

    public static ShulkDrive getInstance() {
        return _instance;
    }

    public void onEnable(){
        _instance = this;
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new ShulkListener(), this);
    }
}
