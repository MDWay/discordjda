package de.romjaki.discord.jda.commands.web;

import de.romjaki.discord.jda.Cache;
import de.romjaki.discord.jda.Command;
import de.romjaki.discord.jda.Commands;
import de.romjaki.discord.jda.UnUtil;
import de.romjaki.discord.jda.commands.Category;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.utils.SimpleLog;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by RGR on 22.06.2017.
 */
public class CommandPostillon implements Command {
    static Cache<List<String>> tickers = new Cache<>(() -> {
        try {
            String[] content = UnUtil.getWebContent(new URL("http://www.der-postillon.com/search/label/Newsticker")).split("\\+\\+\\+");
            return IntStream.range(0, content.length).filter(i -> (i & 1) == 1).mapToObj(i -> content[i]).collect(Collectors.toList());
        } catch (MalformedURLException e) {
            SimpleLog.getLog("web").fatal(e);
        }
        return Collections.emptyList();
    });

    @Override
    public String getName() {
        return "postillon";
    }


    @Override
    public void execute(String[] args, Guild guild, TextChannel channel, Member member, Message message) {
        String line = UnUtil.RandomUtils.choice(tickers.get());
        channel.sendMessage(new EmbedBuilder()
                .setFooter("Der Postillon! Ehrliche Nachrichten seit 69 v. Chr.", "https://lh3.googleusercontent.com/WoYINqqRqzxW1prpVsbtsoDJpFFRBVtP-ThnwPqFUfz8bphezOAIj9cVd6PW_t3rpg=w300")
                .setTitle("BREAKING NEWS!")
                .setDescription("+++ " + line + " +++")
                .build()).queue();
    }

    @Override
    public boolean requiresBotChannel() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Gives you a random postillon news ticker entry";
    }

    @Override
    public String getSyntax() {
        return "";
    }

    @Override
    public String getTopicRequirement() {
        return "allowPostillon";
    }

    @Override
    public int getRequiredClientPermission() {
        return 0;
    }

    @Override
    public Permission[] getRequiredServerPermission() {
        return new Permission[0];
    }

    @Override
    public Category getCategory() {
        return Commands.getCategory("web");
    }
}
