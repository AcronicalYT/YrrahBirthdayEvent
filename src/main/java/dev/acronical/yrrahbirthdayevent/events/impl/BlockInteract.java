package dev.acronical.yrrahbirthdayevent.events.impl;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BlockInteract {

    public static void bucketChestOpen(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        if (block == null) return;
        if (block.getType() != Material.CHEST) return;
        Chest chest = (Chest) block.getState();
        if (!chest.getCustomName().equals("Buckets")) return;
        e.setCancelled(true);
        Inventory inventory = Bukkit.createInventory(player, 9, ChatColor.AQUA + "Buckets");
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, new ItemStack(Material.BUCKET, 16));
        }
        player.openInventory(inventory);
    }

}
