package yawlick.ru.luckytest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public final class LuckyTest extends JavaPlugin {

    private static LuckyTest instance;

    public ArrayList<Material> materialList = new ArrayList();
    public ArrayList<Player> onlinePlayerList = new ArrayList();
    public ArrayList<UUID> totemsList = new ArrayList();

    @Override
    public void onEnable() {
        instance = this;
        int lucky = ThreadLocalRandom.current().nextInt(1, 101);

        ItemStack sponge = new ItemStack(Material.SPONGE, 1);
        ItemMeta spongeMeta = sponge.getItemMeta();
        spongeMeta.setDisplayName(ChatColor.GRAY + "Обычная губка");
        sponge.setItemMeta(spongeMeta);

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_GRAY + "Ничего необычного...");
        spongeMeta.setLore(lore);

        ItemStack rsponge = new ItemStack(Material.SPONGE, 1);
        ItemMeta rspongeMeta = rsponge.getItemMeta();
        rspongeMeta.setDisplayName(ChatColor.BLUE + "Редкая губка");
        rsponge.setItemMeta(rspongeMeta);

        ArrayList<String> rlore = new ArrayList<>();
        rlore.add(ChatColor.GRAY + "Может выпать редкий лут"); //не может
        rspongeMeta.setLore(rlore);

        ItemStack esponge = new ItemStack(Material.SPONGE, 1);
        ItemMeta espongeMeta = esponge.getItemMeta();
        espongeMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Эпическая губка");
        esponge.setItemMeta(espongeMeta);

        ArrayList<String> elore = new ArrayList<>();
        elore.add(ChatColor.WHITE + "Есть небольшой шанс на эпические вещи!"); //нету
        espongeMeta.setLore(elore);

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
        materialList.add(Material.WOOD);
        materialList.add(Material.STICK);
        materialList.add(Material.DIAMOND);
        materialList.add(Material.DIAMOND_PICKAXE);
        materialList.add(Material.IRON_INGOT);
        materialList.add(Material.GOLDEN_CARROT);
        materialList.add(Material.GOLD_INGOT);
        materialList.add(Material.SPONGE);

    }

    @Override
    public void onDisable() {
    }

    public static LuckyTest getInstance() {
        return instance;
    }
}
