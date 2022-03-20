package net.andrewcpu.goodshulk;

import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ShulkUnit {
    private Player player;
    private Inventory fakeInventory;
    private ShulkerBox shulkerBox;
    private ItemStack itemStack;

    public ShulkUnit(Player player, Inventory fakeInventory, ShulkerBox shulkerBox, ItemStack itemStack) {
        this.player = player;
        this.fakeInventory = fakeInventory;
        this.shulkerBox = shulkerBox;
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Inventory getFakeInventory() {
        return fakeInventory;
    }

    public void setFakeInventory(Inventory fakeInventory) {
        this.fakeInventory = fakeInventory;
    }

    public ShulkerBox getShulkerBox() {
        return shulkerBox;
    }

    public void setShulkerBox(ShulkerBox shulkerBox) {
        this.shulkerBox = shulkerBox;
    }
}
