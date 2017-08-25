package de.kinganon.chatblocker;

import de.kinganon.chatblocker.events.ChatEvent;
import de.kinganon.chatblocker.events.JoinEvent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ChatBlocker extends Plugin {
    
    public static File file;
    public static Configuration configuration;
    
    public void onEnable() {
        createConfig();
        new MySQL();
        new ChatEvent(this);
        new JoinEvent(this);
    }
    
    public void onDisable() {
        try {
            MySQL.connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    
    public void createConfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdir();
            }
            file = new File(getDataFolder().getPath(), "config.yml");
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
    
    public void defaultConfiguration() {
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
