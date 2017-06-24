package de.romjaki.discord.jda.commands;

import de.romjaki.discord.jda.Command;
import de.romjaki.discord.jda.UnUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * Created by RGR on 19.05.2017.
 */
public class CommandCat implements Command {
    @Override
    public String getName() {
        return "cat";
    }

    @Override
    public void execute(String[] args, Guild guild, TextChannel channel, Member member, Message m) {
        channel.sendMessage(new EmbedBuilder()
                .setDescription(String.join(" ", args))
                .setColor(UnUtil.RandomUtils.randomColor())
                .build()).queue();
        m.delete().queue();
    }

    @Override
    public boolean requiresBotChannel() {
        return true;
    }

    @Override
    public String getDescription() {
        return "outputs the input";
    }

    @Override
    public String getSyntax() {
        return "<text>";
    }

    @Override
    public String getTopicRequirement() {
        return "allowCat";
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
