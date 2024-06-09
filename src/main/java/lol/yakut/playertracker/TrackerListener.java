package lol.yakut.playertracker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class TrackerListener implements Listener {

    private final PlayerTracker plugin;

    public TrackerListener(PlayerTracker plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Select Player")) {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if (item == null || item.getType() != Material.SKULL_ITEM || item.getDurability() != 3) {
                return;
            }

            SkullMeta meta = (SkullMeta) item.getItemMeta();
            String targetName = meta.getOwner();
            Player player = (Player) event.getWhoClicked();
            Player target = Bukkit.getPlayer(targetName);

            if (target == null) {
                player.sendMessage(ChatColor.RED + "Player not found!");
                return;
            }

            if (player.equals(target)) {
                player.sendMessage(ChatColor.RED + "You cannot track urself dude!");
                return;
            }

            plugin.getCommand("track").getExecutor().onCommand(player, null, "track", new String[]{targetName});
            player.closeInventory();
        }
    }
}
