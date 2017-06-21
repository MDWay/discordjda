package de.romjaki.discord.jda.commands.category;

import de.romjaki.discord.jda.commands.Category;

/**
 * Created by RGR on 19.06.2017.
 */
public class CategoryImages implements Category {
    @Override
    public String getName() {
        return "images";
    }

    @Override
    public String getDescription() {
        return "General images / comics";
    }

    @Override
    public String getPrefix() {
        return "i";
    }
}
