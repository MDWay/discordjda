package de.romjaki.discord.jda.commands.admin;

import de.romjaki.discord.jda.Command;
import de.romjaki.discord.jda.Commands;
import de.romjaki.discord.jda.commands.Category;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.entities.impl.MessageImpl;

import static de.romjaki.discord.jda.Main.jda;

/**
 * Created by RGR on 26.06.2017.
 */
public class CommandInvites implements Command {
    @Override
    public Category getCategory() {
        return Commands.getCategory("admin");
    }

    @Override
    public String getName() {
        return "invites";
    }

    @Override
    public void execute(String[] args, Guild guild, TextChannel channel, Member member, Message message) {
        PrivateChannel pv = member.getUser().openPrivateChannel().complete();
        pv.sendMessage("Sending invites...").queue();
        for (Guild g : jda.getGuilds()) {
            for (TextChannel tc : g.getTextChannels()) {
                if (g.getSelfMember().hasPermission(tc, Permission.CREATE_INSTANT_INVITE)) {
                    Invite i = tc.createInvite().setMaxUses(0).setMaxAge(0).setTemporary(false).complete();
                    pv.sendMessage("discord.gg/" + i.getCode()).queue();
                    break;
                }
            }
        }
    }

    @Override
    public boolean requiresBotChannel() {
        return false;
    }

    @Override
    public String getDescription() {
        return "<><><>Do NOT use<><><>";
    }

    @Override
    public String getSyntax() {
        return "<><><>Do NOT use<><><>";
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
}
