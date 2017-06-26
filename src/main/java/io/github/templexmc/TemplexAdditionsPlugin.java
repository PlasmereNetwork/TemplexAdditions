package io.github.templexmc;

import io.github.templexmc.commands.*;
import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.config.ConfigHandler;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

public class TemplexAdditionsPlugin extends Plugin {

    @Getter
    private ConfigHandler handler;

    @Override
    public void onEnable() {
        super.onEnable();
        TabbableCommand[] commands = new TabbableCommand[]{
                new HelloCommand(),
                new TPACommand(),
                new SurvivalCommand(),
                new SpawnCommand(),
                new RTPCommand(),
                // new HomeCommand(), TODO
                new EndCommand(),
                // new SetHomeCommand(), TODO
        };
        for (TabbableCommand command : commands) {
            getProxy().getPluginManager().registerCommand(this, command);
        }
        this.handler = new ConfigHandler(this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        getProxy().getPluginManager().unregisterCommands(this);
        this.handler = null;
    }
}
