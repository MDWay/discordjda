package de.romjaki.discord.jda;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created by RGR on 19.05.2017.
 */
public class Commands {
    private static Set<Command> commands = new HashSet<>();

    @Contract(" -> fail")
    private Commands() {
        UnUtil.singleton(Commands.class);
    }

    @NotNull
    public static Set<Command> getCommands() {
        return Collections.unmodifiableSet(commands);
    }

    public static boolean addCommand(Command command) {
        return commands.add(command);
    }

    @Nullable
    public static Command getCommand(String name) {
        Objects.requireNonNull(name);
        for (Command c : getCommands()) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }


}
