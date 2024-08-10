package dev.acronical.yrrahbirthdayevent.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static dev.acronical.yrrahbirthdayevent.events.impl.HelloPlayer.helloPlayerEvent;

public class EHandler implements Listener {

    @EventHandler
    public void testEvent(PlayerJoinEvent e) {
        helloPlayerEvent(e.getPlayer());
    }

}
