package dev.acronical.yrrahbirthdayevent.events.impl;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import static dev.acronical.yrrahbirthdayevent.commands.CHandler.running;

public class PlayerJoin {

    public static void playerSpawn(Player player) {
        if (running) joinOnEventRunning(player);
        else joinOnEventStopped(player);
    }

    private static void joinOnEventRunning(Player player) {
        World world = player.getWorld();
        player.setGameMode(GameMode.SURVIVAL);
        player.setRespawnLocation(new Location(world, 72, 8, 90), true);
        player.teleport(new Location(world, 245, -45, 12));
    }

    private static void joinOnEventStopped(Player player) {
        World world = player.getWorld();
        Location spawn = new Location(world, 92, 20, 7);
        player.setRespawnLocation(spawn, true);
        player.teleport(spawn);
        player.setGameMode(GameMode.ADVENTURE);
    }

}
