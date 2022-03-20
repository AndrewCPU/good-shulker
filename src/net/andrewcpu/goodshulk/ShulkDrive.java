package net.andrewcpu.goodshulk;

import net.andrewcpu.goodshulk.commands.GoodShulkCommandManager;
import net.andrewcpu.goodshulk.shulk.ShulkListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
        getCommand("goodshulk").setExecutor(new GoodShulkCommandManager());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        return super.onCommand(sender, command, label, args);
    }
}
