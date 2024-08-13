package dev.acronical.yrrahbirthdayevent.events.impl;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import static dev.acronical.yrrahbirthdayevent.commands.CHandler.running;

public class PlayerJoin {

    public static void playerJoin(Player player) {
        if (running) joinOnEventRunning(player);
        else joinOnEventStopped(player);
    }

    private static void joinOnEventRunning(Player player) {
        World world = player.getWorld();
        Location spawn = new Location(world, 72, 8, 90);
        player.setGameMode(GameMode.SURVIVAL);
        player.setRespawnLocation(spawn);
        player.teleport(spawn);
    }

    private static void joinOnEventStopped(Player player) {
        World world = player.getWorld();
        Location spawn = new Location(world, 98, 100, 6);
        player.setRespawnLocation(spawn);
        player.teleport(spawn);
        player.setGameMode(GameMode.ADVENTURE);
    }

}
