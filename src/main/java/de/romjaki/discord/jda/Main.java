package de.romjaki.discord.jda;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import de.romjaki.discord.jda.commands.*;
import de.romjaki.discord.jda.commands.admin.CommandEval;
import de.romjaki.discord.jda.commands.admin.CommandPermission;
import de.romjaki.discord.jda.commands.category.CategoryAdmin;
import de.romjaki.discord.jda.commands.category.CategoryDefault;
import de.romjaki.discord.jda.commands.category.CategoryImages;
import de.romjaki.discord.jda.commands.category.CategoryMusic;
import de.romjaki.discord.jda.commands.images.CommandAvasDemon;
import de.romjaki.discord.jda.commands.music.*;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import org.jetbrains.annotations.Contract;

import javax.security.auth.login.LoginException;
import java.util.Locale;

/**
 * Created by RGR on 19.05.2017.
 */
public class Main {
    public static JDA jda;
    public static AudioPlayerManager playerManager;
    public static TrackScheduler trackScheduler;
    public static AudioPlayer player;

    @Contract(" -> fail")
    private Main() {
        UnUtil.singleton(Main.class);
    }

    public static void main(String... args) {
        registerCategories();
        registerCommands();
        Locale.setDefault(Locale.ENGLISH);
        //For Lava Player
        playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        player = Main.playerManager.createPlayer();
        trackScheduler = new TrackScheduler(player);
        player.addListener(trackScheduler);
        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken(Constants.BotUser.TOKEN)
                    .setAutoReconnect(true)
                    .setStatus(OnlineStatus.ONLINE)
                    .setGame(Constants.gameMessage)
                    .addEventListener(new StartupListener())
                    .addEventListener(new MessageListener())
                    .buildBlocking();

        } catch (LoginException | InterruptedException | RateLimitedException e) {
            Constants.Loggers.startup.fatal("Error occurred during log in: " + e);
            System.exit(1);
        }
        Permissions.readPermissions(jda);
        Constants.initEmotes(jda);
        Commands.registerHandles(jda);
    }

    private static void registerCategories() {
        Commands.addCategory(new CategoryImages());
        Commands.addCategory(new CategoryDefault());
        Commands.addCategory(new CategoryMusic());
        Commands.addCategory(new CategoryAdmin());
    }

    private static void registerCommands() {
        Commands.addCommand(new CommandAvasDemon());
        Commands.addCommand(new CommandCat());
        Commands.addCommand(new CommandHelp());
        Commands.addCommand(new CommandCountdown());
        Commands.addCommand(new CommandPing());
        Commands.addCommand(new CommandChuck());
        Commands.addCommand(new CommandAddChuck());
        Commands.addCommand(new CommandPermission());
        Commands.addCommand(new CommandPlayMusic());
        Commands.addCommand(new CommandSkipMusic());
        Commands.addCommand(new CommandCancelAndLeave());
        Commands.addCommand(new CommandEval());
        Commands.addCommand(new CommandProgBar());
        Commands.addCommand(new CommandCredits());
        Commands.addCommand(new CommandCurrentlyPlaying());
    }
}
