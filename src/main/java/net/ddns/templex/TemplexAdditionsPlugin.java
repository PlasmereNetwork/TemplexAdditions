package net.ddns.templex;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.config.ConfigHandler;
import lombok.Getter;
import net.ddns.templex.commands.*;
import net.ddns.templex.commands.home.HomeCommand;
import net.ddns.templex.commands.home.HomeHandler;
import net.ddns.templex.commands.home.SetHomeCommand;
import net.ddns.templex.daemon.DaemonChatListener;
import net.ddns.templex.ping.PingListener;
import net.md_5.bungee.api.plugin.Plugin;

public class TemplexAdditionsPlugin extends Plugin {

    @Getter
    private HomeHandler homeHandler;

    @Getter
    private ConfigHandler configHandler;

    @Getter
    private DaemonChatListener daemonChatListener;

    @Getter
    private PingListener pingListener;

    @Override
    public void onEnable() {
        super.onEnable();
        homeHandler = new HomeHandler(this);
        daemonChatListener = new DaemonChatListener(this);
        pingListener = new PingListener();
        this.configHandler = new ConfigHandler(this);
        TabbableCommand[] commands = new TabbableCommand[]{
                new EndCommand(this),
                new RTPCommand(this),
                new SpawnCommand(this),
                new SurvivalCommand(),
                new TPACommand(),
                new HomeCommand(homeHandler), // TODO
                new SetHomeCommand(homeHandler), // TODO
        };
        for (TabbableCommand command : commands) {
            getProxy().getPluginManager().registerCommand(this, command);
        }
        getProxy().getPluginManager().registerListener(this, homeHandler);
        getProxy().getPluginManager().registerListener(this, daemonChatListener);
        getProxy().getPluginManager().registerListener(this, pingListener);
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
