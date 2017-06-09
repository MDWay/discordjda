package de.romjaki.discord.jda.commands;

import de.romjaki.discord.jda.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

import java.awt.*;

/**
 * Created by RGR on 22.05.2017.
 */
public class CommandPing implements Command {
    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public void execute(String[] args, Guild guild, TextChannel channel, Member member, Message message) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.CYAN);
        embed.setTitle("Bot ping:", "http://www.google.com/search?q=bot");
        embed.setDescription(String.format("%.3f", guild.getJDA().getPing() / 1000.0));
        channel.sendMessage(embed.build()).queue();
    }

    @Override
    public boolean requiresBotChannel() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Gets the bot ping";
    }

    @Override
    public String getSyntax() {
        return "";
    }

    @Override
    public String getTopicRequirement() {
        return "allowPing";
    }

    @Override
    public int getRequiredClientPermission() {
        return 0;
    }

    @Override
    public Permission[] getRequiredServerPermission() {
        return new Permission[0];
    }
}
