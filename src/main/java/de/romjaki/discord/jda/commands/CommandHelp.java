package de.romjaki.discord.jda.commands;

import de.romjaki.discord.jda.Command;
import de.romjaki.discord.jda.Commands;
import de.romjaki.discord.jda.Constants;
import de.romjaki.discord.jda.UnUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * Created by RGR on 20.05.2017.
 */
public class CommandHelp implements Command {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public void execute(String[] args, Guild guild, TextChannel channel, Member member, Message message) {
        switch (args.length) {
            case 0: {
                EmbedBuilder eB = new EmbedBuilder();
                for (Category category : Commands.getCategories()) {
                    eB.addField(category.getName(), "More info with: `" + Constants.cmdChar + getName() + " /" + category.getName() + "`", false);
                }
                channel.sendMessage(eB
                        .build()).queue();
                break;
            }
            case 1: {
                if (args[0].startsWith("/")) {
                    Category c = Commands.getCategory(args[0].replaceFirst("/", ""));
                    if (c == null) {
                        channel.sendMessage(new EmbedBuilder()
                                .setTitle("Error")
                                .setColor(UnUtil.randomColor())
                                .setDescription("The category `" + args[0] + "` was not found!")
                                .build()).queue();
                    } else {
                        sendCategory(channel, c);
                    }
                } else {
                    Command c = Commands.getCommand(args[0]);
                    if (c == null) {
                        channel.sendMessage(new EmbedBuilder()
                                .setTitle("Error")
                                .setColor(UnUtil.randomColor())
                                .setDescription("The command `" + args[0] + "` was not found!")
                                .build()).queue();
                    } else {
                        sendCommand(channel, c);
                    }
                }

                break;
            }
        }
        message.delete().queue();
    }

    private void sendCategory(TextChannel channel, Category c) {
        EmbedBuilder eB = new EmbedBuilder();
        c.getCommands().forEach(com -> eB.addField("`" + Constants.cmdChar + com.getInvokation() + " " + com.getSyntax() + "`", com.getDescription(), false));
        channel.sendMessage(eB.build()).queue();
    }

    public void sendCommand(TextChannel channel, Command c) {
        channel.sendMessage(new EmbedBuilder()
                .setTitle(Constants.cmdChar + c.getName())
                .setColor(UnUtil.randomColor())
                .setDescription("`" + Constants.cmdChar + c.getName() + " " + c.getSyntax() + "`   " + c.getDescription())
                .build()).queue();
    }

    @Override
    public boolean requiresBotChannel() {
        return false;
    }

    @Override
    public String getSyntax() {
        return "[command | /category]";
    }

    @Override
    public String getTopicRequirement() {
        return "allowHelp";
    }

    @Override
    public String getDescription() {
        return "helps";
    }

    @Override
    public Permission[] getRequiredServerPermission() {
        return new Permission[0];
    }

    @Override
    public int getRequiredClientPermission() {
        return 0;
    }
}
