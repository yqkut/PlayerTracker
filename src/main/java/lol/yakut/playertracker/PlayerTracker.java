package lol.yakut.playertracker;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerTracker extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("PlayerTracker enabled!");
        getCommand("track").setExecutor(new TrackerCommand(this));
        getServer().getPluginManager().registerEvents(new TrackerListener(this), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("PlayerTracker disabled!");
    }
}
