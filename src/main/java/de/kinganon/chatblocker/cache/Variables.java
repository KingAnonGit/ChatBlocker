package de.kinganon.chatblocker.cache;

import com.google.common.collect.Maps;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;

public class Variables {
    public static String prefixConsole = "[ChatBlocker] >> ";
    public static String prefixUser = "§eDurchrasten §8» §7";
    public static HashMap<ProxiedPlayer, Long> userTimes = Maps.newHashMap();
    public static HashMap<ProxiedPlayer, Boolean> canChat = Maps.newHashMap();
    public static long waitingTime = 7200000;
    
    public static void changeCanChat(Boolean bool, ProxiedPlayer proxiedPlayer) {
        if (canChat.containsKey(proxiedPlayer)) {
            canChat.remove(proxiedPlayer);
            canChat.put(proxiedPlayer, bool);
        } else {
            canChat.put(proxiedPlayer, bool);
        }
    }
}
