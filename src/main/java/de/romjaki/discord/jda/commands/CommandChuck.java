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

/**
 * Created by RGR on 22.05.2017.
 */
public class CommandChuck implements Command {
    @Override
    public String getName() {
        return "chuckMe";
    }

    @Override
    public void execute(String[] args, Guild guild, TextChannel channel, Member member, Message message) {
        String joke = Constants.Chuck.jokes.get(Constants.random.nextInt(Constants.Chuck.jokes.size()));
        channel.sendMessage(new EmbedBuilder()
                .setColor(UnUtil.RandomUtils.randomColor())
                .setTitle(new String[]{"Chuck Worries Joke", "Chuck Norris Joke"}[Constants.random.nextInt(2)])
                .setDescription(joke)
                .build()).queue();
        message.delete().queue();
    }

    @Override
    public boolean requiresBotChannel() {
        return false;
    }

    @Override
    public String getDescription() {
        return "RandomUtils chuck norris witz!";
    }

    @Override
    public String getSyntax() {
        return "";
    }

    @Override
    public String getTopicRequirement() {
        return "allowChuck";
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
