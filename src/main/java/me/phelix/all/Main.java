package me.phelix.all;

import me.phelix.all.Commands.EXP;
import me.phelix.all.Commands.Level;
import me.phelix.all.Events.Join;

import me.phelix.all.Events.Leave;
import me.phelix.all.Utils.Database;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {


    private Database database;

    public void onEnable(){
        database = new Database(this);

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        registerCommands();
        registerEvents();
    }

    private void registerCommands(){
        getCommand("level").setExecutor(new Level(database));
        getCommand("exp").setExecutor(new EXP(database));
    }

    private void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new Join(this, database), this);
        Bukkit.getPluginManager().registerEvents(new Leave(this, database), this);
    }




}
