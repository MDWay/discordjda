package de.romjaki.discord.jda.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEvent;
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

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
    }

    @Override
    public void onPlayerPause(AudioPlayer player) {
        // Player was paused
    }

    @Override
    public void onPlayerResume(AudioPlayer player) {
        // Player was resumed
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        // A track started playing
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            if (queue.isEmpty()) {
                return;
            }
            nextTrack(false);
        }
    }

    @Override
    public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
        nextTrack(true);
        SimpleLog.getLog("music").fatal(exception);
    }

    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
        if (thresholdMs > 10000) {
            nextTrack(true);
        }
    }

    public void nextTrack(boolean interrupt) {
        if (queue.isEmpty() || ((currentTrack() != null) && !interrupt)) {
            return;
        }
        if (interrupt && currentTrack() != null) {
            player.stopTrack();
        }
        player.startTrack(queue.poll(), true);

    }

    @Override
    public void onEvent(AudioEvent audioEvent) {

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
