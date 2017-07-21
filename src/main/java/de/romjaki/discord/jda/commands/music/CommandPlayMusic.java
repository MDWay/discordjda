package de.romjaki.discord.jda.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.romjaki.discord.jda.*;
import de.romjaki.discord.jda.commands.Category;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.HashMap;
import java.util.Map;

import static de.romjaki.discord.jda.Main.jda;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by RGR on 09.06.2017.
 */
public class CommandPlayMusic implements Command {

    private static AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
    private static Map<String, GuildMusicManager> musicManagers = new HashMap<>();

    static {
        Permissions.addFlag("playMusic", 2);

        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    public static synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        GuildMusicManager musicManager = musicManagers.computeIfAbsent(guild.getId(), k -> new GuildMusicManager(playerManager, guild));

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public static String durationFormat(long duration) {
        return String.format("%02d:%02d", duration / 1000 / 60, duration / 1000 % 60);
    }

    public static void play(Guild g, TextChannel channel, String url, boolean fFirstOnly, boolean display) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, url, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                musicManager.scheduler.queue(track);
                Constants.Loggers.commands.info("Added the track " + track);
                if (display)
                    channel.sendMessage(new EmbedBuilder()
                            .setColor(UnUtil.RandomUtils.randomColor())
                            .setTitle("Added Title to queue")
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
                    musicManager.scheduler.queue(track);
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
                    .setFooter(getCategory().getName(), jda.getSelfUser().getEffectiveAvatarUrl())
                    .setTitle("Error")
                    .setDescription("You have to join a Voice channel.")
                    .build()).queue(msg -> msg.delete().queueAfter(5, SECONDS));
            return;
        }
        String url = String.join(" ", args);
        AudioManager a = guild.getAudioManager();
        a.openAudioConnection(vchannel);
        boolean firstOnly = false;
        if (!isUrl(url) && !url.contains(":")) {
            url = "ytsearch: " + url;
            firstOnly = true;
        }
        play(guild, channel, url, firstOnly, true);
    }

    public boolean isUrl(String url) {
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
