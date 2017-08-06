package net.ddns.templex.commands.home;

import com.google.common.util.concurrent.FutureCallback;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import lombok.NonNull;
import net.ddns.templex.commands.CommandUtil;
import net.ddns.templex.daemon.DaemonChatListener;
import net.ddns.templex.world.CoordinateTriad;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collections;
import java.util.Objects;

public class SetHomeCommand extends TabbableCommand {

    private final HomeHandler handler;

    public SetHomeCommand(@NonNull HomeHandler handler) {
        super("sethome", "nonop");
        this.handler = handler;
    }

    @Override
    public void execute(CommandSender commandSender, final String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer) || !Daemon.hasInstance()) {
            return;
        }
        final ProxiedPlayer player = (ProxiedPlayer) commandSender;
        final String name;
        final Daemon instance = Daemon.getInstanceNow();
        if (instance == null) {
            CommandUtil.daemonNotFound(commandSender);
            return;
        }
        if (strings.length == 0) {
            name = "home";
            instance.submitCommands(Collections.singletonList(String.format("/execute %s ~ ~ ~ spawnpoint ~ ~ ~ @s", commandSender.getName())));
        } else {
            name = strings[0];
            if (Objects.equals(name, "base") || Objects.equals(name, "home") || Objects.equals(name, "Base") || Objects.equals(name, "Home")) {
                instance.submitCommands(Collections.singletonList(String.format("/execute %s ~ ~ ~ spawnpoint ~ ~ ~ @s", commandSender.getName())));
            }
        }
        handler.getPlugin().getDaemonChatListener().await(new DaemonChatListener.Matcher() {
            @Override
            public boolean matches(JsonObject jsonObject) {
                return jsonObject.getAsJsonArray("with").get(0).getAsString()
                        .equals(instance.getPlayer().getName()) // obj.with[0] == "VADaemon"
                        && jsonObject.getAsJsonArray("with").get(1).getAsJsonObject()
                        .get("translate").getAsString()
                        .equals("commands.tp.success.coordinates") // obj.with[1].translate == "commands.tp.success.coordinates"
                        && jsonObject.getAsJsonArray("with").get(1).getAsJsonObject()
                        .getAsJsonArray("with").get(0).getAsString()
                        .equals(player.getName()); // obj.with[1].with[0] == playername
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
        instance.submitCommands(Collections.singletonList(String.format("/execute @s ~ ~ ~ tp %s ~ ~ ~", commandSender.getName())));
    }
}
