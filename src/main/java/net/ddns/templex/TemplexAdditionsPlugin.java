package net.ddns.templex;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.config.ConfigHandler;
import lombok.Getter;
import net.ddns.templex.commands.*;
import net.ddns.templex.commands.home.HomeCommand;
import net.ddns.templex.commands.home.HomeHandler;
import net.ddns.templex.commands.home.SetHomeCommand;
import net.ddns.templex.handlers.DaemonChatListener;
import net.md_5.bungee.api.plugin.Plugin;

public class TemplexAdditionsPlugin extends Plugin {

    @Getter
    private HomeHandler homeHandler;

    @Getter
    private ConfigHandler configHandler;

    @Getter
    private DaemonChatListener daemonChatListener;

    @Override
    public void onEnable() {
        super.onEnable();
        homeHandler = new HomeHandler(this);
        daemonChatListener = new DaemonChatListener(this);
        this.configHandler = new ConfigHandler(this);
        TabbableCommand[] commands = new TabbableCommand[]{
                new HelloCommand(),
                new TPACommand(),
                new SurvivalCommand(),
                new SpawnCommand(this),
                new RTPCommand(this),
                new HomeCommand(homeHandler), // TODO
                new EndCommand(this),
                new SetHomeCommand(homeHandler), // TODO
        };
        for (TabbableCommand command : commands) {
            getProxy().getPluginManager().registerCommand(this, command);
        }
        getProxy().getPluginManager().registerListener(this, homeHandler);
        getProxy().getPluginManager().registerListener(this, daemonChatListener);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        getProxy().getPluginManager().unregisterCommands(this);
        homeHandler.saveAllHomes();
        getProxy().getPluginManager().unregisterListeners(this);
        this.configHandler = null;
    }
}
