package de.romjaki.discord.jda.webinterface;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.romjaki.discord.jda.Main.jda;

/**
 * Created by RGR on 22.08.2017.
 */
public class WMain {

    private static final String CHANNEL_FAILURE = "<html>" +
            "<head>" +
            "<title>FAILURE</title>" +
            "</head>" +
            "<body>" +
            "<h1 style=\"color:red\">Failed to send message.. check the channel id.</h1>" +
            "</body>" +
            "</head>";
    private static final String CHANNEL_FORMAT = "<li>%s/%s</li>";
    private static final String CHANNEL_TOTAL = "<html>" +
            "<head>" +
            "<title>SUCCESS</title>" +
            "</head>" +
            "<body>" +
            "<h1 style=\"color:green\">SUCCESS! SEND THE MESSAGE TO %d CHANNELS</h1>" +
            "<p>%s</p>" +
            "</body>" +
            "</head>";

    public static void createWebsocket() {
        try {
            HttpServer server = HttpServerProvider.provider().createHttpServer(new InetSocketAddress(8000), 0);
            server.createContext("/send").setHandler(httpExchange -> {
                Map<String, String> queryParams = parseQueryString(httpExchange.getRequestURI().getQuery());
                String response;
                do {
                    String channelId = queryParams.get("channel");
                    if (channelId == null) {
                        response = CHANNEL_FAILURE;
                        continue;
                    }
                    String text = queryParams.get("text");

                    List<TextChannel> channels = new ArrayList<>();
                    if (channelId.startsWith("bc")) {
                        Guild guild = jda.getGuildById(channelId.replaceFirst("bc", ""));
                        channels.addAll(guild.getTextChannels().stream().filter(TextChannel::canTalk).collect(Collectors.toList()));
                    } else {
                        TextChannel ch = jda.getTextChannelById(channelId);
                        if (ch != null) {
                            if (ch.canTalk()) {
                                channels.add(ch);
                            }
                        }
                    }
                    if (channels.isEmpty()) {
                        response = CHANNEL_FAILURE;
                        continue;
                    }
                    String chList = "";
                    for (Channel channel : channels) {
                        chList += String.format(CHANNEL_FORMAT, channel.getGuild().getName(), channel.getName());
                    }
                    channels.forEach(channel -> channel.sendMessage(text).queue());
                    response = String.format(CHANNEL_TOTAL, channels.size(), chList);
                } while (false);
                httpExchange.sendResponseHeaders(200, response.length());

                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            });
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Map<String, String> parseQueryString(String qs) {
        Map<String, String> result = new HashMap<>();
        if (qs == null)
            return result;

        int last = 0, next, l = qs.length();
        while (last < l) {
            next = qs.indexOf('&', last);
            if (next == -1)
                next = l;

            if (next > last) {
                int eqPos = qs.indexOf('=', last);
                try {
                    if (eqPos < 0 || eqPos > next)
                        result.put(URLDecoder.decode(qs.substring(last, next), "utf-8"), "");
                    else
                        result.put(URLDecoder.decode(qs.substring(last, eqPos), "utf-8"), URLDecoder.decode(qs.substring(eqPos + 1, next), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e); // will never happen, utf-8 support is mandatory for java
                }
            }
            last = next + 1;
        }
        return result;
    }
}
