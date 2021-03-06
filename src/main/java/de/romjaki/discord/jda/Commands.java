package de.romjaki.discord.jda;

import de.romjaki.discord.jda.commands.Category;
import net.dv8tion.jda.core.JDA;

import java.util.*;

/**
 * Created by RGR on 19.05.2017.
 */
public class Commands {
    private static Set<Command> commands = new HashSet<>();
    private static Set<Category> categories = new HashSet<>();

    private Commands() {
        UnUtil.singleton(Commands.class);
    }

    public static Collection<Command> getCommands() {
        return Collections.unmodifiableCollection(commands);
    }

    public static boolean addCommand(Command command) {
        return commands.add(command);
    }

    public static Command getCommand(String name) {
        Objects.requireNonNull(name);
        return getCommands().stream().filter(com -> com.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }


    public static Collection<Category> getCategories() {
        return Collections.unmodifiableCollection(categories);
    }

    public static Category getCategory(String string) {
        Objects.requireNonNull(string);
        return getCategories().stream().filter(cat -> cat.getName().equalsIgnoreCase(string)).findFirst().orElse(null);
    }

    public static void addCategory(Category category) {
        categories.add(category);
    }

    public static Command getCommandByInvocation(String command) {
        Objects.requireNonNull(command);
        return getCommands().stream().filter(c -> c.getInvokation().equalsIgnoreCase(command)).findFirst().orElse(null);
    }

    public static void registerHandles(JDA jda) {
        getCommands().forEach(cmd -> cmd.register(jda));
    }
}
