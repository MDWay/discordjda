package de.romjaki.discord.jda.commands;

import de.romjaki.discord.jda.Command;
import de.romjaki.discord.jda.UnUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

import static de.romjaki.discord.jda.Main.jda;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by RGR on 02.09.2017.
 */
public class CommandSuggest implements Command {
    @Override
    public String getName() {
        return "suggest";
    }

    @Override
    public void execute(String[] args, Guild guild, TextChannel channel, Member member, Message message) {
        jda.asBot().getApplicationInfo().complete().getOwner().openPrivateChannel().complete().sendMessage(
                new EmbedBuilder()
                        .setTitle("FEATURE SUGGESTION")
                        .setColor(UnUtil.RandomUtils.randomColor())
                        .addField("Feature", String.join(" ", args), false)
                        .addField("Suggested by", member.getAsMention() + "(\\" + member.getAsMention() + "/" + member.getUser().getName() + "#" + member.getUser().getDiscriminator() + ")", false)
                        .build()).queue();
        channel.sendMessage(new EmbedBuilder()
                .setTitle("Suggestion has been saved!")
                .build()).queue(msg -> msg.delete().queueAfter(10, SECONDS));
    }

    @Override
    public boolean requiresBotChannel() {
        return false;
    }

    @Override
    public String getDescription() {
        return "Suggests a feature to " + jda.asBot().getApplicationInfo().complete().getOwner().getAsMention();
    }

    @Override
    public String getSyntax() {
        return "<feature>";
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
        return new Permission[0];
    }
}
