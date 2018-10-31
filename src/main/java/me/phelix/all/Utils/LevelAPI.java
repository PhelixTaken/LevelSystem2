package me.phelix.all.Utils;

import org.bukkit.entity.Player;

public class LevelAPI {

    private Database database;
    public LevelAPI(Database database) { this.database = database; }

    public Integer getLevel(Player player){
        return database.level.get(player);
    }

    public Integer getEXP(Player player){
        return database.getExp(player);
    }

    public void setExp(Player player, String number, boolean give){
        database.setExp(player, number, give);
    }

    public void setExp(Player player, Player target, String number, boolean give){
        database.setExp(player, target, number, give);
    }

    public void setLevel(Player player, String number, boolean give){
        database.setLevel(player, number, give);
    }

    public void setLevel(Player player, Player target, String number, boolean give){
        database.setLevel(player, target, number, give);
    }

    public void showStats(Player player){
        database.showStats(player);
    }

    public void showStats(Player player, Player target){
        database.showStats(player, target);
    }

}
