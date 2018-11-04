package me.phelix.all.Utils;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerEXPChangeEvent extends Event {
    private Database data;
    private Player player;

    public PlayerEXPChangeEvent(Player player, Database data){
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

    private final static HandlerList handlers = new HandlerList();

    public HandlerList getHandlers(){
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }

}
