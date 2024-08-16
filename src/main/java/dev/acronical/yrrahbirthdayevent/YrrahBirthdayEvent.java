package dev.acronical.yrrahbirthdayevent;

import dev.acronical.yrrahbirthdayevent.commands.CHandler;
import dev.acronical.yrrahbirthdayevent.commands.impl.PVPCommand;
import dev.acronical.yrrahbirthdayevent.events.EHandler;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;

public final class YrrahBirthdayEvent extends JavaPlugin {

    Logger logger = Logger.getLogger("Yrrah Birthday Event");

    @Override
    public void onEnable() {
        try { versionCheck(); } catch (Exception ignored) { logger.warning("An exception occurred when attempting to grab the latest version..."); }
        try { getServer().getPluginManager().registerEvents(new EHandler(), getPlugin(YrrahBirthdayEvent.class)); } catch (Exception e) { logger.severe("Failed to register the event handler..."); } finally { logger.info("Finished registering events."); }
        try { registerCommands(); } catch (NullPointerException e) { logger.severe("Failed to register one or more commands..."); } finally { logger.info("Finished registering commands."); }
        try { registerTabCompleters(); } catch (NullPointerException e) { logger.severe("Failed to register one or more completers..."); } finally { logger.info("Finished registering completers."); }
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Yrrah Birthday Event] Plugin is enabled!");
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Yrrah Birthday Event] Plugin is disabled!");
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("startevent")).setExecutor(new CHandler());
        Objects.requireNonNull(getCommand("stopevent")).setExecutor(new CHandler());
        Objects.requireNonNull(getCommand("pvp")).setExecutor(new CHandler());
    }

    private void registerTabCompleters() {
        Objects.requireNonNull(getCommand("pvp")).setTabCompleter(new PVPCommand());
    }

    private void versionCheck() throws Exception {
        String latestVersion = getLatestVersion();
        if (latestVersion == null) {
            logger.warning("Unable to check for updates...");
        } else if (getDescription().getVersion().equals(latestVersion)) {
            logger.info("Plugin is up-to-date!");
            logger.info(String.format("Current version: %s", getDescription().getVersion()));
            logger.info(String.format("Latest version: %s", latestVersion));
        } else {
            logger.info("A new version is available!");
            logger.info(String.format("Current version: %s", getDescription().getVersion()));
            logger.info(String.format("Latest version: %s", latestVersion));
            logger.info("Download it from https://acronical.is-a.dev/projects");
        }
    }

    private String getLatestVersion() throws Exception {
        URL url = new URI("https://api.github.com/repos/AcronicalYT/YrrahBirthdayEvent/releases/latest").toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "YrrahBirthdayEvent");
        try {
            connection.connect();
            if (connection.getResponseCode() == 200) {
                Scanner scanner = new Scanner(connection.getInputStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNext()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();
                return response.toString().split("\"tag_name\":\"")[1].split("\"")[0];
            } else {
                throw new RuntimeException("Failed to check for updates. Response code: " + connection.getResponseCode() + " " + connection.getResponseMessage());
            }
        } catch (Exception e) {
            throw new Exception("An error occurred while checking for updates...");
        }
    }

}
