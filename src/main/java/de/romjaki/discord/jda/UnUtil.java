package de.romjaki.discord.jda;

import net.dv8tion.jda.core.entities.TextChannel;
import org.jetbrains.annotations.Contract;

import java.awt.*;

/**
 * Created by RGR on 19.05.2017.
 */
public class UnUtil {
    @Contract(pure = true, value = " -> fail")
    private UnUtil() {
        UnUtil.singleton(UnUtil.class);
    }

    @Contract(pure = true, value = "_ -> fail")
    public static void singleton(Class<?> clazz) {
        throw new Error("No " + clazz.toGenericString() + " instances for you!");
    }

    @Contract(pure = true, value = "null -> fail")
    public static boolean isBotChannel(TextChannel channel) {
        return channel.getName().toLowerCase().contains("bot");
    }


    @Contract(pure = true, value = "null -> fail ; !null -> !null")
    public static String escape(String join) {
        return join.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }

    public static int clamp(int min, int max, int val) {
        return val < min ? min : (val > max ? max : val);
    }

    public static String unescape(String s) {
        return s.replace("\\\n", "\n").replace("\\\"", "\"").replace("\\\\", "\\");
    }

    public static Color randomColor() {
        float r = Constants.random.nextFloat();
        float g = Constants.random.nextFloat();
        float b = Constants.random.nextFloat();
        return new Color(r,g,b);
    }
}
