package dev.acronical.yrrahbirthdayevent;

import dev.acronical.yrrahbirthdayevent.commands.CommandHandler;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class YrrahBirthdayEvent extends JavaPlugin {

    Logger logger = Logger.getLogger("Yrrah Birthday Event");

    @Override
    public void onEnable() {
        try { registerCommands(); } catch(Exception e) { logger.severe("Failed to register commands..."); } finally { logger.info("Finished registering commands."); }
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Yrrah Birthday Event] Plugin is enabled!");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Yrrah Birthday Event] Plugin is disabled!");
    }

    public void registerCommands() {
        getCommand("start").setExecutor(new CommandHandler());
        getCommand("stop").setExecutor(new CommandHandler());
    }
}
