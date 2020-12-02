package me.yochran.vbungee.data;

import me.yochran.vbungee.vbungee;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ServerData {

    private vbungee plugin;
    public File file;
    public FileConfiguration config;

    public ServerData() {
        plugin = vbungee.getPlugin(vbungee.class);
    }

    public void setupData() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        file = new File(plugin.getDataFolder(), "servers.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("[VectroMC] servers.yml file could not load.");
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getData() {
        return config;
    }

    public void saveData() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[VectroMC] servers.yml file could not save.");
        }
    }

    public void reloadData() {
        config = YamlConfiguration.loadConfiguration(file);
    }
}
