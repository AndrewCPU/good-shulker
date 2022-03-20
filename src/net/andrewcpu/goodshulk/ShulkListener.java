package net.andrewcpu.goodshulk;

import org.bukkit.Bukkit;
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
    private HashMap<Player, ShulkUnit> openShulks = new HashMap<>();

    @EventHandler(ignoreCancelled = true)
    public void inventorySelect(InventoryClickEvent event) {
        if((ConfigManager.usePermissions() && event.getWhoClicked().hasPermission("goodshulk.use")) || !ConfigManager.usePermissions()){
            if (event.getCurrentItem() != null) {
                if (event.getCurrentItem().getType() == Material.SHULKER_BOX) {
                    if(openShulks.containsKey((Player) event.getWhoClicked())){
                        event.setCancelled(true);
                        return;
                    }
                    if (event.getClick() == ClickType.RIGHT) {
                        ShulkerBox shulkerBox = (ShulkerBox) ((BlockStateMeta) event.getCurrentItem().getItemMeta()).getBlockState();
                        event.setCancelled(true);
                        event.getWhoClicked().closeInventory();

                        Bukkit.getScheduler().scheduleSyncDelayedTask(ShulkDrive.getInstance(), () -> {
                            String name;
                            if (event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
                                name = event.getCurrentItem().getItemMeta().getDisplayName();
                            } else {
                                name = ShulkDrive.getInstance().getConfig().getString("default_box_name");
                            }
                            Inventory inventory = Bukkit.createInventory(null, shulkerBox.getInventory().getSize(), name);
                            inventory.setContents(shulkerBox.getInventory().getContents());
                            ((Player) event.getWhoClicked()).openInventory(inventory);
                            openShulks.put(((Player) event.getWhoClicked()), new ShulkUnit((Player) event.getWhoClicked(), inventory, shulkerBox, event.getCurrentItem()));
                        }, 0);
                    }
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onClose(InventoryCloseEvent event){
        if(openShulks.containsKey(event.getPlayer())){
            if(event.getInventory() == openShulks.get(event.getPlayer()).getFakeInventory())
            {
                ShulkUnit unit = openShulks.get(event.getPlayer());
                openShulks.remove(event.getPlayer());
                BlockStateMeta meta = (BlockStateMeta) unit.getItemStack().getItemMeta();
                unit.getShulkerBox().getInventory().setContents(unit.getFakeInventory().getContents());
                meta.setBlockState(unit.getShulkerBox());
                unit.getItemStack().setItemMeta(meta);
            }
        }
    }
}
