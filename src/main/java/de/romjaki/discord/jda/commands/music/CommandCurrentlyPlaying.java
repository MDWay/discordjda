package de.romjaki.discord.jda.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.romjaki.discord.jda.Command;
import de.romjaki.discord.jda.Commands;
import de.romjaki.discord.jda.Main;
import de.romjaki.discord.jda.UnUtil;
import de.romjaki.discord.jda.commands.Category;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

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
        AudioTrack track = Main.trackScheduler.currentTrack();
        channel.sendMessage(new EmbedBuilder()
                .setColor(UnUtil.randomColor())
                .setTitle("Currently Playing:")
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
