package io.github.templexmc;

import io.github.templexmc.commands.*;
import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.config.ConfigHandler;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

public class TemplexAdditionsPlugin extends Plugin {

    @Getter
    private ConfigHandler configHandler;

    @Override
    public void onEnable() {
        super.onEnable();
        TabbableCommand[] commands = new TabbableCommand[]{
                new HelloCommand(),
                new TPACommand(),
                new SurvivalCommand(),
                new SpawnCommand(this),
                new RTPCommand(this),
                new HomeCommand(this), // TODO
                new EndCommand(this),
                new SetHomeCommand(this), // TODO
        };
        for (TabbableCommand command : commands) {
            getProxy().getPluginManager().registerCommand(this, command);
        }
        this.configHandler = new ConfigHandler(this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        getProxy().getPluginManager().unregisterCommands(this);
        this.configHandler = null;
    }
}
