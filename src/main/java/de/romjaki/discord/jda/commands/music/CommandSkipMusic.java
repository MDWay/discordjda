package de.romjaki.discord.jda.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.romjaki.discord.jda.Command;
import de.romjaki.discord.jda.Main;
import de.romjaki.discord.jda.Permissions;
import de.romjaki.discord.jda.UnUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * Created by RGR on 09.06.2017.
 */
public class CommandSkipMusic implements Command {
    static {
        Permissions.addFlag("skipMusic", 3);
    }

    @Override
    public String getName() {
        return "mskip";
    }

    @Override
    public void execute(String[] args, Guild guild, TextChannel channel, Member member, Message message) {
        AudioTrack track = Main.player.getPlayingTrack();
        Main.trackScheduler.nextTrack(true);
        channel.sendMessage(new EmbedBuilder()
                .setColor(UnUtil.randomColor())
                .setTitle("Skipped title")
                .setDescription(track.getInfo().title)
                .build()).queue();
    }

    @Override
    public boolean requiresBotChannel() {
        return false;
    }

    @Override
    public String getDescription() {
        return "skips the next song";
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
        return Permissions.getAsFlag("skipMusic");
    }

    @Override
    public Permission[] getRequiredServerPermission() {
        return new Permission[0];
    }
}
