package de.kinganon.chatblocker.events;

import de.kinganon.chatblocker.MySQL;
import de.kinganon.chatblocker.cache.Variables;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class JoinEvent implements Listener {
    public JoinEvent(Plugin plugin) {
        ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
    }
    
    @EventHandler
    public void onJoin(ServerConnectEvent event) {
        Boolean isRegistered = MySQL.isRegistered(event.getPlayer().getUniqueId());
        if (!isRegistered) {
            MySQL.addUser(event.getPlayer().getUniqueId(), System.currentTimeMillis());
            Variables.userTimes.put(event.getPlayer(), MySQL.getStartTime(event.getPlayer().getUniqueId()));
            Variables.changeCanChat(false, event.getPlayer());
        } else if (isRegistered) {
            if (MySQL.getNeed(event.getPlayer().getUniqueId())) {
                Variables.userTimes.put(event.getPlayer(), MySQL.getStartTime(event.getPlayer().getUniqueId()));
                if (Variables.userTimes.get(event.getPlayer()) + Variables.waitingTime <= System.currentTimeMillis()) {
                    Variables.changeCanChat(true, event.getPlayer());
                    MySQL.setNeed(event.getPlayer().getUniqueId(), 0);
                } else {
                    Variables.changeCanChat(false, event.getPlayer());
                }
            } else {
                Variables.changeCanChat(true, event.getPlayer());
            }
        }
    }
}
