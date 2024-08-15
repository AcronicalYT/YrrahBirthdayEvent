package dev.acronical.yrrahbirthdayevent.commands.impl;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StopCommand {

    private static int highestScore = 0;
    private static final List<Player> winners = new ArrayList<>();

    public static boolean stopCommand(Player player, String label, String[] args) {
        World world = player.getWorld();
        Location spawn = new Location(world, 98, 100, 6);
        Player[] players = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);
        Objective scores = player.getScoreboard().getObjective("playerScores");
        if (scores == null) player.sendMessage("Scores don't exist, was the event started properly?");
        world.setDifficulty(Difficulty.PEACEFUL);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setSpawnLocation(spawn);
        world.setPVP(false);
        for (Player target : players) {
            int targetScore = scores.getScore(target).getScore();
            if (highestScore < targetScore) highestScore = targetScore;
            else if (highestScore == targetScore) winners.add(target);
            if (!target.isOp()) target.setGameMode(GameMode.ADVENTURE);
            target.getInventory().clear();
            target.setHealth(Objects.requireNonNull(target.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue());
            target.setSaturation(20);
            target.setRespawnLocation(spawn);
            target.teleport(spawn);
        }
        if (winners.size() > 1) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a actionbar {\"bold\":true,\"text\":\"Congratulations\"}");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format("title @a subtitle {\"bold\":true,\"text\":\"They had %s points!\"}", winners.get(0).getScoreboard().getObjective("playerScores").getScore(winners.get(0))));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title {\"bold\":true,\"text\":\"The event was a draw!\"}");
        } else if (winners.size() == 1) {
            Player winner = winners.get(0);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a actionbar {\"bold\":true,\"text\":\"Congratulations\"}");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format("title @a subtitle {\"bold\":true,\"text\":\"They had %s points!\"}", winner.getScoreboard().getObjective("playerScores").getScore(winner)));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format("title @a title {\"bold\":true,\"text\":\"The event has been won by %s!\"}", winner));
        }
        Objects.requireNonNull(player.getScoreboard().getObjective(scores.getName())).unregister();
        Bukkit.broadcastMessage("The event has ended!");
        return true;
    }
}
