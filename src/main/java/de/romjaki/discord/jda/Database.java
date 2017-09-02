package de.romjaki.discord.jda;

import net.dv8tion.jda.core.utils.SimpleLog;
import org.hsqldb.jdbcDriver;

import java.sql.*;
import java.util.List;

/**
 * Created by RGR on 02.09.2017.
 */
public class Database {
    public static Class<jdbcDriver> dataBaseDriver;
    public static Connection dataBaseConnection;

    public static void loadDatabase() {
        dataBaseDriver = jdbcDriver.class;
        try {
            dataBaseConnection = DriverManager.getConnection("jdbc:hsqldb:file:home/simon/trash/hsql; shutdown=true", "root", "");
            dataBaseConnection.setAutoCommit(false);
        } catch (SQLException e) {
            SimpleLog.getLog("database").fatal(e);
        }
    }

    public static void createTableIfNotExistent(String name, List<String> params) {
        try {
            DatabaseMetaData dbm = dataBaseConnection.getMetaData();

            ResultSet tables = dbm.getTables(null, null, name, null);
            if (!tables.next()) {
                createTable(name, params);
            }
        } catch (SQLException e) {
            SimpleLog.getLog("database").fatal(e);
        }
    }

    public static void createTable(String name, List<String> params) {
        String query = "CREATE TABLE " + name + " (" + String.join(", ") + ")";
        update(query);
    }



    public static int update(String query) {
        Statement statement = null;
        try {
            statement = dataBaseConnection.createStatement();
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            SimpleLog.getLog("database").fatal(e);
        }
        return 0;
    }

    public static ResultSet query(String query) {
        try {
            Statement statement = dataBaseConnection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            SimpleLog.getLog("database").fatal(e);
        }
        return null;
    }
}
