package de.romjaki.discord.jda.commands.music;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.romjaki.discord.jda.*;
import de.romjaki.discord.jda.commands.Category;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.regex.Pattern;

import static de.romjaki.discord.jda.Main.*;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by RGR on 09.06.2017.
 */
public class CommandPlayMusic implements Command {

    static {
        Permissions.addFlag("playMusic", 2);
    }

    public static String durationFormat(long duration) {
        return String.format("%02d:%02d", duration / 1000 / 60, duration / 1000 % 60);
    }

    private static void play(AudioManager a, TextChannel channel, String url, boolean fFirstOnly, boolean display) {

        playerManager.loadItemOrdered(a, url, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                trackScheduler.queue(track);
                Constants.Loggers.commands.info("Added the track " + track);
                if (display)
                    channel.sendMessage(new EmbedBuilder()
                            .setColor(UnUtil.RandomUtils.randomColor())
                            .setTitle("Added Title to queue", track.getInfo().uri)
                            .setFooter("Music", jda.getSelfUser().getEffectiveAvatarUrl())
                            .addField("Name", track.getInfo().title, true)
                            .addField("Interpret", track.getInfo().author, true)
                            .addField("Duration", durationFormat(track.getDuration()), true)
                            .build()).queue(msg -> msg.delete().queueAfter(5, SECONDS));
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                if (fFirstOnly) {
                    trackLoaded(playlist.getTracks().get(0));
                    return;
                }
                for (AudioTrack track : playlist.getTracks()) {
                    trackScheduler.queue(track);
                    Constants.Loggers.commands.info("Added the track " + track);
                }
                if (display)
                    channel.sendMessage(new EmbedBuilder()
                            .setTitle("Added Playlist to queue")
                            .setFooter("Music", jda.getSelfUser().getEffectiveAvatarUrl())
                            .addField("Title", playlist.getName(), true)
                            .setColor(UnUtil.RandomUtils.randomColor())
                            .addField("Duration", playlist.getTracks().stream().map(AudioTrack::getDuration).reduce(Math::addExact) + "", true)
                            .build()).queue(msg -> msg.delete().queueAfter(5, SECONDS));
            }

            @Override
            public void noMatches() {
                Constants.Loggers.commands.fatal("Failed to fetch the music: " + url);
            }

            @Override
            public void loadFailed(FriendlyException e) {
                Constants.Loggers.commands.fatal("Failed to fetch music: " + e);
            }
        });
    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public void execute(String[] args, Guild guild, TextChannel channel, Member member, Message message) {
        VoiceChannel vchannel = member.getVoiceState().getChannel();
        if (vchannel == null) {
            channel.sendMessage(new EmbedBuilder()
                    .setColor(UnUtil.RandomUtils.randomColor())
                    .setFooter("Music", jda.getSelfUser().getEffectiveAvatarUrl())
                    .setTitle("Error")
                    .setDescription("You have to join a Voice channel.")
                    .build()).queue(msg -> msg.delete().queueAfter(5, SECONDS));
            return;
        }
        String url = String.join(" ", args);
        AudioManager a = guild.getAudioManager();
        a.setSendingHandler(new MusicSendingHandler(player));
        a.openAudioConnection(vchannel);
        boolean firstOnly = false;
        if (!isUrl(url) && !url.contains(":")) {
            url = "ytsearch: " + url;
            firstOnly = true;
        }
        play(a, channel, url, firstOnly, true);
    }

    private boolean isUrl(String url) {
        return url.matches("(?i)^(http[s]://)?\\w+\\.\\w+.*");
    }


    @Override
    public Category getCategory() {
        return Commands.getCategory("music");
    }

    @Override
    public boolean requiresBotChannel() {
        return false;
    }

    @Override
    public String getDescription() {
        return "Plays Music in the players channel";
    }

    @Override
    public String getSyntax() {
        return "<query>";
    }

    @Override
    public String getTopicRequirement() {
        return "";
    }

    @Override
    public int getRequiredClientPermission() {
        return Permissions.getAsFlag("playMusic");
    }

    @Override
    public Permission[] getRequiredServerPermission() {
        return new Permission[0];
    }
}
