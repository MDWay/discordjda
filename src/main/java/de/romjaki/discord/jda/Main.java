package de.romjaki.discord.jda;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import de.romjaki.discord.jda.commands.*;
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

    @Contract(" -> fail")
    private Main() {
        UnUtil.singleton(Main.class);
    }

    public static void main(String... args) {
        registerCommands();
        //For Lava Player
        playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        Locale.setDefault(Locale.ENGLISH);
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
    }

    private static void registerCommands() {
        Commands.addCommand(new CommandCat());
        Commands.addCommand(new CommandHelp());
        Commands.addCommand(new CommandCountdown());
        Commands.addCommand(new CommandPing());
        Commands.addCommand(new CommandChuck());
        Commands.addCommand(new CommandAddChuck());
        Commands.addCommand(new CommandPermission());
        Commands.addCommand(new CommandPlayMusic());
    }
}
