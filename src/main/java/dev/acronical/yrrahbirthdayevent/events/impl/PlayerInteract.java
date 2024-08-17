package dev.acronical.yrrahbirthdayevent.events.impl;

import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class PlayerInteract {

    public static void playerThrowEgg(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (Objects.requireNonNull(e.getPlayer().getItemInUse()).getType() != Material.EGG) return;
        else e.setCancelled(true);
    }

}
