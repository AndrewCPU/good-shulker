package net.andrewcpu.goodshulk;

import net.andrewcpu.goodshulk.commands.GoodShulkCommandManager;
import net.andrewcpu.goodshulk.shulk.ShulkerListener;
import org.bukkit.plugin.java.JavaPlugin;

public class ShulkDrive extends JavaPlugin {
    private static ShulkDrive _instance = null;

    public static ShulkDrive getInstance() {
        return _instance;
    }
    private ShulkerListener shulkerListener;

    public void onEnable(){
        _instance = this;
        shulkerListener = new ShulkerListener();

        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(shulkerListener, this);
        getCommand("goodshulk").setExecutor(new GoodShulkCommandManager());
    }

    public void onDisable(){
        getLogger().info("Saving all open Shulkers...");
        shulkerListener.saveAllShulkers();
        getLogger().info("Done saving all open Shulkers.");
    }
}
