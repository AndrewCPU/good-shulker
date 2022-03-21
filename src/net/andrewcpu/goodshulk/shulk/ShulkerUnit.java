package net.andrewcpu.goodshulk.shulk;

import net.andrewcpu.goodshulk.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class ShulkerUnit {
    private Player player;
    private Inventory fakeInventory;
    private ShulkerBox shulkerBox;
    private ItemStack itemStack;

    public ShulkerUnit(Player player, ItemStack itemStack) {
        this.player = player;
        this.shulkerBox = (ShulkerBox) ((BlockStateMeta)itemStack.getItemMeta()).getBlockState();
        this.itemStack = itemStack;
        createFakeInventory();
    }

    private void createFakeInventory(){
        String inventoryName; // fake inventory inventoryName
        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName())
            inventoryName = itemStack.getItemMeta().getDisplayName();
        else
            inventoryName = ConfigManager.getDefaultBoxName();
        Inventory inventory = Bukkit.createInventory(null, shulkerBox.getInventory().getSize(), inventoryName);
        inventory.setContents(shulkerBox.getInventory().getContents());
        this.fakeInventory = inventory;
    }

    public void openFakeInventory(){
        player.openInventory(fakeInventory);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Inventory getFakeInventory() {
        return fakeInventory;
    }

    public ShulkerBox getShulkerBox() {
        return shulkerBox;
    }

    public void updateItemContents(){
        BlockStateMeta meta = (BlockStateMeta) getItemStack().getItemMeta();
        getShulkerBox().getInventory().setContents(getFakeInventory().getContents());
        meta.setBlockState(getShulkerBox());
        getItemStack().setItemMeta(meta);
    }

}
