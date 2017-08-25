package de.kinganon.chatblocker.events;

import de.kinganon.chatblocker.cache.Variables;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class DisconnectEvent implements Listener {
    public DisconnectEvent(Plugin plugin) {
        ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
    }
    
    @EventHandler
    public void onChat(PlayerDisconnectEvent event) {
        if (Variables.canChat.containsKey(event.getPlayer())) {
            Variables.canChat.remove(event.getPlayer());
        }
        if (Variables.userTimes.containsKey(event.getPlayer())) {
            Variables.userTimes.remove(event.getPlayer());
        }
    }
}
