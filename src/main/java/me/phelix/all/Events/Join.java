package me.phelix.all.Events;

import me.phelix.all.Utils.Database;
import me.phelix.all.Utils.UserDataHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class Join implements Listener {

    private Plugin plugin;
    private Database database;
    public Join(Plugin plugin, Database database) {
        this.plugin = plugin;
        this.database = database;
    }

    @EventHandler
    void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UserDataHandler user = new UserDataHandler(player.getUniqueId(), plugin);
        File userFile = new File(plugin.getDataFolder() + "/Users", player.getUniqueId() + ".yml");
        if (!userFile.exists()) {
            user.createUser(player);
            database.level.put(player, user.getUserFile().getInt("Player.Level"));
            database.exp.put(player, user.getUserFile().getInt("Player.EXP"));
        } else {
            database.level.put(player, user.getUserFile().getInt("Player.Level"));
            database.exp.put(player, user.getUserFile().getInt("Player.EXP"));
        }
    }
}