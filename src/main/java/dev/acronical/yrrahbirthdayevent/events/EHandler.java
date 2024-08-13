package dev.acronical.yrrahbirthdayevent.events;

import dev.acronical.yrrahbirthdayevent.YrrahBirthdayEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;

import static dev.acronical.yrrahbirthdayevent.events.impl.BlockBreak.sugarCaneBreakEvent;
import static dev.acronical.yrrahbirthdayevent.events.impl.EggSpawn.spawnEgg;
import static dev.acronical.yrrahbirthdayevent.events.impl.PlayerDeath.givePlayerCake;
import static dev.acronical.yrrahbirthdayevent.events.impl.PlayerDeath.removeItems;
import static dev.acronical.yrrahbirthdayevent.events.impl.PlayerDropItem.playerDropCake;
import static dev.acronical.yrrahbirthdayevent.events.impl.BlockBreak.wheatBreakEvent;

public class EHandler implements Listener {

    File configFile = new File(YrrahBirthdayEvent.getPlugin(YrrahBirthdayEvent.class).getDataFolder(), "config.yml");
    FileConfiguration data = YamlConfiguration.loadConfiguration(configFile);

    public BukkitTask eggSpawnTask = Bukkit.getServer().getScheduler().runTaskTimer(YrrahBirthdayEvent.getPlugin(YrrahBirthdayEvent.class), () -> {
        boolean enabled = data.getBoolean("enabled");
        if (!enabled) return;
        spawnEgg();
    }, 0L, 500L);

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        boolean enabled = data.getBoolean("enabled");
        if (!enabled) return;
        if (e.getEntity().getPlayer() == null) return;
        if (e.getEntity().getKiller() == null) return;
        Player player = e.getEntity().getPlayer();
        Player killer = e.getEntity().getKiller();
        removeItems(player);
        if (player != killer) givePlayerCake(killer);
    }

    @EventHandler
    public void onEggDrop(EntityDropItemEvent e) {
        boolean enabled = data.getBoolean("enabled");
        if (!enabled) return;
        if (e.getEntity().getType() == EntityType.CHICKEN) e.setCancelled(true);
    }

    @EventHandler
    public void onItemDropped(PlayerDropItemEvent e) {
        boolean enabled = data.getBoolean("enabled");
        if (!enabled) return;
        Player player = e.getPlayer();
        Item item = e.getItemDrop();
        Location playerLocation = player.getLocation();
        playerDropCake(player, item, playerLocation);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        boolean enabled = data.getBoolean("enabled");
        if (!enabled) return;
        Block block = e.getBlock();
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        e.setCancelled(true);
        if (e.getBlock().getType() == Material.SUGAR_CANE) sugarCaneBreakEvent(block);
        if (e.getBlock().getType() == Material.WHEAT) wheatBreakEvent(block);
    }
}
