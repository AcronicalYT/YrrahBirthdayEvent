package dev.acronical.yrrahbirthdayevent.events;

import dev.acronical.yrrahbirthdayevent.YrrahBirthdayEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitTask;

import static dev.acronical.yrrahbirthdayevent.events.impl.EggSpawn.spawnEgg;
import static dev.acronical.yrrahbirthdayevent.events.impl.HelloPlayer.helloPlayerEvent;
import static dev.acronical.yrrahbirthdayevent.events.impl.PlayerDeath.givePlayerCake;
import static dev.acronical.yrrahbirthdayevent.events.impl.PlayerDeath.removeItems;
import static dev.acronical.yrrahbirthdayevent.events.impl.PlayerDropItem.playerDropCake;

public class EHandler implements Listener {

    public BukkitTask eggSpawnTask = Bukkit.getServer().getScheduler().runTaskTimer(YrrahBirthdayEvent.getPlugin(YrrahBirthdayEvent.class), () -> spawnEgg(), 0L, 500L);

    @EventHandler
    public void testEvent(PlayerJoinEvent e) {
        helloPlayerEvent(e.getPlayer());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        System.out.println("Player died!");
        if (e.getEntity().getPlayer() == null) return;
        if (e.getEntity().getKiller() == null) return;
        Player player = e.getEntity().getPlayer();
        Player killer = e.getEntity().getKiller();
        removeItems(player);
        givePlayerCake(killer);
    }

    @EventHandler
    public void onEggDrop(EntityDropItemEvent e) {
        if (e.getEntity().getType() == EntityType.CHICKEN) e.setCancelled(true);
    }

    @EventHandler
    public void onItemDropped(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        Item item = e.getItemDrop();
        Location playerLocation = player.getLocation();
        playerDropCake(player, item, playerLocation);
    }
}
