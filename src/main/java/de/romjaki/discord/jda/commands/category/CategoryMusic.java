package de.romjaki.discord.jda.commands.category;

import de.romjaki.discord.jda.commands.Category;

/**
 * Created by RGR on 11.06.2017.
 */
public class CategoryMusic implements Category {
    @Override
    public String getName() {
        return "music";
    }

    @Override
    public String getDescription() {
        return "General Music";
    }

    @Override
    public String getPrefix() {
        return "m";
    }
}
