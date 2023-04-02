package yawlick.ru.luckytest;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import javax.print.DocFlavor;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Thread.sleep;

public class EventListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {


        Block block = event.getBlock();
        Material material = block.getType();
        Player plr = event.getPlayer();
        Location location = block.getLocation();

        if (material == Material.SPONGE) {
            if (LuckyTest.getInstance().luckyblocks.containsKey(event.getBlock().getLocation())) {
                ItemStack item = getRandomItem(LuckyTest.getInstance().luckyblocks.get(event.getBlock().getLocation()));

                event.setCancelled(true);
                event.getBlock().setType(Material.AIR);
                event.getBlock().getLocation().getWorld().dropItem(event.getBlock().getLocation(), item);
                LuckyTest.getInstance().luckyblocks.remove(location);
            }
        }

        if (material == Material.BREWING_STAND) {
            plr.playSound(plr.getLocation(), Sound.ENTITY_VILLAGER_DEATH, 1, 1);
            if (LuckyTest.getInstance().totemsList.size() == 0) {
                plr.sendActionBar(ChatColor.GOLD + "но у тебя же нет приватов...");
            } else {
                for (UUID stand : LuckyTest.getInstance().totemsList) {
                    LuckyTest.getInstance().totemsList.remove(stand);
                    block.getWorld().getEntity(stand).remove();
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player plr = event.getPlayer();
        Block b = event.getBlock();
        Location loc = event.getBlock().getLocation();
        if (b.getType() == Material.BREWING_STAND) {
            plr.playSound(plr.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            ArmorStand stand = (ArmorStand) loc.getWorld().spawn(loc.add(0.500,0.7,0.500), ArmorStand.class);
            stand.setCanMove(false);
            stand.setCollidable(false);
            stand.setCustomName(ChatColor.DARK_GREEN + "Тотем игрока: " + ChatColor.YELLOW + plr.getName());
            stand.setCustomNameVisible(true);
            stand.setVisible(false);
            stand.setMarker(true);
            LuckyTest.getInstance().totemsList.add(stand.getUniqueId());
        }

        if (b.getType() == Material.SPONGE) {
            NBTItem itemNBT = new NBTItem(event.getItemInHand());
            String type = itemNBT.getString("type");
            if(type == null) return;
            LBType luckyblock = LBType.valueOf(type);
            LuckyTest.getInstance().luckyblocks.put(event.getBlock().getLocation(), luckyblock);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player plr = event.getPlayer();
        LuckyTest.getInstance().onlinePlayerList.add(plr);
        if (plr.getName() == "yawlick") {
            plr.sendMessage("Да, ты " + plr.getName());
            plr.setCustomName(ChatColor.BLUE + "DEV" + ChatColor.DARK_GRAY + " | " + ChatColor.YELLOW + "YawFal228" + ChatColor.GREEN + " » ");
            plr.setDisplayName(ChatColor.BLUE + "DEV" + ChatColor.DARK_GRAY + " | " + ChatColor.YELLOW + "YawFal228" + ChatColor.GREEN + " » ");
            plr.setCustomNameVisible(true);
        } else {
            plr.sendMessage("Нет, ты " + plr.getName());
            plr.setCustomName(ChatColor.BLUE + "DEV" + ChatColor.DARK_GRAY + " | " + ChatColor.YELLOW + plr.getName() + ChatColor.GREEN + " » ");
            plr.setDisplayName(ChatColor.BLUE + "DEV" + ChatColor.DARK_GRAY + " | " + ChatColor.YELLOW + plr.getName() + ChatColor.GREEN + " » ");
            plr.setCustomNameVisible(true);
        }
    }

    @EventHandler
    public void onChatSendMessage(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        for(Player plr1 : LuckyTest.getInstance().onlinePlayerList) {
            plr1.sendMessage(event.getPlayer().getCustomName() + event.getMessage());
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player plr = event.getPlayer();
        LuckyTest.getInstance().onlinePlayerList.remove(plr);
    }

    private ItemStack getRandomItem(LBType type) {
        return type.getDrop().get(LuckyTest.getInstance().random.nextInt(type.getDrop().size()));
    }
}
