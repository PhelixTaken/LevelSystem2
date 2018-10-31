package me.phelix.all.Events;

import me.phelix.all.Utils.Database;
import me.phelix.all.Utils.UserDataHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class Leave implements Listener {

    private Database data;
    private Plugin plugin;
    public Leave(Plugin plugin, Database database){
        this.data = database;
        this.plugin = plugin;
    }

    @EventHandler
    void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UserDataHandler user = new UserDataHandler(player.getUniqueId(), plugin);
        user.getUserFile().set("Player.Level", data.level.get(player));
        user.getUserFile().set("Player.EXP", data.exp.get(player));
        user.saveUserFile();
    }
}
