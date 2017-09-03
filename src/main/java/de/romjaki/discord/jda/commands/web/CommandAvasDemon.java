package de.romjaki.discord.jda.commands.web;

import de.romjaki.discord.jda.Command;
import de.romjaki.discord.jda.Commands;
import de.romjaki.discord.jda.UnUtil;
import de.romjaki.discord.jda.commands.Category;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.utils.SimpleLog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by RGR on 19.06.2017.
 */
public class CommandAvasDemon extends ListenerAdapter implements Command {
    private static final Pattern AVA_FOOTER_MATCH = Pattern.compile("^Ava's Demon Comic #(?<id>\\d+)$");
    private static final String AVAS_DEMON_FOOTER = "Ava's Demon Comic #%d";
    private static final String AVAS_DEMON_START_URL = "http://www.avasdemon.com/start.png";
    private static final String AVAS_DEMON_URL = "http://www.avasdemon.com/pages/%04d.png";

    @Override
    public String getName() {
        return "ava";
    }

    @Override
    public void execute(String[] args, Guild guild, TextChannel channel, Member member, Message message) {
        sendComic(Integer.parseInt(args[0]), channel);
    }

    @Override
    public boolean requiresBotChannel() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Shows you a comic strip";
    }

    @Override
    public String getSyntax() {
        return "[id]";
    }

    @Override
    public String getTopicRequirement() {
        return "allowAva";
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

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if (event.getUser().equals(event.getJDA().getSelfUser())) {
            return;
        }
        Message m = event.getChannel().getMessageById(event.getMessageId()).complete();
        if (!m.getAuthor().equals(event.getJDA().getSelfUser())) {
            return;
        }
        Matcher matcher = AVA_FOOTER_MATCH.matcher(m.getEmbeds().get(0).getFooter().getText());
        if (!matcher.matches()) {
            return;
        }
        int id = Integer.parseInt(matcher.group("id"));
        if (event.getReaction().getEmote().toString().equals("RE:\u2b05(null)")) {
            id--;
        } else if (event.getReaction().getEmote().toString().equals("RE:\u27a1(null)")) {
            id++;
        } else if (event.getReaction().getEmote().toString().equals("RE:\u274c(null)")) {
            m.delete().queue();
            return;
        } else {
            return;
        }
        sendComic(id, event.getChannel());
        SimpleLog.getLog("commands").info("Someone reacted to an avas demon comic strip: " + event.getMember() + " the next comic id is " + id);
    }

    private void sendComic(int id, TextChannel channel) {
        channel.sendMessage(new EmbedBuilder()
                .setColor(UnUtil.RandomUtils.randomColor())
                .setTitle("Avas Demon")
                .setFooter(String.format(AVAS_DEMON_FOOTER, id), AVAS_DEMON_START_URL)
                .setImage(String.format(AVAS_DEMON_URL, id))
                .build()).queue(msg -> {
            msg.addReaction("\u2b05").queue();
            msg.addReaction("\u27a1").queue();
            msg.addReaction("\u274c").queue();
        });
    }

    @Override
    public Command register(JDA jda) {
        jda.addEventListener(this);
        return this;
    }
}
