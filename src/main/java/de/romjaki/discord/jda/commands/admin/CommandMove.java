package de.romjaki.discord.jda.commands.admin;

import de.romjaki.discord.jda.Command;
import de.romjaki.discord.jda.Commands;
import de.romjaki.discord.jda.UnUtil;
import de.romjaki.discord.jda.commands.Category;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.managers.GuildController;

import static de.romjaki.discord.jda.Main.jda;
import static java.util.concurrent.TimeUnit.SECONDS;
import static net.dv8tion.jda.core.Permission.VOICE_MOVE_OTHERS;

/**
 * Created by RGR on 25.06.2017.
 */
public class CommandMove implements Command {
    @Override
    public String getName() {
        return "move";
    }

    @Override
    public void execute(String[] args, Guild guild, TextChannel channel, Member member, Message message) {
        Member user = guild.getMember(message.getMentionedUsers().get(0));
        GuildController controller = guild.getController();
        String channelName = args[1];
        if (channelName.equals("+")) {
            controller.setDeafen(user, false).queue();
            controller.setMute(user, false).queue();
        } else if (channelName.equals("-")) {
            controller.setDeafen(user, true).queue();
            controller.setMute(user, true).queue();
        } else {
            VoiceChannel vChannel = guild.getVoiceChannelById(channelName);
            controller.moveVoiceMember(user, vChannel).queue();
        }

        channel.sendMessage(new EmbedBuilder()
                .setFooter(getCategory().getName(), jda.getSelfUser().getEffectiveAvatarUrl())
                .setTitle("Moved Succesfull")
                .setDescription("Moved " + user.getAsMention() + " successfully.")
                .setColor(UnUtil.RandomUtils.randomColor())
                .build()).queue(msg -> msg.delete().queueAfter(5, SECONDS));
    }

    @Override
    public boolean requiresBotChannel() {
        return false;
    }

    @Override
    public String getDescription() {
        return "Moves a member";
    }

    @Override
    public String getSyntax() {
        return "<member> <channel-name | - >";
    }

    @Override
    public String getTopicRequirement() {
        return "";
    }

    @Override
    public int getRequiredClientPermission() {
        return 0;
    }

    @Override
    public Permission[] getRequiredServerPermission() {
        return new Permission[]{VOICE_MOVE_OTHERS};
    }

    @Override
    public Category getCategory() {
        return Commands.getCategory("admin");
    }
}
