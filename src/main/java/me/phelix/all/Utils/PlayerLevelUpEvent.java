package me.phelix.all.Utils;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLevelUpEvent extends Event {

    private Database data;
    private Player player;

    public PlayerLevelUpEvent(Player player, Database data){
        this.player = player;
        this.data = data;
    }

    public Player getPlayer(){
        return player;
    }

    public Integer getExp(){
        return data.getExp(player);
    }

    public Integer getLevel(){
        return data.getLevel(player);
    }

    public Integer getOldLevel() { return data.getLevel(player) - 1; }

    private final static HandlerList handlers = new HandlerList();

    public HandlerList getHandlers(){
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }


}
