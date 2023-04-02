package yawlick.ru.luckytest;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import yawlick.ru.luckytest.command.gm;
import yawlick.ru.luckytest.command.heal;
import yawlick.ru.luckytest.command.sponge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public final class LuckyTest extends JavaPlugin {

    public Random random = new Random();

    private static LuckyTest instance;

    public ArrayList<Material> materialList = new ArrayList();
    public ArrayList<Player> onlinePlayerList = new ArrayList();
    public ArrayList<UUID> totemsList = new ArrayList();

    public HashMap<Location, LBType> luckyblocks = new HashMap<>();


    @Override
    public void onEnable() {
        instance = this;

        ItemStack sponge = new ItemStack(Material.SPONGE, 1);
        ItemMeta spongeMeta = sponge.getItemMeta();
        spongeMeta.setDisplayName(ChatColor.GRAY + "Обычная губка");
        sponge.setItemMeta(spongeMeta);
        NBTItem spongeCommonNBT = new NBTItem(sponge);
        spongeCommonNBT.setString("type", LBType.COMMMON.name());
        sponge = spongeCommonNBT.getItem();

        ItemStack rsponge = new ItemStack(Material.SPONGE, 1);
        ItemMeta rspongeMeta = rsponge.getItemMeta();
        rspongeMeta.setDisplayName(ChatColor.BLUE + "Редкая губка");
        rsponge.setItemMeta(rspongeMeta);
        NBTItem rspongeCommonNBT = new NBTItem(rsponge);
        rspongeCommonNBT.setString("type", LBType.RARE.name());
        rsponge = rspongeCommonNBT.getItem();

        ItemStack esponge = new ItemStack(Material.SPONGE, 1);
        ItemMeta espongeMeta = esponge.getItemMeta();
        espongeMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Эпическая губка");
        esponge.setItemMeta(espongeMeta);
        NBTItem espongeCommonNBT = new NBTItem(esponge);
        espongeCommonNBT.setString("type", LBType.EPIC.name());
        esponge = espongeCommonNBT.getItem();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                for (Player plr1 : onlinePlayerList) {
                    if (ThreadLocalRandom.current().nextInt(1, 101) < 6) { // 100: 5% epic; 15% rare; 80% default
                        plr1.getInventory().addItem(esponge);
                        plr1.sendActionBar(ChatColor.LIGHT_PURPLE + "Вам очень повезло! Вы получили эпический лакиблок.");
                    } else if (ThreadLocalRandom.current().nextInt(1, 101) < 21) {
                        if(ThreadLocalRandom.current().nextInt(1, 101) > 5) {
                            plr1.getInventory().addItem(rsponge);
                        } else {
                            plr1.getInventory().addItem(esponge);
                        }
                    } else if (ThreadLocalRandom.current().nextInt(1, 101) > 20) {
                        plr1.getInventory().addItem(sponge);
                    }
                }
            }
        }, 40, 120);

        // КОММАНД ЭКЗЕКУТЕРЫ
        getCommand("heal").setExecutor(new heal());
        getCommand("gm").setExecutor(new gm());
        getCommand("sponge").setExecutor(new sponge());

        getServer().getPluginManager().registerEvents(new EventListener(), this);

        // АРРАЙ ЛИСТЫ
        materialList.add(Material.APPLE);
        materialList.add(Material.LOG);
        materialList.add(Material.STICK);
        materialList.add(Material.DIAMOND_ORE);
        materialList.add(Material.IRON_ORE);
        materialList.add(Material.BREAD);
        materialList.add(Material.GOLD_ORE);
        materialList.add(Material.COAL);
        materialList.add(Material.COBBLESTONE);
        materialList.add(Material.BOOK);
        materialList.add(Material.OBSIDIAN);
        materialList.add(Material.EXP_BOTTLE);
        materialList.add(Material.DIRT);
        materialList.add(Material.GLASS);
        materialList.add(Material.BLAZE_POWDER);
        materialList.add(Material.NETHER_WARTS);
        materialList.add(Material.WATER_BUCKET);
        materialList.add(Material.LAVA_BUCKET);

    }

    @Override
    public void onDisable() {
    }

    public static LuckyTest getInstance() {
        return instance;
    }
}
