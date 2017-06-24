package io.github.templexmc;

import io.github.templexmc.commands.*;
import io.github.trulyfree.va.command.commands.TabbableCommand;
import net.md_5.bungee.api.plugin.Plugin;

public class TemplexAdditionsPlugin extends Plugin {
    @Override
    public void onEnable() {
        super.onEnable();
        TabbableCommand[] commands = new TabbableCommand[]{
                new HelloCommand(),
                new TPACommand(),
                new Survival(),
                new Spawn(),
                new RTP(),
                new Home(),
                new End(),
                new SetHome(),
        };
        for (TabbableCommand command : commands) {
            getProxy().getPluginManager().registerCommand(this, command);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        getProxy().getPluginManager().unregisterCommands(this);
    }
}
