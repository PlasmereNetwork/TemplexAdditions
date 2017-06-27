package net.ddns.templex.commands.home;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import lombok.NonNull;
import net.ddns.templex.commands.Util;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

/**
 * TODO
 */
public class SetHomeCommand extends TabbableCommand {

    private final HomeHandler handler;

    public SetHomeCommand(@NonNull HomeHandler handler) {
        super("sethome", "templex.sethome", "sh");
        this.handler = handler;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) commandSender;

        // TODO
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {
        Util.pushAutocompletePlayers(event);
    }

}
