package lol.yakut.playertracker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class TrackerCommand implements CommandExecutor {

    private final PlayerTracker plugin;

    public TrackerCommand(PlayerTracker plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length != 1) {
                openPlayerSelectionMenu(player);
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.RED + "Player not found!");
                return false;
            }

            if (player.equals(target)) {
                player.sendMessage(ChatColor.RED + "You cannot track urself dude");
                return false;
            }

            giveTrackingCompass(player, target);
            return true;
        }
        return false;
    }

    private void openPlayerSelectionMenu(Player player) {
        Inventory menu = Bukkit.createInventory(null, 9, "Select Player");

        for (Player target : Bukkit.getOnlinePlayers()) {
            if (!target.equals(player)) {
                ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                skullMeta.setOwner(target.getName());
                skullMeta.setDisplayName(ChatColor.AQUA + target.getName());

                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.YELLOW + "Click to track " + target.getName());
                skullMeta.setLore(lore);

                skull.setItemMeta(skullMeta);
                menu.addItem(skull);
            }
        }

        player.openInventory(menu);
    }

    public void giveTrackingCompass(Player player, Player target) {
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta meta = compass.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + "Tracking " + ChatColor.AQUA + target.getName());
        compass.setItemMeta(meta);

        player.getInventory().addItem(compass);
        player.setCompassTarget(target.getLocation());

        player.sendMessage(ChatColor.GREEN + "You are now tracking " + ChatColor.YELLOW + target.getName() + ChatColor.GREEN + "!");
    }
}
