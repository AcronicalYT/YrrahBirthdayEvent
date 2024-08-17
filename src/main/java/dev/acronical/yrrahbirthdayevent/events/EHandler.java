package dev.acronical.yrrahbirthdayevent.events;

import dev.acronical.yrrahbirthdayevent.YrrahBirthdayEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitTask;

import static dev.acronical.yrrahbirthdayevent.commands.CHandler.running;
import static dev.acronical.yrrahbirthdayevent.events.impl.BlockBreak.sugarCaneBreakEvent;
import static dev.acronical.yrrahbirthdayevent.events.impl.BlockInteract.bucketChestOpen;
import static dev.acronical.yrrahbirthdayevent.events.impl.Runnables.spawnEgg;
import static dev.acronical.yrrahbirthdayevent.events.impl.PlayerDeath.givePlayerCake;
import static dev.acronical.yrrahbirthdayevent.events.impl.PlayerDeath.removeItems;
import static dev.acronical.yrrahbirthdayevent.events.impl.PlayerDropItem.playerDropCake;
import static dev.acronical.yrrahbirthdayevent.events.impl.BlockBreak.wheatBreakEvent;
import static dev.acronical.yrrahbirthdayevent.events.impl.PlayerJoin.playerSpawn;

public class EHandler implements Listener {

    public BukkitTask eggSpawnTask = Bukkit.getServer().getScheduler().runTaskTimer(YrrahBirthdayEvent.getPlugin(YrrahBirthdayEvent.class), () -> {
        if (!running) return;
        spawnEgg();
    }, 0L, 250L);

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!running && e.getPlayer().getScoreboard().getObjective("playerScores") != null) {
            running = true;
        }
        if (e.getPlayer().isOp()) return;
        playerSpawn(e.getPlayer());
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        playerSpawn(e.getPlayer());
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.getPlayer().isOp()) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onEntityBreed(EntityBreedEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onFarmlandJump(PlayerInteractEvent e) {
        if (!running) return;
        if (e.getAction() != Action.PHYSICAL) return;
        Block block = e.getClickedBlock();
        if (block == null) return;
        if (block.getType() == Material.FARMLAND) e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerCraft(CraftItemEvent e) {
        if (e.getRecipe().getResult().getType() != Material.CAKE && e.getRecipe().getResult().getType() == Material.BREAD) e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (!running) return;
        if (e.getEntity().getPlayer() == null) return;
        if (e.getEntity().getKiller() == null) return;
        Player player = e.getEntity().getPlayer();
        Player killer = e.getEntity().getKiller();
        removeItems(player);
        if (player != killer) givePlayerCake(killer);
    }

    @EventHandler
    public void onEggDrop(EntityDropItemEvent e) {
        if (!running) return;
        if (e.getEntity().getType() == EntityType.CHICKEN) e.setCancelled(true);
        if (e.getEntity().getType() == EntityType.COW) e.setCancelled(true);
    }

    @EventHandler
    public void onItemDropped(PlayerDropItemEvent e) {
        if (!running) return;
        Player player = e.getPlayer();
        Item item = e.getItemDrop();
        Location playerLocation = player.getLocation();
        playerDropCake(player, item, playerLocation);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!running) return;
        if (e.getPlayer().isOp()) return;
        Block block = e.getBlock();
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        e.setCancelled(true);
        if (e.getBlock().getType() == Material.SUGAR_CANE) sugarCaneBreakEvent(block);
        if (e.getBlock().getType() == Material.WHEAT) wheatBreakEvent(block);
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent e) {
        bucketChestOpen(e);
    }
}
