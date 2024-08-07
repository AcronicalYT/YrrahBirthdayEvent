package dev.acronical.yrrahbirthdayevent.commands.impl;

import org.bukkit.entity.Player;

public class StartCommand {
    public static boolean startCommand(Player player, String label, String[] args) {
        player.sendMessage("Start!");
        return true;
    }
}
