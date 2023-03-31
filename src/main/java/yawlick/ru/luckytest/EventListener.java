package yawlick.ru.luckytest;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
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

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Thread.sleep;

public class EventListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {


        Block block = event.getBlock();
        Material material = block.getType();
        Player plr = event.getPlayer();
        Location location = block.getLocation();

        ItemStack RandomItem = getRandomItem();

        int itemValue2 = ThreadLocalRandom.current().nextInt(1, 4);

        Random ran = new Random();
        //int lucky = ThreadLocalRandom.current().nextInt(1, LuckyTest.getInstance().materialList.size() + 1);

        if (material == Material.SPONGE) {
            block.getWorld().getBlockAt(location).setType(Material.AIR);

            for (Material m : LuckyTest.getInstance().materialList) {
                if (ThreadLocalRandom.current().nextInt(1, 12) < 12) {
                    block.getWorld().dropItem(block.getLocation(), getRandomItem());
                    break;
                } else {
                    plr.playSound(plr.getLocation(), Sound.ENTITY_VILLAGER_DEATH, 1, 1);
                    plr.sendActionBar(ChatColor.RED + "Увы, но вам не повезло =(");
                }
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

    private ItemStack getRandomItem() {

        Material chosenMaterial = LuckyTest.getInstance().materialList.get(new Random().nextInt(LuckyTest.getInstance().materialList.size())); // Выбирается рандомный материал

        if (!chosenMaterial.isSolid() || !chosenMaterial.equals(Material.AIR)) { // Проверка

            ItemStack luckyItems = new ItemStack(chosenMaterial);

            return luckyItems;
        }

        return getRandomItem();
    }
}
