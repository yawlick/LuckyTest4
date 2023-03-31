package yawlick.ru.luckytest.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class heal implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player plr = (Player) sender;

        plr.sendActionBar(ChatColor.GOLD + "Вы были " + ChatColor.RED + "исцелены" + ChatColor.GOLD + "!");
        plr.setSaturation(20);
        plr.setFoodLevel(20);
        plr.setHealth(20);

    
        return true;
    }
}
