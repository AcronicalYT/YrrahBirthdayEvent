package dev.acronical.yrrahbirthdayevent.commands.impl;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.Objects;

public class StopCommand {

    public static boolean stopCommand(Player player, String label, String[] args) {
        World world = player.getWorld();
        Location spawn = new Location(world, 98, 100, 6);
        Player[] players = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);
        world.setDifficulty(Difficulty.PEACEFUL);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setSpawnLocation(spawn);
        world.setPVP(false);
        for (Player target : players) {
            if (!target.isOp()) target.setGameMode(GameMode.ADVENTURE);
            target.getInventory().clear();
            target.setHealth(Objects.requireNonNull(target.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue());
            target.setSaturation(20);
            target.setRespawnLocation(spawn);
            target.teleport(spawn);
        }
        Bukkit.broadcastMessage("The event has ended!");
        return true;
    }
}
