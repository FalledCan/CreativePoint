package xyz.masa3mc.creativepoint;

import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

public class DBcontrol {

    public static Statement stmt = null;

    public void loadDB(){

        File file = new File(Bukkit.getServer().getPluginManager().getPlugin("CreativePoint").getDataFolder().getAbsolutePath() + "/data.db");

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + Bukkit.getServer().getPluginManager().getPlugin("CreativePoint").getDataFolder().getAbsolutePath() + "/data.db");
            stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS players(uuid, point, times)");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public void insert(UUID uuid){

        try {
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void update(UUID uuid, String rankpoint, String atm){
        if(rankpoint != null) {
            long rp = Long.parseLong(rankpoint);
            try {
                stmt.executeUpdate("update players set rankpoint = " + rp + " where uuid = '" + uuid + "'");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }else {
            long atm_ = Long.parseLong(atm);
            try {
                stmt.executeUpdate("update players set atm = " + atm_ + " where uuid = '" + uuid + "'");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    public long get(UUID uuid, boolean atm){
        long rankpoint = 0;
        long _atm = 0;
        try {
            ResultSet rs = stmt.executeQuery("select * from players where uuid = '" + uuid + "'");
            while (rs.next()){
                rankpoint = rs.getLong("rankpoint");
                _atm = rs.getLong("atm");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (atm){
            return _atm;
        }else {
            return rankpoint;
        }
    }

}
