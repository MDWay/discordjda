package de.romjaki.discord.jda.commands.category;

import de.romjaki.discord.jda.commands.Category;

/**
 * Created by RGR on 11.06.2017.
 */
public class CategoryDefault implements Category {
    @Override
    public String getName() {
        return "Default";
    }

    @Override
    public String getDescription() {
        return "Normal commands without any special thingis";
    }

    @Override
    public String getPrefix() {
        return "";
    }
}
