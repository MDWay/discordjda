package de.romjaki.discord.jda.commands.category;

import de.romjaki.discord.jda.commands.Category;

/**
 * Created by RGR on 19.06.2017.
 */
public class CategoryWebContent implements Category {
    @Override
    public String getName() {
        return "web";
    }

    @Override
    public String getDescription() {
        return "Anything that makes calls to external sites.";
    }

    @Override
    public String getPrefix() {
        return "i";
    }
}
