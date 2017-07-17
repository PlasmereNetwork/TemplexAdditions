package net.ddns.templex.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import net.ddns.templex.TemplexAdditionsPlugin;
import net.md_5.bungee.api.CommandSender;

public class RefreshCommand extends TabbableCommand {

    private final TemplexAdditionsPlugin plugin;

    public RefreshCommand(TemplexAdditionsPlugin plugin) {
        super("refresh", "templex.op");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        plugin.getProxy().getPluginManager().unregisterCommands(plugin);
        plugin.registerCommands();
    }
}
