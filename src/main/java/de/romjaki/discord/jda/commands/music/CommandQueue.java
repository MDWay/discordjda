package de.romjaki.discord.jda.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static de.romjaki.discord.jda.Main.trackScheduler;

/**
 * Created by RGR on 25.06.2017.
 */
public class CommandQueue implements Command {
    @Override
    public String getName() {
        return "queue";
    }

    @Override
    public void execute(String[] args, Guild guild, TextChannel channel, Member member, Message message) {
        EmbedBuilder eB = new EmbedBuilder()
                .setColor(UnUtil.RandomUtils.randomColor());
        eB.setDescription("Current Track: " + trackScheduler.currentTrack().getInfo().title + " " +
                "by " + trackScheduler.currentTrack().getInfo().author);
        List<AudioTrack> q = new ArrayList<>(trackScheduler.queue());
        long[] time = {trackScheduler.currentTrack().getDuration() - trackScheduler.currentTrack().getPosition()};
        IntStream.range(0, Math.min(q.size(), 15)).forEach(value -> {
            eB.addField(value + ". " + q.get(value).getInfo().title, "by " + q.get(value).getInfo().author + "; time till playing: " + CommandPlayMusic.durationFormat(time[0]), false);
            time[0] += q.get(value).getDuration();
        });
        channel.sendMessage(eB
                .build()).queue();
    }

    @Override
    public boolean requiresBotChannel() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Shows the current queue";
    }

    @Override
    public String getSyntax() {
        return "";
    }

    @Override
    public String getTopicRequirement() {
        return "allowQueue";
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
        return Commands.getCategory("music");
    }

}
