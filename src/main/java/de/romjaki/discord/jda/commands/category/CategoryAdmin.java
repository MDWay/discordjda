package de.romjaki.discord.jda.commands.category;

import de.romjaki.discord.jda.commands.Category;

/**
 * Created by RGR on 15.06.2017.
 */
public class CategoryAdmin implements Category {
    @Override
    public String getName() {
        return "admin";
    }

    @Override
    public String getDescription() {
        return "Admin stuff. You know!";
    }

    @Override
    public String getPrefix() {
        return "#";
    }
}
