package dev.acronical.yrrahbirthdayevent.events.impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class PlayerDeath {

    String[] itemIDs = {
      "cake",
      "sugar",
      "egg",
      "wheat",
      "milk bucket",
    };

    public static void removeItems(Player player) {
        System.out.println("Removing items event!");
        Inventory playerInv = player.getInventory();
        ItemStack[] listOfItems = playerInv.getContents();
        for (ItemStack item : listOfItems) {
            if (item == null) return;
            String itemID = Objects.requireNonNull(item.getItemMeta()).getItemName().toLowerCase();
            System.out.println(itemID);
        }
    }

    public static void givePlayerCake(Player player) {
        if (player == null) return;
        player.getInventory().addItem(new ItemStack(Material.CAKE));
    }

}
