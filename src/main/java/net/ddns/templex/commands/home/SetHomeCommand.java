package net.ddns.templex.commands.home;

import com.google.common.util.concurrent.FutureCallback;
import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import lombok.NonNull;
import net.ddns.templex.commands.Util;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.Collections;

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
        if (!(commandSender instanceof ProxiedPlayer) || !Daemon.hasInstance()) {
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        handler.getPlugin().getDaemonChatListener().await("" /* TODO */, new FutureCallback<String>() {
            @Override
            public void onSuccess(String s) {
                // TODO
            }

            @Override
            public void onFailure(Throwable throwable) {
                // TODO
            }
        });
        try {
            Daemon.getInstance().submitCommands(Collections.singletonList(String.format("/tp %s ~ ~ ~", commandSender.getName())));
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {
        Util.pushAutocompletePlayers(event);
    }

}
