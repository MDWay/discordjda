package de.romjaki.discord.jda.commands;

import de.romjaki.discord.jda.Command;
import de.romjaki.discord.jda.Commands;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by RGR on 11.06.2017.
 */
public interface Category {
    String getName();

    String getDescription();

    String getPrefix();

    default Collection<Command> getCommands() {
        return Commands.getCommands().stream().filter(com -> com.getCategory() == this).collect(Collectors.toSet());
    }
}
