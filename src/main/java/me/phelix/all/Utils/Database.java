package me.phelix.all.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.IdentityHashMap;
import java.util.Map;

public class Database {

    private Plugin plugin;
    public Database(Plugin plugin) { this.plugin = plugin; }



    public Map<Player, Integer> level = new IdentityHashMap<>();
    public Map<Player, Integer> exp = new IdentityHashMap<>();

    public Integer getLevel(Player player){
        return level.get(player);
    }

    public Integer getExp(Player player){
        return exp.get(player);
    }


    private void zeroInd(Player player){
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.ZeroIndicator")));
    }

    private void calculateStats(Player player){
        int formula = 5 * getLevel(player) + 50;
        while(getExp(player) >= formula){
            level.put(player, getLevel(player) + 1);
            exp.put(player, getExp(player) - formula);
        }
        Bukkit.getPluginManager().callEvent(new EXPChangeEvent(player, this));
    }

    public void setLevel(Player player, String value, boolean give){
        try{
            int number = Integer.parseInt(value);
            if(number < 0){
                zeroInd(player);
                return;
            }
            if(give){
                level.put(player, getLevel(player) +  number);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.LevelGive").replace("%level%", String.valueOf(number))));
            } else {
                level.put(player, number);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.LevelSet").replace("%level%", String.valueOf(number))));
            } calculateStats(player);
        } catch (NumberFormatException e){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.NoValid").replace("%string%", value)));
        }
    }

    public void setLevel(Player player, Player target, String value, boolean give){
        try{
            int number = Integer.parseInt(value);
            if(number < 0){
                zeroInd(player);
                return;
            }
            if(give){
                level.put(target, getLevel(player) +  number);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.LevelGiveTarget")
                        .replace("%level%", String.valueOf(number))
                        .replace("%target%", target.getName()))
                );
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.LevelGiveTargetReceive")
                        .replace("%level%", String.valueOf(number))
                        .replace("%sender%", player.getName()))
                );
            } else {
                level.put(target, number);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.LevelSetTarget")
                        .replace("%level%", String.valueOf(number))
                        .replace("%target%", target.getName()))
                );
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.LevelSetTargetReceive")
                        .replace("%level%", String.valueOf(number))
                        .replace("%sender%", player.getName()))
                );
            } calculateStats(target);
        } catch (NumberFormatException e){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.NoValid").replace("%string%", value)));
        }
    }

    public void setExp(Player player, String value, boolean give){
        try{
            int number = Integer.parseInt(value);
            if(number < 0){
                zeroInd(player);
                return;
            }
            if(give){
                exp.put(player, getExp(player) + number);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.EXPGive")
                        .replace("%exp%", String.valueOf(number))));
            } else {
                exp.put(player, number);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.EXPSet")
                        .replace("%exp%", String.valueOf(number))));
            } calculateStats(player);
        } catch (NumberFormatException e){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.NoValid")
                    .replace("%string%", value)));
        }
    }

    public void setExp(Player player, Player target, String value, boolean give){
        try{
            int number = Integer.parseInt(value);
            if(number < 0){
                zeroInd(player);
                return;
            }
            if(give){
                exp.put(target, getExp(player) +  number);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.EXPGiveTarget")
                        .replace("%exp%", String.valueOf(number))
                        .replace("%target%", target.getName()))
                );
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.EXPGiveTargetReceive")
                        .replace("%exp%", String.valueOf(number))
                        .replace("%sender%", player.getName()))
                );
            } else {
                exp.put(target, number);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.EXPSetTarget")
                        .replace("%exp%", String.valueOf(number))
                        .replace("%target%", target.getName()))
                );
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.EXPSetTargetReceive")
                        .replace("%exp%", String.valueOf(number))
                        .replace("%sender%", player.getName()))
                );
            } calculateStats(target);
        } catch (NumberFormatException e){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.NoValid").replace("%string%", value)));
        }
    }

    public void showStats(Player player){
        for(String msg : plugin.getConfig().getStringList("Messages.StatsSender")){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg)
                    .replace("%level%", String.valueOf(getLevel(player)))
                    .replace("%exp%", String.valueOf(getExp(player)))
                    .replace("%bar%", xpBar(player))
            );
        }
    }

    public void showStats(Player player, Player target){
        for(String msg : plugin.getConfig().getStringList("Messages.StatsTarget")){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg)
                    .replace("%level%", String.valueOf(getLevel(target)))
                    .replace("%exp%", String.valueOf(getExp(target)))
                    .replace("%bar%", xpBar(target))
                    .replace("%target%", target.getName())
            );
        }
    }

    public void resetStats(Player player){
        exp.put(player, 0);
        level.put(player, 0);
    }

    private String xpBar(Player player){
        int totalBars = 50;
        int formula = 5 * getLevel(player) + 50;
        float percent = (float) getExp(player) / formula;

        int progressBars = (int) (totalBars * percent);
        int leftOver = totalBars - progressBars;

        StringBuilder sb = new StringBuilder();
        String completed = ChatColor.GREEN + "|";
        String uncompleted = ChatColor.RED + "|";

        for(int i = 0; i < progressBars; i++){
            sb.append(completed);
        }
        for(int i = 0; i < leftOver; i++){
            sb.append(uncompleted);
        }
        return sb.toString();
    }


}
