package de.romjaki.discord.jda;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.UnaryOperator;

/**
 * Created by RGR on 02.06.2017.
 */
public class Permissions {
    public static final Map<User, Integer> permissionMap = new HashMap<>();
    private static final Map<String, Integer> flagMap = new HashMap<>();
    private static final Integer DEFAULT = 1;
    public static File permissionFile = new File(Constants.Config.path + "permissions.csv");

    static {
        if (!permissionFile.exists()) {
            permissionFile.mkdirs();
        }
    }

    private Permissions() {
        UnUtil.singleton(Permissions.class);
    }

    public static void addFlag(String key, Integer offset) {
        flagMap.put(key.toLowerCase(), offset);
    }

    public static void setPermissions(User u, int permissions) {
        permissionMap.put(u, permissions);
        if (permissions == DEFAULT) {
            permissionMap.remove(u);
        }
        updatePermissions();
    }

    public static void updatePermission(User u, UnaryOperator<Integer> op) {
        setPermissions(u, op.apply(getPermissions(u)));
    }

    public static void updatePermissions() {
        if (!permissionFile.getParentFile().exists()) {
            permissionFile.getParentFile().mkdirs();
        }
        if (!permissionFile.exists()) {
            try {
                permissionFile.createNewFile();
            } catch (IOException e) {
                Constants.Loggers.startup.fatal("Could not create permissionsfile: " + e);
            }
        }
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(permissionFile))) {
            permissionMap.forEach((u, l) -> writer.write(u.getId() + ";" + l + System.lineSeparator()));
        } catch (FileNotFoundException e) {
            Constants.Loggers.commands.warn("permission save failed: " + e);
        }
    }

    public static void readPermissions(JDA jda) {


        if (!permissionFile.getParentFile().exists()) {
            permissionFile.getParentFile().mkdirs();
        }
        if (!permissionFile.exists()) {
            try {
                permissionFile.createNewFile();
            } catch (IOException e) {
                Constants.Loggers.startup.fatal("Could not create permissionsfile: " + e);
            }
        }
        try (Scanner s = new Scanner(permissionFile)) {
            while (s.hasNextLine()) {
                String[] tmp = s.nextLine().split(";", 2);
                permissionMap.put(jda.getUserById(tmp[0]), Integer.valueOf(tmp[1]));
                Constants.Loggers.startup.info("User " + jda.getUserById(tmp[0]) + " has now the permission 0b" + Integer.toBinaryString(Integer.valueOf(tmp[1])) + "(" + tmp[1] + ") by file read.");
            }
        } catch (FileNotFoundException e) {
            Constants.Loggers.commands.warn("permission load failed: " + e);
        }
    }

    public static int getPermissions(User user) {
        return permissionMap.getOrDefault(user, DEFAULT);
    }

    public static Integer getAsFlag(String arg) {
        return 1 << getFlagOffset(arg);
    }

    private static Integer getFlagOffset(String arg) {
        return flagMap.get(arg.toLowerCase());
    }
}
