package de.romjaki.discord.jda.commands.music;

import de.romjaki.discord.jda.Command;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * Created by RGR on 09.06.2017.
 */
public class CommandSkipMusic implements Command {
    @Override
    public String getName() {
        return "skip";
    }

    @Override
    public void execute(String[] args, Guild guild, TextChannel channel, Member member, Message message) {

    }

    @Override
    public boolean requiresBotChannel() {
        return false;
    }

    @Override
    public String getDescription() {
        return "skips the next song";
    }

    @Override
    public String getSyntax() {
        return "";
    }

    @Override
    public String getTopicRequirement() {
        return "";
    }

    @Override
    public int getRequiredClientPermission() {
        return 8;
    }

    @Override
    public Permission[] getRequiredServerPermission() {
        return new Permission[0];
    }
}
