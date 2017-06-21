package de.romjaki.discord.jda;

import com.google.common.base.Splitter;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.utils.SimpleLog;

/**
 * Created by RGR on 21.06.2017.
 */
public class SimpleLog2Discord {
    public static void addLogChannel(TextChannel channel) {
        SimpleLog.addListener(new SimpleLog.LogListener() {
            @Override
            public void onLog(SimpleLog log, SimpleLog.Level logLevel, Object message) {
                String name = log.name;
                String content = message + "";
                if (logLevel == SimpleLog.Level.TRACE || logLevel == SimpleLog.Level.DEBUG) return;
                if (!channel.canTalk()) return;
                for (String s : Splitter.fixedLength(2000).omitEmptyStrings().split("[" + logLevel.getTag() + "]" + content)) {
                    channel.sendMessage(s).queue();
                }
            }

            @Override
            public void onError(SimpleLog log, Throwable err) {
                onLog(log, SimpleLog.Level.FATAL, err);
            }
        });
    }
}
