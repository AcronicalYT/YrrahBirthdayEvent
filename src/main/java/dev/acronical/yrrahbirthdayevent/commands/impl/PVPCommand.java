package dev.acronical.yrrahbirthdayevent.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PVPCommand implements TabCompleter {

    private static final String[] COMMANDS = { "on", "off" };

    public static boolean pvpCommand(Player player, String label, String[] args) {
        if (!player.hasPermission("yrrahbirthday.pvptoggle") && !player.isOp()) return false;
        World world = player.getWorld();
        boolean state = world.getPVP();

        if (args.length == 0 && !state) {
            enablePVP(player, world);
        } else if (args.length == 0 && state) {
            disablePVP(player, world);
        } else if (args.length == 1) {
            String setting = args[0].toLowerCase();
            if (setting.equals("on")) enablePVP(player, world);
            else if (setting.equals("off")) disablePVP(player, world);
            else {
                player.sendMessage("Please specify \"on\" or \"off\".");
                return false;
            }
        } else {
            player.sendMessage("An error occurred: Too many arguments provided.");
            return false;
        }
        return true;
    }

    public static void enablePVP(Player player, World world) {
        world.setPVP(true);
        player.sendMessage("PVP has been enabled.");
        Bukkit.broadcastMessage(String.format("§lPvP has been enabled by %s", player.getName()));
    }

    public static void disablePVP(Player player, World world) {
        world.setPVP(false);
        player.sendMessage("PVP has been disabled.");
        Bukkit.broadcastMessage(String.format("§lPvP has been disabled by %s", player.getName()));
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {        //create new array
        final List<String> completions = new ArrayList<>();
        StringUtil.copyPartialMatches(args[0], List.of(COMMANDS), completions);
        return completions;
    }
}
