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
import org.bukkit.inventory.meta.ItemMeta;

public class BlockInteract {

    public static void bucketChestOpen(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        if (block == null) return;
        if (block.getType() != Material.CHEST) return;
        Chest chest = (Chest) block.getState();
        if (chest.getCustomName() == null) return;
        if (!chest.getCustomName().equals("Buckets")) return;
        e.setCancelled(true);
        Inventory inventory = Bukkit.createInventory(player, 9, ChatColor.AQUA + "Buckets");
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, new ItemStack(Material.BUCKET));
        }
        player.openInventory(inventory);
    }

    public static void bonemealChestOpen(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        if (block == null) return;
        if (block.getType() != Material.CHEST) return;
        Chest chest = (Chest) block.getState();
        if (chest.getCustomName() == null) return;
        if (!chest.getCustomName().equals("Bonemeal")) return;
        e.setCancelled(true);
        ItemStack blocked = new ItemStack(Material.BARRIER);
        ItemMeta blockedMeta = blocked.getItemMeta();
        assert blockedMeta != null;
        blockedMeta.setDisplayName(ChatColor.RED + "Slot Blocked");
        blocked.setItemMeta(blockedMeta);
        Inventory inventory = Bukkit.createInventory(player, 9, ChatColor.AQUA + "Bonemeal");
        for (int i = 0; i < 9; i++) {
            if (i == 1 || i == 3 || i == 5 || i == 7) inventory.setItem(i, blocked);
            else inventory.setItem(i, new ItemStack(Material.BONE_MEAL, 2));
        }
        player.openInventory(inventory);
    }

}
