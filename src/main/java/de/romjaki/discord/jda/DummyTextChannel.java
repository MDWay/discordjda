package de.romjaki.discord.jda;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.managers.ChannelManager;
import net.dv8tion.jda.core.managers.ChannelManagerUpdatable;
import net.dv8tion.jda.core.requests.RestAction;
import net.dv8tion.jda.core.requests.restaction.AuditableRestAction;
import net.dv8tion.jda.core.requests.restaction.InviteAction;
import net.dv8tion.jda.core.requests.restaction.PermissionOverrideAction;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static de.romjaki.discord.jda.Main.jda;

/**
 * Created by RGR on 24.06.2017.
 */
public class DummyTextChannel implements TextChannel {
    @Override
    public String getTopic() {
        return "";
    }

    @Override
    public boolean isNSFW() {
        return false;
    }

    @Override
    public RestAction<List<Webhook>> getWebhooks() {
        return new RestAction.EmptyRestAction<>(jda, Collections.emptyList());
    }

    @Override
    public AuditableRestAction<Void> deleteMessages(Collection<Message> messages) {
        return new AuditableRestAction.EmptyRestAction<>(jda, null);
    }

    @Override
    public AuditableRestAction<Void> deleteMessagesByIds(Collection<String> messageIds) {
        return new AuditableRestAction.EmptyRestAction<>(jda, null);
    }

    @Override
    public AuditableRestAction<Void> deleteWebhookById(String id) {
        return new AuditableRestAction.EmptyRestAction<>(jda, null);
    }

    @Override
    public AuditableRestAction<Void> clearReactionsById(String messageId) {
        return new AuditableRestAction.EmptyRestAction<>(jda, null);
    }

    @Override
    public boolean canTalk() {
        return true;
    }

    @Override
    public boolean canTalk(Member member) {
        return member.equals(jda.getSelfUser());
    }

    @Override
    public int compareTo(TextChannel o) {
        return 0;
    }

    @Override
    public ChannelType getType() {
        return ChannelType.TEXT;
    }

    @Override
    public long getLatestMessageIdLong() {
        return 0;
    }

    @Override
    public boolean hasLatestMessage() {
        return false;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public Guild getGuild() {
        return null;
    }

    @Override
    public List<Member> getMembers() {
        return Collections.emptyList();
    }

    @Override
    public int getPosition() {
        return 0;
    }

    @Override
    public int getPositionRaw() {
        return 0;
    }

    @Override
    public JDA getJDA() {
        return null;
    }

    @Override
    public PermissionOverride getPermissionOverride(Member member) {
        return null;
    }

    @Override
    public PermissionOverride getPermissionOverride(Role role) {
        return null;
    }

    @Override
    public List<PermissionOverride> getPermissionOverrides() {
        return Collections.emptyList();
    }

    @Override
    public List<PermissionOverride> getMemberPermissionOverrides() {
        return Collections.emptyList();
    }

    @Override
    public List<PermissionOverride> getRolePermissionOverrides() {
        return Collections.emptyList();
    }

    @Override
    public ChannelManager getManager() {
        return null;
    }

    @Override
    public ChannelManagerUpdatable getManagerUpdatable() {
        return null;
    }

    @Override
    public AuditableRestAction<Void> delete() {
        return new AuditableRestAction.EmptyRestAction<>(jda, null);
    }

    @Override
    public PermissionOverrideAction createPermissionOverride(Member member) {
        return null;
    }

    @Override
    public PermissionOverrideAction createPermissionOverride(Role role) {
        return null;
    }

    @Override
    public InviteAction createInvite() {
        return null;
    }

    @Override
    public RestAction<List<Invite>> getInvites() {
        return new RestAction.EmptyRestAction<>(jda, Collections.emptyList());
    }

    @Override
    public String getAsMention() {
        return "";
    }

    @Override
    public long getIdLong() {
        return 0;
    }
}
