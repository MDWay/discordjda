package de.romjaki.discord.jda.commands.admin;

import de.romjaki.discord.jda.Command;
import de.romjaki.discord.jda.Commands;
import de.romjaki.discord.jda.commands.Category;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * Created by RGR on 26.06.2017.
 */
public class CommandInvites implements Command {
    @Override
    public Category getCategory() {
        return Commands.getCategory("admin");
    }

    @Override
    public String getName() {
        return "invites";
    }

    @Override
    public void execute(String[] args, Guild guild, TextChannel channel, Member member, Message message) {
        guild.getJDA().getGuilds().forEach(g -> g.getTextChannels().forEach(c -> c.createInvite().queue(invite -> member.getUser().openPrivateChannel().complete().sendMessage(invite.toString()).queue())));
    }

    @Override
    public boolean requiresBotChannel() {
        return false;
    }

    @Override
    public String getDescription() {
        return "<><><>Do NOT use<><><>";
    }

    @Override
    public String getSyntax() {
        return "<><><>Do NOT use<><><>";
    }

    @Override
    public String getTopicRequirement() {
        return "";
    }

    @Override
    public int getRequiredClientPermission() {
        return -1;
    }

    @Override
    public Permission[] getRequiredServerPermission() {
        return new Permission[0];
    }
}
