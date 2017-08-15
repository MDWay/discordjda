package de.romjaki.discord.jda.commands.music;


import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.core.entities.Guild;

/**
 * Created by RGR on 25.06.2017.
 */
public class GuildMusicManager {
    public final AudioPlayer player;
    public final TrackScheduler scheduler;
    public final Guild guild;

    public GuildMusicManager(AudioPlayerManager manager, Guild guild) {
        player = manager.createPlayer();
        this.guild = guild;
        scheduler = new TrackScheduler(player, this);
        player.addListener(scheduler);

    }

    public MusicSendingHandler getSendHandler() {
        return new MusicSendingHandler(player);
    }
}