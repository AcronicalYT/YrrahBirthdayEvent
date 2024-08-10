package dev.acronical.yrrahbirthdayevent.events.impl;

import org.bukkit.entity.Player;

public class HelloPlayer {

    public static void helloPlayerEvent(Player p) {
        p.sendMessage(String.format("Hello %s!", p.getName()));
    }

}
