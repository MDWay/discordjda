package de.romjaki.discord.jda.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.core.utils.SimpleLog;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by RGR on 09.06.2017.
 */
public class TrackScheduler extends AudioEventAdapter {
    private Queue<AudioTrack> queue = new ArrayDeque<>();
    private AudioPlayer player;
    private GuildMusicManager manager;

    public TrackScheduler(AudioPlayer player, GuildMusicManager manager) {
        this.player = player;
        this.manager = manager;
    }

    @Override
    public void onPlayerPause(AudioPlayer player) {
    }

    @Override
    public void onPlayerResume(AudioPlayer player) {
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            SimpleLog.getLog("music").info("Starting next track....");
            nextTrack(true);
        }


    }

    @Override
    public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
        SimpleLog.getLog("music").fatal(exception);
        nextTrack(true);
    }

    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
        if (thresholdMs > 10000) {
            nextTrack(true);
        }
    }

    public void nextTrack(boolean interrupt) {
        if (interrupt) {
            player.stopTrack();
        }
        if (queue.isEmpty()) {
            manager.guild.getAudioManager().closeAudioConnection();
        }
        if (currentTrack() != null && !interrupt) {
            return;
        }
        player.startTrack(queue.poll(), !interrupt);
    }


    public Queue<AudioTrack> queue() {
        return queue;
    }

    public void queue(AudioTrack track) {
        queue.add(track);
        nextTrack(false);
    }

    public void cancelAll() {
        queue.clear();
        player.stopTrack();
    }

    public AudioTrack currentTrack() {
        return player.getPlayingTrack();
    }
}
