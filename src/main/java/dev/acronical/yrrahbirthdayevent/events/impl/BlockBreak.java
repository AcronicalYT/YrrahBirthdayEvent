package dev.acronical.yrrahbirthdayevent.events.impl;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class BlockBreak {

    private static final Random random = new Random();

    public static void wheatBreakEvent(Block block) {
        Ageable age = (Ageable) block.getBlockData();
        Location blockLocation = block.getLocation();
        World world = blockLocation.getWorld();
        if (world == null) return;
        if (age.getAge() < age.getMaximumAge()) return;
        world.dropItemNaturally(blockLocation, new ItemStack(Material.WHEAT, random.nextInt(1, 6)));
        block.setType(Material.WHEAT);
    }

    public static void sugarCaneBreakEvent(Block block) {
        Location blockLocation = block.getLocation();
        World world = blockLocation.getWorld();
        if (world == null) return;
        world.dropItemNaturally(blockLocation, new ItemStack(Material.SUGAR, random.nextInt(1, 4)));
    }

}
