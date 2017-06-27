package de.romjaki.discord.jda;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.utils.SimpleLog;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * Created by RGR on 19.05.2017.
 */
public class UnUtil {
    private UnUtil() {
        UnUtil.singleton(UnUtil.class);
    }


    public static String getWebContent(URL url) {
        try (Scanner s = new Scanner(url.openStream())) {
            s.useDelimiter("\\A");
            return s.next();
        } catch (IOException e) {
            SimpleLog.getLog("web").fatal(e);
        }
        return null;
    }

    public static void singleton(Class<?> clazz) {
        throw new Error("No " + clazz.toGenericString() + " instances for you!");
    }

    public static boolean isBotChannel(TextChannel channel) {
        return channel.getName().toLowerCase().contains("bot");
    }


    public static String escape(String join) {
        return join.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }

    public static int clamp(int min, int max, int val) {
        return val < min ? min : (val > max ? max : val);
    }

    public static String unescape(String s) {
        return s.replace("\\\n", "\n").replace("\\\"", "\"").replace("\\\\", "\\");
    }

    public static class RandomUtils {
        private RandomUtils() {
            UnUtil.singleton(RandomUtils.class);
        }

        public static <T> T choice(List<T> strings) {
            return strings.get(Constants.random.nextInt(strings.size()));
        }

        public static Color randomColor() {
            float r = Constants.random.nextFloat();
            float g = Constants.random.nextFloat();
            float b = Constants.random.nextFloat();
            return new Color(r, g, b);
        }
    }
}
