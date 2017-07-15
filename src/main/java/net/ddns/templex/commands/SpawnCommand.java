package net.ddns.templex.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import lombok.NonNull;
import net.ddns.templex.TemplexAdditionsPlugin;
import net.ddns.templex.world.CoordinateTriad;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.io.IOException;
import java.util.Collections;

/**
 * Tps players to SpawnCommand.
 */
public class SpawnCommand extends TabbableCommand {

    private final CoordinateTriad spawn;

    public SpawnCommand(@NonNull TemplexAdditionsPlugin plugin) {
        super("spawn");
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
            commandSender.sendMessage(new ComponentBuilder("Spawn coordinates were not specified! Contact an administrator.").color(ChatColor.RED).create());
            return;
        }
        try {
            Daemon.getInstance().submitCommands(Collections.singletonList("/tp " + commandSender.getName() + " " + spawn));
            commandSender.sendMessage(new ComponentBuilder("You have been successfully tped to Spawn!").color(ChatColor.GREEN).create());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {
        Util.pushAutocompletePlayers(event);
    }

}
