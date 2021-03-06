package de.romjaki.discord.jda.commands.music;

import de.romjaki.discord.jda.Command;
import de.romjaki.discord.jda.Commands;
import de.romjaki.discord.jda.UnUtil;
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
 * Created by RGR on 10.06.2017.
 */
public class CommandCancelAndLeave implements Command {
    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public void execute(String[] args, Guild guild, TextChannel channel, Member member, Message message) {
        CommandPlayMusic.getGuildAudioPlayer(guild).scheduler.cancelAll();
        guild.getAudioManager().closeAudioConnection();
        channel.sendMessage(new EmbedBuilder()
                .setTitle("Cleared the music queue... Now leaving Music Channel")
                .setFooter(getCategory().getName(), jda.getSelfUser().getEffectiveAvatarUrl())
                .setColor(UnUtil.RandomUtils.randomColor())
                .build()).queue(msg -> msg.delete().queueAfter(5, SECONDS));
    }

    @Override
    public boolean requiresBotChannel() {
        return false;
    }

    @Override
    public String getDescription() {
        return "Deletes the music queue and leaves the current voicechannel.";
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
        return 8;
    }

    @Override
    public Category getCategory() {
        return Commands.getCategory("music");
    }

    @Override
    public Permission[] getRequiredServerPermission() {
        return new Permission[0];
    }
}
