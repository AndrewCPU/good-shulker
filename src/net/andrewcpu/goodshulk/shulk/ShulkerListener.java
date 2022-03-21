package net.andrewcpu.goodshulk.shulk;

import net.andrewcpu.goodshulk.ShulkDrive;
import net.andrewcpu.goodshulk.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class ShulkerListener implements Listener {
    private HashMap<Player, ShulkerUnit> openShulkerRegistry = new HashMap<>();

    public void saveAllShulkers(){
        openShulkerRegistry.values().forEach(ShulkerUnit::updateItemContents);
    }

    private ShulkerUnit isRegistered(Inventory i){
        return openShulkerRegistry.values().stream().filter(unit -> unit.getFakeInventory() == i).findFirst().orElse(null);
    }

    @EventHandler(ignoreCancelled = true)
    public void inventorySelect(InventoryClickEvent event) {
        if (ConfigManager.canUseGoodShulk((Player) event.getWhoClicked())) {
            if (event.getCurrentItem() != null) {
                if (event.getCurrentItem().getType() == Material.SHULKER_BOX) {
                    if (openShulkerRegistry.containsKey((Player) event.getWhoClicked())) {
                        event.setCancelled(true);
                        return;
                    }
                    if (event.getClick() == ConfigManager.getOpenMethod()) {
                        event.setCancelled(true);
                        event.getWhoClicked().closeInventory();
                        Bukkit.getScheduler().scheduleSyncDelayedTask(ShulkDrive.getInstance(), () -> {
                            ShulkerUnit unit = new ShulkerUnit((Player) event.getWhoClicked(), event.getCurrentItem());
                            openShulkerRegistry.put(((Player) event.getWhoClicked()), unit);
                            unit.openFakeInventory();
                        }, 0);
                    }
                }
            }
        }
    }

    @EventHandler
    public void inventoryMove(InventoryMoveItemEvent event){
        if(isRegistered(event.getDestination()) != null || isRegistered(event.getSource()) != null){
            ShulkerUnit unit = isRegistered(event.getDestination());
            if(unit == null) unit = isRegistered(event.getSource());
            unit.updateItemContents();
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onClose(InventoryCloseEvent event) {
        if (openShulkerRegistry.containsKey(event.getPlayer())) {
            if (event.getInventory() == openShulkerRegistry.get(event.getPlayer()).getFakeInventory()) {
                openShulkerRegistry.remove(event.getPlayer()).updateItemContents();
            }
        }
    }
}
