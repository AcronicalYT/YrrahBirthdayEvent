package dev.acronical.yrrahbirthdayevent.commands.impl;

import org.bukkit.entity.Player;

public class StopCommand {
    public static boolean stopCommand(Player player, String label, String[] args) {
        player.sendMessage("Stop!");
        return true;
    }
}
