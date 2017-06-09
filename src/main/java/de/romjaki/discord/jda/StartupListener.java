package de.romjaki.discord.jda;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.ShutdownEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.StringJoiner;

/**
 * Created by RGR on 19.05.2017.
 */
public class StartupListener extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        super.onReady(event);
        StringJoiner sj = new StringJoiner("\n", "Everything seems fine. Current servers: \n", "\n");
        for (Guild g : event.getJDA().getGuilds()) {
            sj.add(String.format("* %s(%s)", g.getName(), g.getId()));
        }
        Constants.Loggers.startup.info(sj.toString());

    }

    @Override
    public void onShutdown(ShutdownEvent event) {
        super.onShutdown(event);
        Constants.Loggers.startup.info("Exiting now...");
    }


}
