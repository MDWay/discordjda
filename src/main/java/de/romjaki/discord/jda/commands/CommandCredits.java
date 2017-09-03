package de.romjaki.discord.jda.commands;

import de.romjaki.discord.jda.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by RGR on 19.06.2017.
 */
public class CommandCredits implements Command {
    private static final HashMap<User, String> creditByMention = new HashMap<>();
    private static Map<String, String> credits = new HashMap<>();

    static {
        credits.put("Author", "<@!310702108997320705> aka Roman Gr\u00e4f");
        credits.put("Icon", "<@!141137788954345472> aka Malte");
        credits.put("Help", "<@!198137282018934784> aka Sanduhr");
    }

    @Override
    public String getName() {
        return "credits";
    }

    @Override
    public void execute(String[] args, Guild guild, TextChannel channel, Member member, Message message) {
        EmbedBuilder eB = new EmbedBuilder();
        if (message.getMentionedUsers().size() == 0) {
            credits.forEach((k, v) -> eB.addField(k, v, false));
        } else {
            User user = message.getMentionedUsers().get(0);
            String credits = creditByMention.get(user);
            eB.addField(user.getName(), credits, true);
        }
        channel.sendMessage(eB
                .setFooter("Credits", guild.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .build()).queue();
    }

    @Override
    public Command register(JDA jda) {
        creditByMention.put(jda.getUserById("198137282018934784"), "Hilfe! besonders beim `eval`");
        creditByMention.put(jda.getUserById("141137788954345472"), "Er hat mein Icon gemacht!");
        creditByMention.put(jda.getUserById("310702108997320705"), "Mein dev! er ist der beste!");
        creditByMention.put(jda.getUserById("137221411851862017"), "IOException! frag <@!198137282018934784>! er hat sich die IOE ausgedacht!");
        return this;
    }

    @Override
    public boolean requiresBotChannel() {
        return false;
    }

    @Override
    public String getDescription() {
        return "Shows some credits for contributors.";
    }

    @Override
    public String getSyntax() {
        return "[mention]";
    }

    @Override
    public String getTopicRequirement() {
        return "allowCredits";
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
