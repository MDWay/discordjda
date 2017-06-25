package de.romjaki.discord.jda.commands.admin;

import de.romjaki.discord.jda.Command;
import de.romjaki.discord.jda.Commands;
import de.romjaki.discord.jda.commands.Category;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

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
        return -1;
    }

    @Override
    public Permission[] getRequiredServerPermission() {
        return new Permission[0];
    }

    @Override
    public Category getCategory() {
        return Commands.getCategory("admin");
    }
}
