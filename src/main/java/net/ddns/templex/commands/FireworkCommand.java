package net.ddns.templex.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.Collections;

public class FireworkCommand extends TabbableCommand {
    public FireworkCommand() {super("firework","nonop","fw");}

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            return;
        }
        try {
            Daemon.getInstance().submitCommands(Collections.singletonList("/execute @p[name=" + commandSender + ",tag=special] ~ ~ ~ summon fireworks_rocket ~ ~ ~ {LifeTime:40,Damage:0s}"));
            Daemon.getInstance().submitCommands(Collections.singletonList("/tellraw @p[name=" + commandSender + ",tag=!special] [{\"text\":\"You do not meet the required specifications to submit this command!\",\"color\":\"red\"}]"));
            Daemon.getInstance().submitCommands(Collections.singletonList("/tellraw @p[name=" + commandSender + ",tag=special] [{\"text\":\"Successfully sent off a rocket!\",\"color\":\"green\"}]"));
            Daemon.getInstance().submitCommands(Collections.singletonList("/execute @p[name=" + commandSender + ",tag=special] [{\"text\":\"Firework PL\":\"color\":\"gold\"},{\"text\":\" : \",\"color\":\"dark_gray\"},{\"selector\":\"@s\"},{\"text\":\" just sent off a rocket!\",\"color\":\"red\"}]"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {
        CommandUtil.pushAutocompletePlayers(event);
    }
}
