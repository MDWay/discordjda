package de.romjaki.discord.jda;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.utils.SimpleLog;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;

/**
 * Created by RGR on 19.05.2017.
 */
public class Constants {
    public static String cmdChar = "##";
    public static Game gameMessage = Game.of(Constants.cmdChar + "help");
    public static List<String> allowAllCMDs = new ArrayList<>();
    public static Random random = new Random();
    public static Map<String, String> progBars = new HashMap<>();
    public static User OWNER = null;

    static {
        allowAllCMDs.add("316125040917872660");//Leshs Kuhler server
    }

    static {
        String[] ids = {"<:progbar_mid_full:324910937863618560>", "<:progbar_mid_empty:324910938400751617>", "<:progbar_mid_swap:324910937771343873>", "<:progbar_start_full:324910938715193355>", "<:progbar_end_full:324910937741983744>", "<:progbar_start_empty:324910938224459778>", "<:progbar_end_empty:324910937368952832>"};
        String[] names = {"midfull", "midempty", "midswap", "startfull", "endfull", "startempty", "endempty"};
        for (int i = 0; i < names.length; i++) {
            progBars.put(names[i], ids[i]);
        }
    }

    private Constants() {
        UnUtil.singleton(Constants.class);
    }

    public static void loadOwner(JDA jda, Consumer<User> loaded) {
        jda.asBot().getApplicationInfo().queue(applicationInfo -> {
            OWNER = applicationInfo.getOwner();
            loaded.accept(OWNER);
        });
    }


    public static class SpotifyUser {
        public static String CLIENT_SECRET = "token-invalid";
        public static String CLIENT_ID = "token-invalid";

        static {
            try (Scanner s = new Scanner(new File(Config.path + "spotify.id"))) {
                s.useDelimiter("\\A");
                CLIENT_ID = s.next();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try (Scanner s = new Scanner(new File(Config.path + "spotify.secret"))) {
                s.useDelimiter("\\A");
                CLIENT_SECRET = s.next();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        private SpotifyUser() {
            UnUtil.singleton(SpotifyUser.class);
        }
    }

    public static class DiscordUser {
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

        private DiscordUser() {
            UnUtil.singleton(DiscordUser.class);
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
