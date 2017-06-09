package de.romjaki.discord.jda;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * Created by RGR on 19.05.2017.
 */
public interface Command {

    String getName();

    void execute(String[] args, Guild guild, TextChannel channel, Member member, Message message);

    boolean requiresBotChannel();

    String getDescription();

    String getSyntax();

    String getTopicRequirement();

    int getRequiredClientPermission();

    Permission[] getRequiredServerPermission();

}
