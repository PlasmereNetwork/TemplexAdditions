package net.ddns.templex.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import lombok.NonNull;
import net.ddns.templex.TemplexAdditionsPlugin;
import net.ddns.templex.world.CoordinateTriad;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.io.IOException;
import java.util.Collections;

/**
 * Tps players to SpawnCommand.
 */
public class SpawnCommand extends TabbableCommand {

    private final BaseComponent[] NO_COORDS = new ComponentBuilder("Spawn coordinates were not specified! Contact an administrator.").color(ChatColor.RED).create();

    private final CoordinateTriad spawn;

    public SpawnCommand(@NonNull TemplexAdditionsPlugin plugin) {
        super("spawn", "nonop");
        CoordinateTriad spawn;
        try {
            spawn = plugin.getConfigHandler().getConfig("end.json", CoordinateTriad.class);
        } catch (IOException e) {
            spawn = null;
            e.printStackTrace();
        }
        this.spawn = spawn;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            return;
        }
        if (spawn == null) {
            commandSender.sendMessage(NO_COORDS);
            return;
        }
        try {
            Daemon.getInstance().submitCommands(Collections.singletonList(String.format("/tp %s %s", commandSender.getName(), spawn)));
            CommandUtil.tellOps(
                    new ComponentBuilder("Spawn PL ").color(ChatColor.GOLD)
                            .append(": ").color(ChatColor.DARK_GRAY)
                            .append("Successfully teleported ").color(ChatColor.RED)
                            .append(commandSender.getName()).color(ChatColor.GRAY)
                            .append(" to Spawn!").color(ChatColor.RED).create()
            );
            commandSender.sendMessage(new ComponentBuilder("You have been successfully teleported to Spawn!").color(ChatColor.GREEN).create());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {
        CommandUtil.pushAutocompletePlayers(event);
    }

}
