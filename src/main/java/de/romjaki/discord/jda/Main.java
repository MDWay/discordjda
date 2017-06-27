package de.romjaki.discord.jda;

import de.romjaki.discord.jda.commands.*;
import de.romjaki.discord.jda.commands.admin.CommandEval;
import de.romjaki.discord.jda.commands.admin.CommandInvites;
import de.romjaki.discord.jda.commands.admin.CommandMove;
import de.romjaki.discord.jda.commands.admin.CommandPermission;
import de.romjaki.discord.jda.commands.category.CategoryAdmin;
import de.romjaki.discord.jda.commands.category.CategoryDefault;
import de.romjaki.discord.jda.commands.category.CategoryMusic;
import de.romjaki.discord.jda.commands.category.CategoryWebContent;
import de.romjaki.discord.jda.commands.music.*;
import de.romjaki.discord.jda.commands.web.CommandAvasDemon;
import de.romjaki.discord.jda.commands.web.CommandPostillon;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Created by RGR on 19.05.2017.
 */
public class Main {
    public static JDA jda;
    public static boolean mainCompleted = false;
    public static boolean ownerCompleted = false;
    private static boolean fullyUpAndRunning = false;

    private Main() {
        UnUtil.singleton(Main.class);
    }

    public static void main(String... args) {
        Locale.setDefault(Locale.ENGLISH);
        registerCategories();
        registerCommands();
        //For Lava Player
        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken(Constants.DiscordUser.TOKEN)
                    .setAutoReconnect(true)
                    .setStatus(OnlineStatus.ONLINE)
                    .setGame(Constants.gameMessage)
                    .addEventListener(new StartupListener())
                    .addEventListener(new MessageListener())
                    .addEventListener(new LoginListener())
                    .buildBlocking();
        } catch (LoginException | InterruptedException | RateLimitedException e) {
            Constants.Loggers.startup.fatal("Error occurred during log in: " + e);
            System.exit(1);
        }
        findLogChannel().ifPresent(SimpleLog2Discord::addLogChannel);
        Permissions.readPermissions(jda);
        Commands.registerHandles(jda);
        Constants.loadOwner(jda, u -> ownerCompleted = true);
        mainCompleted = true;
    }

    private static Optional<TextChannel> findLogChannel() {
        List<TextChannel> channels = new ArrayList<>();
        jda.getGuilds().forEach(guild -> channels.addAll(guild.getTextChannels()));
        return channels.stream().filter(channel -> channel.getName().contains("log") && channel.getName().contains("rom")).findAny();
    }

    private static void registerCategories() {
        Commands.addCategory(new CategoryWebContent());
        Commands.addCategory(new CategoryDefault());
        Commands.addCategory(new CategoryMusic());
        Commands.addCategory(new CategoryAdmin());
    }

    private static void registerCommands() {
        Commands.addCommand(new CommandAvasDemon());
        Commands.addCommand(new CommandCat());
        Commands.addCommand(new CommandInvites());
        Commands.addCommand(new CommandHelp());
        Commands.addCommand(new CommandCountdown());
        Commands.addCommand(new CommandMove());
        Commands.addCommand(new CommandPing());
        Commands.addCommand(new CommandChuck());
        Commands.addCommand(new CommandAddChuck());
        Commands.addCommand(new CommandPermission());
        Commands.addCommand(new CommandQueue());
        Commands.addCommand(new CommandPlayMusic());
        Commands.addCommand(new CommandSkipMusic());
        Commands.addCommand(new CommandCancelAndLeave());
        Commands.addCommand(new CommandPostillon());
        Commands.addCommand(new CommandEval());
        Commands.addCommand(new CommandProgBar());
        Commands.addCommand(new CommandCredits());
        Commands.addCommand(new CommandCurrentlyPlaying());
    }

    public static boolean isFullyUpAndRunning() {
        if (!fullyUpAndRunning) {
            if (mainCompleted && ownerCompleted) fullyUpAndRunning = true;
        }
        return fullyUpAndRunning;
    }
}
