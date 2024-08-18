package dev.acronical.yrrahbirthdayevent.events.impl;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Objective;

import java.util.Objects;

import static dev.acronical.yrrahbirthdayevent.commands.CHandler.running;
import static dev.acronical.yrrahbirthdayevent.commands.impl.StartCommand.setInventory;

public class PlayerJoin {

    public static void playerSpawn(Player player, String cause) {
        if (running) joinOnEventRunning(player, cause);
        else joinOnEventStopped(player);
    }

    private static void joinOnEventRunning(Player player, String cause) {
        World world = player.getWorld();
        Objective scores = player.getScoreboard().getObjective("playerScores");
        if (scores != null && !(scores.getScore(player).getScore() >= 0)) {
            scores.getScore(player).setScore(0);
        }
        if (player.getInventory().isEmpty()) setInventory(player);
        player.setGameMode(GameMode.SURVIVAL);
        player.setRespawnLocation(new Location(world, 245, -45, 12), true);
        if (cause.equalsIgnoreCase("init")) player.teleport(new Location(world, 72, 8, 90));
        else if (cause.equalsIgnoreCase("death")) player.teleport(new Location(world, 245, -45, 12));
        else player.teleport(Objects.requireNonNull(player.getRespawnLocation()));
    }

    private static void joinOnEventStopped(Player player) {
        World world = player.getWorld();
        Location spawn = new Location(world, 92, 20, 7);
        player.setRespawnLocation(spawn, true);
        player.teleport(spawn);
        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().clear();
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue());
        player.setSaturation(20);
    }

}
