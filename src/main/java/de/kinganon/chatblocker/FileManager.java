package de.kinganon.chatblocker;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {
    
    public static File file;
    public static Configuration configuration;
    
    public static void createConfig() {
        try {
            if (!ChatBlocker.getPlugin().getDataFolder().exists()) {
                new ChatBlocker().getPlugin().getDataFolder().mkdir();
            }
            file = new File(new ChatBlocker().getPlugin().getDataFolder().getPath(), "config.yml");
            if (!file.exists()) {
                file.createNewFile();
                configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
                defaultConfiguration();
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
            } else {
                configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
                saveConfiguration();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void defaultConfiguration() {
        configuration.set("MySQL.Host", "localhost");
        configuration.set("MySQL.Port", "3306");
        configuration.set("MySQL.Datenbank", "ChatBlocker");
        configuration.set("MySQL.Benutzername", "root");
        configuration.set("MySQL.Passwort", "password");
    }
    
    public static void saveConfiguration() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
