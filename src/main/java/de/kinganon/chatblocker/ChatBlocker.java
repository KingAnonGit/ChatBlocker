package de.kinganon.chatblocker;

import de.kinganon.chatblocker.events.ChatEvent;
import de.kinganon.chatblocker.events.JoinEvent;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

import java.sql.SQLException;

public class ChatBlocker extends Plugin {
    
    @Getter
    private static ChatBlocker chatBlocker;
    
    public ChatBlocker() {
        chatBlocker = this;
    }
    
    public void onEnable() {
        FIleManager.createConfig();
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
}
