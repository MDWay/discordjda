package de.romjaki.discord.jda.commands.admin;

import de.romjaki.discord.jda.Command;
import de.romjaki.discord.jda.Commands;
import de.romjaki.discord.jda.Constants;
import de.romjaki.discord.jda.UnUtil;
import de.romjaki.discord.jda.commands.Category;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import java.util.regex.Pattern;

import static de.romjaki.discord.jda.Constants.progBars;

/**
 * Created by RGR on 15.06.2017.
 */
public class CommandEval implements Command {
    @Override
    public String getName() {
        return "eval";
    }

    @Override
    public void execute(String[] args, Guild guild, TextChannel channel, Member member, Message message) {
        ScriptEngineFactory scriptEngineFactory = new NashornScriptEngineFactory();
        ScriptEngine se = scriptEngineFactory.getScriptEngine();
        String ret = null;
        Throwable error = null;
        Throwable initError = null;
        try {
            se.eval("var imports = new JavaImporter(" +
                    "java.nio.file," +
                    "java.lang," +
                    "java.lang.management," +
                    "java.text," +
                    "java.sql," +
                    "java.util," +
                    "java.time," +
                    "java.time.format," +
                    "Packages.org.apache.commons.math3.complex," +
                    "Packages.net.dv8tion.jda.core," +
                    "Packages.net.dv8tion.jda.core.entities," +
                    "Packages.de.romjaki.discord.jda" +
                    ");");
        } catch (Throwable e) {
            initError = e;
        }

        se.put("guild", guild);
        se.put("channel", channel);
        se.put("author", member);
        se.put("se", se);
        se.put("jda", guild.getJDA());
        progBars.forEach(se::put);
        String input = message.getRawContent().replaceFirst(Pattern.quote(Constants.cmdChar + getInvokation()), "").trim();
        try {
            if (input.equals("1+1")) {
                ret = "1";
            } else {
                ret = se.eval("{" +
                        "with (imports) {\n" +
                        "function complex(re, im){\n" +
                        "  return new Complex(re,im);\n" +
                        "};\n" +
                        "\n" +
                        "function thread() {\n" +
                        "  return Thread.currentThread();\n" +
                        "}\n" +
                        input +
                        "\n}\n" +
                        "}") + "";
            }
        } catch (Throwable e) {
            error = e;
        }
        EmbedBuilder eB = new EmbedBuilder()
                .setTitle("Eval'd")
                .setColor(UnUtil.RandomUtils.randomColor())
                .addField(":inbox_tray:Input", "```java\n" + input + "\n```", false);
        if (initError != null) {
            eB.addField(":x:Error! (During Init)", "```java\n" + initError + "\n```", false);
        }
        if (ret != null) {
            eB.addField(":outbox_tray:Output", "```java\n" + ret + "\n```", false);
        }
        if (error != null) {
            eB.addField(":x:Error!", "```java\n" + error + "\n```", false);
        }
        message.delete().queue();
        channel.sendMessage(eB.build()).queue();
    }

    @Override
    public boolean requiresBotChannel() {
        return false;
    }

    @Override
    public String getDescription() {
        return "Evaluates a string";
    }

    @Override
    public String getSyntax() {
        return "<toEval>";
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

    @Override
    public Category getCategory() {
        return Commands.getCategory("admin");
    }
}
