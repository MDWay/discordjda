package de.romjaki.discord.jda.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.romjaki.discord.jda.*;
import de.romjaki.discord.jda.commands.Category;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

import static de.romjaki.discord.jda.Main.jda;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by RGR on 09.06.2017.
 */
public class CommandSkipMusic implements Command {
    static {
        Permissions.addFlag("skipMusic", 3);
    }

    @Override
    public String getName() {
        return "skip";
    }

    @Override
    public void execute(String[] args, Guild guild, TextChannel channel, Member member, Message message) {
        AudioTrack track = Main.player.getPlayingTrack();
        Main.trackScheduler.nextTrack(true);
        channel.sendMessage(new EmbedBuilder()
                .setColor(UnUtil.RandomUtils.randomColor())
                .setTitle("Skipped title")
                .setFooter("Music", jda.getSelfUser().getEffectiveAvatarUrl())
                .setDescription(track.getInfo().title)
                .build()).queue(msg -> msg.delete().queueAfter(5, SECONDS));
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

    @Override
    public Category getCategory() {
        return Commands.getCategory("music");
    }
}
