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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import static dev.acronical.yrrahbirthdayevent.commands.CHandler.running;
import static dev.acronical.yrrahbirthdayevent.events.impl.BlockBreak.sugarCaneBreakEvent;
import static dev.acronical.yrrahbirthdayevent.events.impl.BlockInteract.bonemealChestOpen;
import static dev.acronical.yrrahbirthdayevent.events.impl.BlockInteract.bucketChestOpen;
import static dev.acronical.yrrahbirthdayevent.events.impl.Runnables.setSugarCane;
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
    }, 0L, 350L);

    public BukkitTask sugarCaneSpawnTask = Bukkit.getServer().getScheduler().runTaskTimer(YrrahBirthdayEvent.getPlugin(YrrahBirthdayEvent.class), () -> {
        if (!running) return;
        setSugarCane();
    }, 0, 100L);

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!running && e.getPlayer().getScoreboard().getObjective("playerScores") != null) {
            running = true;
        }
        if (e.getPlayer().isOp()) return;
        playerSpawn(e.getPlayer(), "init");
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        playerSpawn(e.getPlayer(), "death");
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
    public void onPlayerDamaged(EntityDamageByEntityEvent e) {
        if (!running) return;
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Player damager)) return;
        if (damager.isOp()) return;
        if (damager.getItemInUse() == null) return;
        if (damager.getItemInUse().getType() == Material.TRIDENT) e.setCancelled(true);
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
        Player player = e.getEntity().getPlayer();
        removeItems(player);
        if (e.getEntity().getKiller() == null) return;
        Player killer = e.getEntity().getKiller();
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
        if (item.getItemStack().getType() != Material.CAKE) {
            e.setCancelled(true);
            return;
        }
        playerDropCake(player, item, playerLocation);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!running) return;
        if (e.getPlayer().isOp()) return;
        Block block = e.getBlock();
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        e.setDropItems(false);
        if (e.getBlock().getType() == Material.SUGAR_CANE) {
            sugarCaneBreakEvent(block);
            return;
        }
        e.setCancelled(true);
        if (e.getBlock().getType() == Material.WHEAT) wheatBreakEvent(block);
    }

    @EventHandler
    public void onPlayerThrowEgg(PlayerEggThrowEvent e) {
        if (!running) return;
        if (e.getPlayer().isOp()) return;
        e.setHatching(false);
        e.getPlayer().getInventory().addItem(new ItemStack(Material.EGG));
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent e) {
        if (!running) return;
        bucketChestOpen(e);
        bonemealChestOpen(e);
    }

    @EventHandler
    public void onPlayerDepositItem(InventoryClickEvent e) {
        if (!running) return;
        if (e.getCurrentItem() == null) return;
        if (e.getInventory().getSize() == 9 && (e.getCurrentItem().getType() != Material.BONE_MEAL && e.getCurrentItem().getType() != Material.BUCKET)) e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerClickCake(PlayerInteractEvent e) {
        if (!running) return;
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getClickedBlock() == null) return;
        if (e.getClickedBlock().getType() != Material.CAKE) return;
        e.setCancelled(true);
    }

}
