package net.ddns.templex.commands.home;

import com.google.common.util.concurrent.FutureCallback;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import lombok.NonNull;
import net.ddns.templex.game.CoordinateTriad;
import net.ddns.templex.handlers.DaemonChatListener;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collections;

public class SetHomeCommand extends TabbableCommand {

    private final HomeHandler handler;

    public SetHomeCommand(@NonNull HomeHandler handler) {
        super("sethome");
        this.handler = handler;
    }

    @Override
    public void execute(CommandSender commandSender, final String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer) || !Daemon.hasInstance()) {
            return;
        }
        final ProxiedPlayer player = (ProxiedPlayer) commandSender;
        final String name;
        if (strings.length == 0) {
            name = "home";
        } else {
            name = strings[0];
        }
        handler.getPlugin().getDaemonChatListener().await(new DaemonChatListener.Matcher() {
            @Override
            public boolean matches(JsonObject jsonObject) {
                try {
                    return jsonObject.getAsJsonArray("with").get(0).getAsString()
                            .equals(Daemon.getInstance().getPlayer().getName()) // obj.with[0] == "VADaemon"
                            && jsonObject.getAsJsonArray("with").get(1).getAsJsonObject()
                            .get("translate").getAsString()
                            .equals("commands.tp.success.coordinates") // obj.with[1].translate == "commands.tp.success.coordinates"
                            && jsonObject.getAsJsonArray("with").get(1).getAsJsonObject()
                            .getAsJsonArray("with").get(0).getAsString()
                            .equals(player.getName()); // obj.with[1].with[0] == playername
                } catch (InterruptedException e) {
                    return false;
                }
            }
        }, new FutureCallback<JsonObject>() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                JsonArray coords = jsonObject.getAsJsonArray("with").get(1).getAsJsonObject()
                        .getAsJsonArray("with"); // [playername, x, y, z]
                CoordinateTriad triad = new CoordinateTriad(
                        coords.get(1).getAsDouble(),
                        coords.get(2).getAsDouble(),
                        coords.get(3).getAsDouble()
                );
                HomeSet set = handler.getActiveHomes().get(player);
                set.getHomes().put(name, triad);
                player.sendMessage(new ComponentBuilder(String.format("Home '%s' was successfully created!", name)).color(ChatColor.GREEN).create());
                handler.saveHomeSet(set);
            }

            @Override
            public void onFailure(Throwable throwable) {
                player.sendMessage(new ComponentBuilder(String.format("Home '%s' could not be set. Please contact an administrator.", name)).color(ChatColor.RED).create());
            }
        });
        try {
            Daemon.getInstance().submitCommands(Collections.singletonList(String.format("/execute @s ~ ~ ~ tp %s ~ ~ ~", commandSender.getName())));
        } catch (InterruptedException ignored) {
        }
    }
}
