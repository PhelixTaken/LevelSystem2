package me.phelix.all.Utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.UUID;

public class UserDataHandler {

    private Plugin plugin;
    private UUID uuid;
    private File userFile;
    private FileConfiguration userConfig;

    public UserDataHandler(UUID uuid, Plugin plugin){
        this.plugin = plugin;
        this.uuid = uuid;
        userFile = new File(plugin.getDataFolder() + "/Users", uuid + ".yml");
        userConfig = YamlConfiguration.loadConfiguration(userFile);
    }

    public void createUser(Player player){
        if(!(userFile.exists())){
            try{
                YamlConfiguration userConfig = YamlConfiguration.loadConfiguration(userFile);
                userConfig.set("Player.Name", player.getName());
                userConfig.set("Player.Level", 0);
                userConfig.set("Player.EXP", 0);
                userConfig.save(userFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public FileConfiguration getUserFile(){
        return userConfig;
    }

    public void saveUserFile(){
        try{
            getUserFile().save(userFile);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}