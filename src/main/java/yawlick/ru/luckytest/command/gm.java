package yawlick.ru.luckytest.command;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gm implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player plr = (Player) sender;

        if(args.length == 0) {
            plr.sendActionBar(ChatColor.RED + "Вы не указали аргумент! (0, 1, 2, 3)");
            plr.playSound(plr.getLocation(), Sound.ENTITY_VILLAGER_DEATH, 1, 1);
            return true;
        }
        // Команда {
        if(args[0].equalsIgnoreCase("0")) {
            if(plr.getGameMode() == GameMode.SURVIVAL) {
                sender.sendMessage(ChatColor.RED + "Вы уже находитесь в этом режиме!");
                return true;
            }

            plr.setGameMode(GameMode.SURVIVAL);
            sender.sendMessage(ChatColor.GOLD + "Вы успешно сменили свой режим на " + ChatColor.YELLOW + "Выживание");
            return true;
        }

        if(args[0].equalsIgnoreCase("1")) {
            if(plr.getGameMode() == GameMode.CREATIVE) {
                sender.sendMessage(ChatColor.RED + "Вы уже находитесь в этом режиме!");
                return true;
            }

            plr.setGameMode(GameMode.CREATIVE);
            sender.sendMessage(ChatColor.GOLD + "Вы успешно сменили свой режим на " + ChatColor.YELLOW + "Креатив");
            return true;
        }

        if(args[0].equalsIgnoreCase("2")) {
            if(plr.getGameMode() == GameMode.ADVENTURE) {
                sender.sendMessage(ChatColor.RED + "Вы уже находитесь в этом режиме!");
                return true;
            }

            plr.setGameMode(GameMode.ADVENTURE);
            sender.sendMessage(ChatColor.GOLD + "Вы успешно сменили свой режим на " + ChatColor.YELLOW + "Адвенчур");
            return true;
        }

        if(args[0].equalsIgnoreCase("3")) {
            if(plr.getGameMode() == GameMode.SPECTATOR) {
                sender.sendMessage(ChatColor.RED + "Вы уже находитесь в этом режиме!");
                return true;
            }

            plr.setGameMode(GameMode.SPECTATOR);
            sender.sendMessage(ChatColor.GOLD + "Вы успешно сменили свой режим на " + ChatColor.YELLOW + "Спектатор");
            return true;
        }
        return true;
    }
}
