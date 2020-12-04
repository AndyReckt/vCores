package net.vectromc.vbasic.data;

import net.vectromc.vbasic.vBasic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class EconomyData {

    private vBasic plugin;
    public File file;
    public FileConfiguration config;

    public EconomyData() {
        plugin = vBasic.getPlugin(vBasic.class);
    }

    public void setupData() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        file = new File(plugin.getDataFolder(), "economy.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("[VectroMC] economy.yml file could not load.");
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
            System.out.println("[VectroMC] economy.yml file could not save.");
        }
    }

    public void reloadData() {
        config = YamlConfiguration.loadConfiguration(file);
    }
}
