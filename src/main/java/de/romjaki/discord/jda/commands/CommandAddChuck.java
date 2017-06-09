package de.romjaki.discord.jda.commands;

import de.romjaki.discord.jda.Command;
import de.romjaki.discord.jda.Constants;
import de.romjaki.discord.jda.UnUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by RGR on 23.05.2017.
 */
public class CommandAddChuck implements Command {
    @Override
    public String getName() {
        return "chuck-add";
    }

    @Override
    public void execute(String[] args, Guild guild, TextChannel channel, Member member, Message message) {
        message.delete().queue();
        Constants.Chuck.add(String.join(" ", args));
        channel.sendMessage(new EmbedBuilder()
                .setColor(UnUtil.randomColor())
                .setTitle("Joke added")
                .setDescription(String.join(" ", args))
                .build())
                .queue(msg -> msg.delete().queueAfter(5, SECONDS));
    }

    @Override
    public boolean requiresBotChannel() {
        return false;
    }

    @Override
    public String getDescription() {
        return "adds a joke to the chuck norris list";
    }

    @Override
    public String getSyntax() {
        return "<joke>";
    }

    @Override
    public String getTopicRequirement() {
        return "";
    }

    @Override
    public int getRequiredClientPermission() {
        return 2;
    }

    @Override
    public Permission[] getRequiredServerPermission() {
        return new Permission[0];
    }
}
