package de.kinganon.chatblocker.events;

import de.kinganon.chatblocker.MySQL;
import de.kinganon.chatblocker.cache.Variables;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

public class ChatEvent implements Listener {
    public ChatEvent(Plugin plugin) {
        ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
    }
    
    @EventHandler
    public void onChat(net.md_5.bungee.api.event.ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        if (Variables.canChat.get(event.getSender())) {
            System.out.println(event.getMessage());
            if (event.getMessage().contains("https") && !(event.getMessage().contains("://durchrasten.de")) || event.getMessage().contains("http") && !(event.getMessage().contains("://durchrasten.de"))) {
                if (player.hasPermission("ChatBlocker.ByPassLinks")) {
                    event.setCancelled(false);
                } else {
                    player.sendMessage(Variables.prefixUser + "Du darfst keine Links Senden!");
                    event.setMessage("");
                    event.setCancelled(true);
                }
            }
            event.setCancelled(false);
        } else {
            if (player.hasPermission("ChatBlocker.ByPass")) {
                MySQL.setNeed(player.getUniqueId(), 0);
                Variables.changeCanChat(true, player);
                event.setCancelled(false);
            } else if (event.getMessage().startsWith("/")) {
                event.setCancelled(false);
            } else if (MySQL.getStartTime(((ProxiedPlayer) event.getSender()).getUniqueId()) + Variables.waitingTime <= System.currentTimeMillis()) {
                Variables.changeCanChat(true, player);
                MySQL.setNeed(player.getUniqueId(), 0);
                Variables.changeCanChat(true, player);
                event.setCancelled(false);
            } else {
                long hours = TimeUnit.HOURS.convert(Variables.userTimes.get(event.getSender()) + Variables.waitingTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
                long minutesRaw = TimeUnit.MINUTES.convert(Variables.userTimes.get(event.getSender()) + Variables.waitingTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
                long minutes = minutesRaw - TimeUnit.MINUTES.convert(hours, TimeUnit.HOURS);
                
                ((ProxiedPlayer) event.getSender()).sendMessage(Variables.prefixUser + "Du darfst erst in §e" + hours + "§7 Stunden und §e" + minutes + "§7 Minuten Schreiben");
                event.setCancelled(true);
            }
        }
    }
}