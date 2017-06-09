package de.romjaki.discord.jda.commands;

import de.romjaki.discord.jda.Command;
import de.romjaki.discord.jda.UnUtil;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by RGR on 20.05.2017.
 */
public class CommandCountdown implements Command {
    @Override
    public String getName() {
        return "cd";
    }

    @Override
    public void execute(String[] args, Guild guild, TextChannel channel, Member member, Message message) {
        if (args.length != 1) {
            return;
        }
        int i = 10;
        try {
            i = UnUtil.clamp(1, 60, Integer.valueOf(args[0]));
        } catch (Exception e) {
            channel.sendMessage(args[0] + " isn't a valid number. Using 10");
        }
        int f = i;
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            int j = f;

            @Override
            public void run() {
                j--;
                channel.sendMessage(j + "").queue();
                if (j < 1) {
                    t.cancel();
                }
            }
        }, 1000, 1000);
    }


    @Override
    public boolean requiresBotChannel() {
        return true;
    }

    @Override
    public String getDescription() {
        return "counts down from input to 0";
    }

    @Override
    public String getSyntax() {
        return "<second>";
    }

    @Override
    public String getTopicRequirement() {
        return "allowCD";
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
