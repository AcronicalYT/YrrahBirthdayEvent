package dev.acronical.yrrahbirthdayevent.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static dev.acronical.yrrahbirthdayevent.commands.impl.PVPCommand.pvpCommand;
import static dev.acronical.yrrahbirthdayevent.commands.impl.StartCommand.startCommand;
import static dev.acronical.yrrahbirthdayevent.commands.impl.StopCommand.stopCommand;

public class CHandler implements CommandExecutor {

    public static boolean running = false;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            Bukkit.getConsoleSender().sendMessage("You must be logged in as a player to run these commands!");
            return false;
        }

        switch (command.getName().toLowerCase()) {
            case "startevent":
                if (running) {
                    player.sendMessage("Plugin is already enabled!");
                    return true;
                } else {
                    running = true;
                }
                return startCommand(player, label, args);
            case "stopevent":
                if (!running) {
                    player.sendMessage("Plugin is already disabled!");
                    return true;
                } else {
                    running = false;
                }
                return stopCommand(player, label, args);
            case "pvp":
                return pvpCommand(player, label, args);
            default:
                sender.sendMessage("An error occurred: Command does not exist or is not implemented.");
        }
        return false;
    }
}
