package dev.acronical.yrrahbirthdayevent.commands.impl;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.Objects;

public class StartCommand {

    public static boolean startCommand(Player player, String label, String[] args) {
        World world = player.getWorld();
        Location spawn = new Location(world, 72, 8, 90);
        Player[] players = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);
        world.setDifficulty(Difficulty.EASY);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setSpawnLocation(spawn);
        for (Player target : players) {
            if (!target.isOp()) target.setGameMode(GameMode.SURVIVAL);
            target.getInventory().clear();
            target.setHealth(Objects.requireNonNull(target.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue());
            target.setSaturation(20);
            target.setRespawnLocation(spawn);
            target.teleport(spawn);
            target.sendMessage("The event has started, good luck!");
        }
        Bukkit.broadcastMessage("The event has started!");
        return true;
    }
}
