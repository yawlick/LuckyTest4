package yawlick.ru.luckytest.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.bukkit.ChatColor.LIGHT_PURPLE;

public class sponge implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player plr = (Player) sender;

        ItemStack sponge = new ItemStack(Material.SPONGE, 64);
        ItemMeta spongeMeta = sponge.getItemMeta();
        spongeMeta.setDisplayName(ChatColor.GRAY + "Обычная губка");
        sponge.setItemMeta(spongeMeta);

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_GRAY + "Ничего необычного...");
        spongeMeta.setLore(lore);

        ItemStack rsponge = new ItemStack(Material.SPONGE, 4);
        ItemMeta rspongeMeta = rsponge.getItemMeta();
        rspongeMeta.setDisplayName(ChatColor.BLUE + "Редкая губка");
        rsponge.setItemMeta(rspongeMeta);

        ArrayList<String> rlore = new ArrayList<>();
        rlore.add(ChatColor.GRAY + "Может выпать редкий лут"); //не может
        rspongeMeta.setLore(rlore);

        ItemStack esponge = new ItemStack(Material.SPONGE, 64);
        ItemMeta espongeMeta = esponge.getItemMeta();
        espongeMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Эпическая губка");
        esponge.setItemMeta(espongeMeta);

        ArrayList<String> elore = new ArrayList<>();
        elore.add(ChatColor.WHITE + "Есть небольшой шанс на эпические вещи!"); //нету
        espongeMeta.setLore(elore);

        if(args.length == 0) {
            plr.sendActionBar(ChatColor.RED + "Вы не указали редкость лакиблока!");
            plr.playSound(plr.getLocation(), Sound.ENTITY_VILLAGER_DEATH, 1, 1);
            return true;
        }

        if(args[0].equalsIgnoreCase("default")) {
            plr.getInventory().addItem(sponge);
            return true;
        }

        if(args[0].equalsIgnoreCase("rare")) {
            plr.getInventory().addItem(rsponge);
            return true;
        }

        if(args[0].equalsIgnoreCase("epic")) {
            plr.getInventory().addItem(esponge);
            return true;
        }

        return true;
    }
}
