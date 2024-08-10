package dev.acronical.yrrahbirthdayevent.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static dev.acronical.yrrahbirthdayevent.commands.impl.StartCommand.startCommand;
import static dev.acronical.yrrahbirthdayevent.commands.impl.StopCommand.stopCommand;

public class CHandler implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;

        switch (command.getName()) {
            case "startevent":
                return startCommand(player, label, args);
            case "stopevent":
                return stopCommand(player, label, args);
            default:
                sender.sendMessage("An error occurred: Command does not exist or is not implemented.");
        }

        return false;
    }
}
