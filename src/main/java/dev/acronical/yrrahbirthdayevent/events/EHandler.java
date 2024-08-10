package dev.acronical.yrrahbirthdayevent.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import static dev.acronical.yrrahbirthdayevent.events.impl.HelloPlayer.helloPlayerEvent;
import static dev.acronical.yrrahbirthdayevent.events.impl.PlayerDeath.givePlayerCake;
import static dev.acronical.yrrahbirthdayevent.events.impl.PlayerDeath.removeItems;

public class EHandler implements Listener {

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

}
