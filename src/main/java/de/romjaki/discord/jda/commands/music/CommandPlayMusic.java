package de.romjaki.discord.jda.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.romjaki.discord.jda.Command;
import de.romjaki.discord.jda.Constants;
import de.romjaki.discord.jda.Main;
import de.romjaki.discord.jda.UnUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.managers.AudioManager;

/**
 * Created by RGR on 09.06.2017.
 */
public class CommandPlayMusic implements Command {
    @Override
    public String getName() {
        return "music";
    }

    @Override
    public void execute(String[] args, Guild guild, TextChannel channel, Member member, Message message) {
        VoiceChannel vchannel = member.getVoiceState().getChannel();
        String url = args[0];
        AudioManager a = guild.getAudioManager();
        AudioPlayer player = Main.playerManager.createPlayer();
        TrackScheduler trackScheduler = new TrackScheduler(player);
        player.addListener(trackScheduler);
        a.setSendingHandler(new MusicSendingHandler(player));
        a.openAudioConnection(vchannel);
        Main.playerManager.loadItemOrdered(a, url, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                trackScheduler.queue(track);
                Constants.Loggers.commands.info("Added the track " + track);
                channel.sendMessage(new EmbedBuilder()
                        .setColor(UnUtil.randomColor())
                        .setTitle("Added Title to queue", track.getInfo().uri)
                        .addField("Name", track.getInfo().title, true)
                        .addField("Interpret", track.getInfo().author, true)
                        .addField("Duration", durationFormat(track.getDuration()), true)
                        .build()).queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                for (AudioTrack track : playlist.getTracks()) {
                    trackScheduler.queue(track);
                    Constants.Loggers.commands.info("Added the track " + track);
                }
                channel.sendMessage(new EmbedBuilder()
                        .setTitle("Added Playlist to queue")
                        .addField("Title", playlist.getName(), true)
                        .setColor(UnUtil.randomColor())
                        .addField("Duration", playlist.getTracks().stream().map(AudioTrack::getDuration).reduce(Math::addExact) + "", true)
                        .build()).queue();
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

    private String durationFormat(long duration) {
        return String.format("%02d:%02d", duration / 1000 / 60, duration / 1000 % 60);
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
        return "<yt-url>";
    }

    @Override
    public String getTopicRequirement() {
        return "";
    }

    @Override
    public int getRequiredClientPermission() {
        return 4;
    }

    @Override
    public Permission[] getRequiredServerPermission() {
        return new Permission[0];
    }
}
