package me.phelix.all.Commands;

import me.phelix.all.Utils.Msg;
import me.phelix.all.Utils.Database;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class EXP implements CommandExecutor {

    private Database data;
    public EXP(Database database) { this.data = database; }

    public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Msg.noPlayer);
            return true;
        }

        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("exp")) {
            if (args.length == 0) {
                data.showStats(player);
            } else if (args.length > 1 && args[0].equalsIgnoreCase("set")) {
                if (args.length == 3) {
                    data.setExp(player, Bukkit.getPlayer(args[1]), args[2], false);
                } else {
                    data.setExp(player, args[1], false);
                }
            } else if(args.length > 1 && args[0].equalsIgnoreCase("give")){
                if(args.length == 3){
                    data.setExp(player, Bukkit.getPlayer(args[1]), args[2], true);
                } else {
                    data.setExp(player, args[1], true);
                }
            } else if(args.length == 2 && args[0].equalsIgnoreCase("show")){
                data.showStats(player, Bukkit.getPlayer(args[1]));
            } else {
                data.showStats(player);
            }
        }
        return true;
    }
}

