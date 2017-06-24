package de.romjaki.discord.jda.commands.admin;

import de.romjaki.discord.jda.Command;
import de.romjaki.discord.jda.Commands;
import de.romjaki.discord.jda.Permissions;
import de.romjaki.discord.jda.UnUtil;
import de.romjaki.discord.jda.commands.Category;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by RGR on 09.06.2017.
 */
public class CommandPermission implements Command {
    @Override
    public String getName() {
        return "perm";
    }

    @Override
    public void execute(String[] args, Guild guild, TextChannel channel, Member member, Message message) {
        if (args.length < 3) return;
        if (args[0].equalsIgnoreCase("rem")) {
            Permissions.updatePermission(message.getMentionedUsers().get(0), perm -> perm & ~((int) Math.pow(2, Integer.parseInt(args[2]) - 1)));
        } else if (args[0].equalsIgnoreCase("add")) {
            Permissions.updatePermission(message.getMentionedUsers().get(0), perm -> perm | ((int) Math.pow(2, Integer.parseInt(args[2]) - 1)));
            Permissions.setPermissions(message.getMentionedUsers().get(0), Permissions.getPermissions(message.getMentionedUsers().get(0)) | ((int) Math.pow(2, Integer.parseInt(args[2]) - 1)));
        } else if (args[0].equalsIgnoreCase("set")) {
            Permissions.setPermissions(message.getMentionedUsers().get(0), Integer.parseInt(args[2]));
        } else if (args[0].equalsIgnoreCase("u_add")) {
            Permissions.updatePermission(message.getMentionedUsers().get(0), perm -> perm | Permissions.getAsFlag(args[2]));
        } else if (args[0].equalsIgnoreCase("u_rem")) {
            Permissions.updatePermission(message.getMentionedUsers().get(0), perm -> perm & ~Permissions.getAsFlag(args[2]));
        }
        channel.sendMessage(new EmbedBuilder()
                .setColor(UnUtil.RandomUtils.randomColor())
                .setTitle("Updated Permissions")
                .setDescription(message.getMentionedUsers().get(0).getAsMention() + " has now the permissions `0b" + Integer.toBinaryString(Permissions.getPermissions(message.getMentionedUsers().get(0))) + "`")
                .build()).queue(msg -> msg.delete().queueAfter(5, SECONDS));
    }

    @Override
    public boolean requiresBotChannel() {
        return false;
    }

    @Override
    public String getDescription() {
        return "Gives or takes permissions";
    }

    @Override
    public String getSyntax() {
        return "<<add|rem> <user> <permission-bit> | <set> <permissions>>";
    }

    @Override
    public String getTopicRequirement() {
        return "";
    }

    @Override
    public Category getCategory() {
        return Commands.getCategory("admin");
    }

    @Override
    public int getRequiredClientPermission() {
        return -1;
    }

    @Override
    public Permission[] getRequiredServerPermission() {
        return new Permission[0];
    }
}
