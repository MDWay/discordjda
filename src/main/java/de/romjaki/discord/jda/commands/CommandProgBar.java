package de.romjaki.discord.jda.commands;

import de.romjaki.discord.jda.Command;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

import static de.romjaki.discord.jda.Constants.progBars;

/**
 * Created by RGR on 16.06.2017.
 */
public class CommandProgBar implements Command {
    @Override
    public String getName() {
        return "prog";
    }

    @Override
    public void execute(String[] args, Guild guild, TextChannel channel, Member member, Message message) {
        int min = Integer.parseInt(args[0]);
        int max = Integer.parseInt(args[1]);
        int value = Integer.parseInt(args[2]);
        int delta = max - min;
        StringBuilder sb = new StringBuilder().append(min);
        sb.append(progBars.get(min == value ? "startempty" : "startfull"));
        for (int i = min + 1; i < max - 1; i++) {
            if (value == i) {
                sb.append(progBars.get("midswap"));
            } else if (i < value) {
                sb.append(progBars.get("midfull"));
            } else if (i > value) {
                sb.append(progBars.get("midempty"));
            }
        }
        sb.append(progBars.get(value == max ? "endfull" : "endempty")).append(max);
        channel.sendMessage(sb.toString()).queue();
    }

    @Override
    public boolean requiresBotChannel() {
        return false;
    }

    @Override
    public String getDescription() {
        return "Shows a *fancy* progress bar.";
    }

    @Override
    public String getSyntax() {
        return "<min> <max> <size>";
    }

    @Override
    public String getTopicRequirement() {
        return "allowProg";
    }

    @Override
    public int getRequiredClientPermission() {
        return 0;
    }

    @Override
    public Permission[] getRequiredServerPermission() {
        return new Permission[0];
    }
}
