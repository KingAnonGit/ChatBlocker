package de.kinganon.chatblocker;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import de.kinganon.chatblocker.cache.Variables;
import net.md_5.bungee.api.plugin.Plugin;

public class MySQL extends Plugin {
    public static Connection connection;
    
    public MySQL() {
        if (!isConnected()) {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://" + FIleManager.configuration.getString("MySQL.Host") + ":" + FIleManager.configuration.getString("MySQL.Port") + "/"
                                + FIleManager.configuration.getString("MySQL.Datenbank"),
                        FIleManager.configuration.getString("MySQL.Benutzername"), FIleManager.configuration.getString("MySQL.Passwort"));
                createTable();
                System.out.println(Variables.prefixConsole + "MySQL Verbindung hergestellt");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static boolean isConnected() {
        try {
            if (connection != null && connection.isClosed() != true) {
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }
    
    public static void update(String qry) {
        if (isConnected()) {
            try {
                connection.createStatement().executeUpdate(qry);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            new MySQL();
            try {
                connection.createStatement().executeUpdate(qry);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static ResultSet getResult(String qry) {
        if (isConnected()) {
            try {
                return connection.createStatement().executeQuery(qry);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            new MySQL();
            try {
                connection.createStatement().executeUpdate(qry);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public static void createTable() {
        if (isConnected()) {
            update("CREATE TABLE IF NOT EXISTS ChatBlocker (mcUUID VARCHAR(100), time INT(100), need BOOLEAN)");
        }
        
    }
    
    public static long getStartTime(UUID uuid) {
        ResultSet rs = getResult("SELECT time FROM ChatBlocker WHERE mcUUID='" + uuid.toString().replace("-", "") + "'");
        try {
            if (rs.next()) {
                return rs.getLong("time");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public static Boolean getNeed(UUID uuid) {
        ResultSet rs = getResult("SELECT need FROM ChatBlocker WHERE mcUUID='" + uuid.toString().replace("-", "") + "'");
        try {
            if (rs.next()) {
                return rs.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Boolean isRegistered(UUID uuid) {
        ResultSet rs = getResult("SELECT mcUUID FROM ChatBlocker WHERE mcUUID='" + uuid.toString().replace("-", "") + "'");
        try {
            if (rs.next()) {
                if (rs.getString("mcUUID") != null) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static void setStartTime(UUID uuid, String time) {
        update("UPDATE ChatBlocker SET time='" + time + "' WHERE mcUUID='" + uuid.toString().replace("-", "") + "'");
    }
    
    public static void setNeed(UUID uuid, int need) {
        update("UPDATE ChatBlocker SET need='" + need + "' WHERE mcUUID='" + uuid.toString().replace("-", "") + "'");
    }
    
    public static void addUser(UUID uuid, long time) {
        update("INSERT INTO ChatBlocker(mcUUID, time, need) VALUES ('" + uuid.toString().replace("-", "") + "', '" + time + "',true)");
    }
}
