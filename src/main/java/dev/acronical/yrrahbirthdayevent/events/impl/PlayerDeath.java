package dev.acronical.yrrahbirthdayevent.events.impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerDeath {

    public static void givePlayerCake(Player player) {
        if (player == null) return;
        player.getInventory().addItem(new ItemStack(Material.CAKE));
    }

    public static void removeItems(Player player) {
        Inventory playerInv = player.getInventory();
        ItemStack[] contents = playerInv.getContents();

        for (ItemStack itemStack : contents) {
            if (itemStack != null && itemStack.getAmount() > 0) {
                int newAmount = Math.round((float) itemStack.getAmount() / 2);
                itemStack.setAmount(newAmount);
            }
        }
    }
}
