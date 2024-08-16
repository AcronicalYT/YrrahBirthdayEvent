package dev.acronical.yrrahbirthdayevent.events.impl;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class Runnables {

    public static void spawnEgg() {
        if (Bukkit.getServer().getOnlinePlayers().isEmpty()) return;
        World world = Objects.requireNonNull(Bukkit.getServer().getOnlinePlayers().stream().findFirst().orElse(null)).getWorld();
        int mobCount = (int) world.getEntities().stream().filter(entity -> entity.getType() == EntityType.CHICKEN).count();
        for (int i = 0; i < mobCount; i++) {
            Entity chicken = world.getLivingEntities().stream().filter(livingEntity -> livingEntity.getType() == EntityType.CHICKEN).findAny().orElse(null);
            if (chicken == null) return;
            Location chickenLocation = chicken.getLocation();
            world.dropItem(chickenLocation, new ItemStack(Material.EGG));
        }
    }
}
