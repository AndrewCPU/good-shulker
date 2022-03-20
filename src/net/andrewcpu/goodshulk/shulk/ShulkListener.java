package net.andrewcpu.goodshulk.shulk;

import net.andrewcpu.goodshulk.ShulkDrive;
import net.andrewcpu.goodshulk.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.HashMap;

public class ShulkListener implements Listener {
    private HashMap<Player, ShulkUnit> shulkRegistry = new HashMap<>();

    @EventHandler(ignoreCancelled = true)
    public void inventorySelect(InventoryClickEvent event) {
        if (ConfigManager.canUseGoodShulk((Player) event.getWhoClicked())) {
            if (event.getCurrentItem() != null) {
                if (event.getCurrentItem().getType() == Material.SHULKER_BOX) {
                    if (shulkRegistry.containsKey((Player) event.getWhoClicked())) {
                        event.setCancelled(true);
                        return;
                    }
                    if (event.getClick() == ConfigManager.getOpenMethod()) {
                        ShulkerBox shulkerBox = (ShulkerBox) ((BlockStateMeta) event.getCurrentItem().getItemMeta()).getBlockState();
                        event.setCancelled(true);
                        event.getWhoClicked().closeInventory();
                        Bukkit.getScheduler().scheduleSyncDelayedTask(ShulkDrive.getInstance(), () -> {
                            String inventoryName; // fake inventory inventoryName

                            if (event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName())
                                inventoryName = event.getCurrentItem().getItemMeta().getDisplayName();
                            else
                                inventoryName = ConfigManager.getDefaultBoxName();

                            Inventory inventory = Bukkit.createInventory(null, shulkerBox.getInventory().getSize(), inventoryName);
                            inventory.setContents(shulkerBox.getInventory().getContents());
                            event.getWhoClicked().openInventory(inventory);
                            shulkRegistry.put(((Player) event.getWhoClicked()), new ShulkUnit((Player) event.getWhoClicked(), inventory, shulkerBox, event.getCurrentItem()));

                        }, 0);

                    }
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onClose(InventoryCloseEvent event) {
        if (shulkRegistry.containsKey(event.getPlayer())) {
            if (event.getInventory() == shulkRegistry.get(event.getPlayer()).getFakeInventory()) {
                ShulkUnit unit = shulkRegistry.get(event.getPlayer());
                unit.updateItemContents();
                shulkRegistry.remove(event.getPlayer());
            }
        }
    }
}
