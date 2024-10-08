package dev.acronical.yrrahbirthdayevent.commands.impl;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Animals;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Objective;

import java.util.Objects;

public class StartCommand {

    public static boolean startCommand(Player player, String label, String[] args) {
        World world = player.getWorld();
        Location spawn = new Location(world, 245, -45, 12);
        Player[] players = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);
        world.setDifficulty(Difficulty.EASY);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, false);
        world.setGameRule(GameRule.SEND_COMMAND_FEEDBACK, false);
        world.setGameRule(GameRule.KEEP_INVENTORY, true);
        world.setSpawnLocation(spawn);
        if (player.getScoreboard().getObjective("playerScores") != null) player.getScoreboard().getObjective("playerScores").unregister();
        Objective scores = player.getScoreboard().registerNewObjective("playerScores", Criteria.DUMMY, "Player Score");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard objectives setdisplay below_name playerScores");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard objectives setdisplay list playerScores");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard objectives setdisplay sidebar playerScores");
        for (Player target : players) {
            scores.getScore(target).setScore(0);
            if (!target.isOp()) target.setGameMode(GameMode.SURVIVAL);
            target.getInventory().clear();
            target.setHealth(Objects.requireNonNull(target.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue());
            target.setSaturation(20);
            target.setRespawnLocation(spawn, true);
            target.teleport(new Location(world, 72, 8, 90));
            setInventory(target);
            target.sendMessage("The event has started, good luck!");
        }
        spawnAnimals(world, EntityType.CHICKEN, new Location(world, 136, -55, 122));
        spawnAnimals(world, EntityType.COW, new Location(world, 91, -58, 137));
        Bukkit.broadcastMessage("The event has started!");
        return true;
    }

    private static void spawnAnimals(World world, EntityType animal, Location location) {
        for (int i = 0; i < 15; i++) {
            Animals spawnedEntity = (Animals) world.spawnEntity(location, animal);
            spawnedEntity.setCustomName(String.format("Event %s", spawnedEntity.getType().toString().toLowerCase()));
            spawnedEntity.setCustomNameVisible(false);
            spawnedEntity.setInvulnerable(true);
            spawnedEntity.setBreed(false);
            spawnedEntity.setAdult();
        }
    }

    public static void setInventory(Player player) {
        player.getInventory().addItem(createItem(Material.LEATHER_HELMET));
        player.getInventory().addItem(createItem(Material.LEATHER_CHESTPLATE));
        player.getInventory().addItem(createItem(Material.LEATHER_LEGGINGS));
        player.getInventory().addItem(createItem(Material.LEATHER_BOOTS));
        player.getInventory().addItem(createItem(Material.WOODEN_SWORD));
        player.getInventory().addItem(createItem(Material.TRIDENT));
        player.getInventory().addItem(new ItemStack(Material.BREAD, 8));
    }

    private static ItemStack createItem(Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setUnbreakable(true);
        if (material.equals(Material.LEATHER_BOOTS)) {
            itemMeta.addEnchant(Enchantment.FEATHER_FALLING, 4, true);
        } else if (material.equals(Material.TRIDENT)) {
            itemMeta.addEnchant(Enchantment.RIPTIDE, 2, true);
        }
        item.setItemMeta(itemMeta);
        return item;
    }

}
