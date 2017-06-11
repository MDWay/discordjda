package de.romjaki.discord.jda;

import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.utils.SimpleLog;
import org.jetbrains.annotations.Contract;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by RGR on 19.05.2017.
 */
public class Constants {
    public static String cmdChar = "##";
    public static Game gameMessage = Game.of(Constants.cmdChar + "help");
    public static List<String> allowAllCMDs = new ArrayList<>();
    public static Random random = new Random();

    static {
        allowAllCMDs.add("316125040917872660");//Leshs Kuhler server
    }


    @Contract(" -> fail")
    private Constants() {
        UnUtil.singleton(Constants.class);
    }

    public static class BotUser {
        public static final String NAME = "api-testing-rom";
        public static final int NUMBER = 9515;
        public static final String USERTAG = NAME + "#" + NUMBER;
        public static String TOKEN = "token-invalid";

        static {
            try (Scanner s = new Scanner(new File(Config.path + "bot.token"))) {
                s.useDelimiter("\\A");
                TOKEN = s.next();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        private BotUser() {
            UnUtil.singleton(BotUser.class);
        }
    }

    public static class Loggers {
        public static final SimpleLog startup = SimpleLog.getLog("STARTUP");
        public static final SimpleLog commands = SimpleLog.getLog("COMMANDS");

        private Loggers() {
            UnUtil.singleton(Loggers.class);
        }
    }

    public static class Config {
        public static String path = "C:\\config\\";

        private Config() {
            UnUtil.singleton(Config.class);
        }
    }

    public static class Chuck {
        public static final List<String> jokes = new ArrayList<>();
        public static File jokeFile = new File(Config.path + "jokes.list");

        static {
            readJokeList();
        }

        private Chuck() {
            UnUtil.singleton(Chuck.class);
        }

        public static void add(String joke) {
            jokes.add(joke);
            updateJokeList();
        }

        public static void updateJokeList() {
            if (!jokeFile.getParentFile().exists()) {
                jokeFile.getParentFile().mkdirs();
            }
            if (!jokeFile.exists()) {
                try {
                    jokeFile.createNewFile();
                } catch (IOException e) {
                    Loggers.startup.fatal("Could not create jokefile: " + e);
                }
            }
            try (PrintWriter writer = new PrintWriter(new FileOutputStream(jokeFile))) {
                jokes.forEach(j -> {
                    writer.print(UnUtil.escape(j));
                    writer.print(System.lineSeparator());
                });
            } catch (FileNotFoundException e) {
                Loggers.commands.warn("Joke save failed: " + e);
            }
        }

        public static void readJokeList() {
            if (!jokeFile.getParentFile().exists()) {
                jokeFile.getParentFile().mkdirs();
            }
            if (!jokeFile.exists()) {
                try {
                    jokeFile.createNewFile();
                } catch (IOException e) {
                    Loggers.startup.fatal("Could not create jokefile: " + e);
                }
            }
            try (Scanner s = new Scanner(jokeFile)) {
                while (s.hasNextLine()) {
                    String tmp = UnUtil.unescape(s.nextLine());
                    if (tmp.trim().length() != 0) {
                        jokes.add(tmp);
                    }
                }
            } catch (FileNotFoundException e) {
                Loggers.commands.warn("Joke load failed: " + e);
            }
        }


    }
}
