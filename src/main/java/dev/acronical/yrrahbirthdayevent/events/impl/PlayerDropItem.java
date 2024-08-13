package dev.acronical.yrrahbirthdayevent.events.impl;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;

public class PlayerDropItem {

    public static void playerDropCake(Player player, Item item, Location playerLocation) {
        if (!item.getItemStack().getType().equals(Material.CAKE)) return;
        if (playerLocation.getY() < 1 && playerLocation.subtract(0, 1, 0).getBlock().getType() != Material.DIAMOND_BLOCK) return;
        Objective scores = player.getScoreboard().getObjective("playerScores");
        if (scores == null) return;
        int scoreVal = scores.getScore(player.getName()).getScore();
        int newScoreVal = scoreVal + 1;
        scores.getScore(player.getName()).setScore(newScoreVal);
        player.sendMessage("You scored a point!");
        player.getServer().broadcastMessage(String.format("%s has scored a point!", player.getName()));
        item.remove();
    }

}
