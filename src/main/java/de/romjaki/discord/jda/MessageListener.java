package de.romjaki.discord.jda;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.regex.Pattern;

/**
 * Created by RGR on 19.05.2017.
 */
public class MessageListener extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);
        Message mesWrapper = event.getMessage();
        String mes = mesWrapper.getRawContent();
        if (mes.startsWith(Constants.cmdChar)) {
            mes = mes.replaceFirst(Constants.cmdChar, "");
            String[] tmp = mes.split("\\s+", 2);
            String command = tmp[0].replaceFirst(Pattern.quote(Constants.cmdChar), "");
            String[] args;
            if (tmp.length == 1) {
                args = new String[0];
            } else {
                args = tmp[1].split(" ");
                if (args.length > 1 && args[0].length() == 0) {
                    args = new String[0];
                }
            }
            processCommand(command, args, event.getGuild(), event.getChannel(), event.getMember(), mesWrapper);
        }
    }

    public void processCommand(String command, String[] args, Guild guild, TextChannel channel, Member member, Message message) {
        channel.sendTyping().queue();
        if (member.equals(message.getJDA().getSelfUser())) {
            channel.sendMessage("You should not use the bot to execute code/commands.").queue();
            Constants.Loggers.commands.warn("Someone tried to force the bot to execute code. Message: `" + UnUtil.escape(command) + " " + UnUtil.escape(String.join(" ", args)) + "`");
            return;
        }
        Constants.Loggers.commands.info(member + " issued the command `" + Constants.cmdChar + UnUtil.escape(command) + " " + UnUtil.escape(String.join(" ", args)) + "`");

        Command c = Commands.getCommandByInvocation(command);
        if ((c == null)) {
            channel.sendMessage("Command `" + Constants.cmdChar + UnUtil.escape(command) + "` was not found. Try `" + Constants.cmdChar + "help`").queue();
            Constants.Loggers.commands.info(member + " tried to execute the (unknown) command `" + Constants.cmdChar + UnUtil.escape(command) + "`.");
            return;
        }
        if (!member.hasPermission(channel, c.getRequiredServerPermission()) || (((1 | c.getRequiredClientPermission()) &
                Permissions.getPermissions(member.getUser())) != (1 | c.getRequiredClientPermission()))) {
            channel.sendMessage("The command `" + Constants.cmdChar + UnUtil.escape(command) + "` requires more than Permissions than you have.").queue();
            Constants.Loggers.commands.warn(member + " tried to execute the command `" + Constants.cmdChar + UnUtil.escape(command) + "` with too few permissions.");
            return;
        }
        if ((c.requiresBotChannel() && !UnUtil.isBotChannel(channel))) {
            channel.sendMessage("The command `" + Constants.cmdChar + UnUtil.escape(command) + "` can only be executed in bot channels.").queue();
            Constants.Loggers.commands.info(member + " tried to execute the command `" + Constants.cmdChar + UnUtil.escape(command) + "` in a not bot channel despite it is required");
            return;
        }
        if (!channel.getTopic().contains(c.getTopicRequirement()) && !Constants.allowAllCMDs.contains(guild.getId())) {
            channel.sendMessage("The command `" + Constants.cmdChar + UnUtil.escape(command) + "` requires the channel topic to match `" + c.getTopicRequirement() + "`").queue();
            Constants.Loggers.commands.info(member + " tried to execute the command `" + Constants.cmdChar + UnUtil.escape(command) + "` without the Channel topic (`" + channel.getTopic() + "`) containing the string `" + c.getTopicRequirement() + "`.");
            return;
        }
        Thread t = new Thread(() -> {
            c.execute(args, guild, channel, member, message);
            message.delete().queue();
        });
        t.setName("Command Executor Thread: CommandLine: \"" + message.getRawContent() + "\"");
        t.start();
    }
}
