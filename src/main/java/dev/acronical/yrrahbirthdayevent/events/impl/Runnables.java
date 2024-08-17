package dev.acronical.yrrahbirthdayevent.events.impl;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;

public class Runnables {

    private static final int[][] sugarCaneLocations = {
            { 123, -59, 102 },
            { 127, -59, 103 },
            { 134, -59, 96 },
            { 33, -56, 157 },
            { 34, -56, 156 },
            { 40, -56, 158 },
            { 41, -56, 161 },
            { 38, -56, 162 },
            { 35, -56, 161 },
            { 34, -56, 160 },
            { 8, -59, 44 },
            { 6, -59, 39 },
            { 5, -59, 32 },
            { 13, -59, 28 },
            { 16, -59, 37 },
            { 16, -59, 46 },
            { 12, -59, 55 },
            { 8, -59, 64 },
            { 0, -59, 78 },
            { 2, -59, 75 },
            { -24, -59, 88 },
            { -24, -59, 86 },
            { -17, -59, 77 },
    };

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

    public static void setSugarCane() {
        if (Bukkit.getServer().getOnlinePlayers().isEmpty()) return;
        World world = Objects.requireNonNull(Bukkit.getServer().getOnlinePlayers().stream().findFirst().orElse(null)).getWorld();
        System.out.println("Setting sugar cane");
        for (int[] sugarCaneLocation : sugarCaneLocations) {
            System.out.println(Arrays.toString(sugarCaneLocation));
            int x = sugarCaneLocation[0];
            System.out.println(x);
            int y = sugarCaneLocation[1];
            System.out.println(y);
            int z = sugarCaneLocation[2];
            System.out.println(z);
            world.getBlockAt(new Location(world, x, y, z)).setType(Material.SUGAR_CANE);
            System.out.println("Set sugar cane");
        }
    }

}
