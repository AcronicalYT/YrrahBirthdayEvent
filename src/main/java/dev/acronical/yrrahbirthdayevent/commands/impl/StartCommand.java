package dev.acronical.yrrahbirthdayevent.commands.impl;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Objective;

import java.util.Objects;

public class StartCommand {

    public static boolean startCommand(Player player, String label, String[] args) {
        World world = player.getWorld();
        Location spawn = new Location(world, 72, 8, 90);
        Player[] players = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);
        if (player.getScoreboard().getObjective("playerScores") != null) player.getScoreboard().getObjective("playerScores").unregister();
        Objective scores = player.getScoreboard().registerNewObjective("playerScores", Criteria.DUMMY, "Player Score");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard objectives setdisplay below_name playerScores");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard objectives setdisplay list playerScores");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard objectives setdisplay sidebar playerScores");
        world.setDifficulty(Difficulty.EASY);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setSpawnLocation(spawn);
        for (Player target : players) {
            scores.getScore(target).setScore(0);
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
