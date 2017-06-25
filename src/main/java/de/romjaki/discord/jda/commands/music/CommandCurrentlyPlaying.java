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

import static de.romjaki.discord.jda.Main.jda;

/**
 * Created by RGR on 10.06.2017.
 */
public class CommandCurrentlyPlaying implements Command {
    @Override
    public String getName() {
        return "playing";
    }

    @Override
    public Category getCategory() {
        return Commands.getCategory("music");
    }

    @Override
    public void execute(String[] args, Guild guild, TextChannel channel, Member member, Message message) {
        AudioTrack track = CommandPlayMusic.getGuildAudioPlayer(guild).scheduler.currentTrack();
        if (track == null) {
            channel.sendMessage(new EmbedBuilder()
                    .setColor(UnUtil.RandomUtils.randomColor())
                    .setTitle("Currently Playing:")
                    .setDescription("Currently the queue is empty")
                    .setFooter("Music", jda.getSelfUser().getEffectiveAvatarUrl())
                    .build()).queue();
            return;
        }
        channel.sendMessage(new EmbedBuilder()
                .setColor(UnUtil.RandomUtils.randomColor())
                .setTitle("Currently Playing:")
                .setFooter("Music", jda.getSelfUser().getEffectiveAvatarUrl())
                .addField("Name", track.getInfo().title, true)
                .addField("Interpret", track.getInfo().author, true)
                .addField("Duration", CommandPlayMusic.durationFormat(track.getDuration()), true)
                .build()).queue();
    }

    @Override
    public boolean requiresBotChannel() {
        return false;
    }

    @Override
    public String getDescription() {
        return "Shows the current song.";
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
        return 0;
    }

    @Override
    public Permission[] getRequiredServerPermission() {
        return new Permission[0];
    }
}
