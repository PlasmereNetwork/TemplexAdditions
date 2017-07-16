package net.ddns.templex;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.config.ConfigHandler;
import lombok.Getter;
import net.ddns.templex.chat.ChatListener;
import net.ddns.templex.commands.*;
import net.ddns.templex.commands.home.HomeCommand;
import net.ddns.templex.commands.home.HomeHandler;
import net.ddns.templex.commands.home.SetHomeCommand;
import net.ddns.templex.daemon.DaemonChatListener;
import net.ddns.templex.login.PlayerLoginListener;
import net.ddns.templex.ping.PingListener;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TemplexAdditionsPlugin extends Plugin {

    @Getter
    private final ExecutorService backgroundExecutor = Executors.newCachedThreadPool();
    @Getter
    private HomeHandler homeHandler;
    @Getter
    private ConfigHandler configHandler;
    @Getter
    private DaemonChatListener daemonChatListener;
    @Getter
    private PingListener pingListener;
    @Getter
    private PlayerLoginListener loginListener;
    @Getter
    private ChatListener chatListener;
    @Getter
    private List<TabbableCommand> addedCommands;

    @Override
    public void onEnable() {
        super.onEnable();
        this.homeHandler = new HomeHandler(this);
        this.configHandler = new ConfigHandler(this);
        this.daemonChatListener = new DaemonChatListener(this);
        this.pingListener = new PingListener();
        this.loginListener = new PlayerLoginListener(this);
        this.chatListener = new ChatListener(this);
        addedCommands = Arrays.asList(
                new BanIPCommand(),
                new DaemonizeCommand(),
                new EndCommand(this),
                new RTPCommand(),
                new SpawnCommand(this),
                new SurvivalCommand(),
                new TPACommand(),
                new HomeCommand(homeHandler),
                new SetHomeCommand(homeHandler)
        );
        for (TabbableCommand command : addedCommands) {
            getProxy().getPluginManager().registerCommand(this, command);
        }
        getProxy().getPluginManager().registerListener(this, homeHandler);
        getProxy().getPluginManager().registerListener(this, daemonChatListener);
        getProxy().getPluginManager().registerListener(this, pingListener);
        getProxy().getPluginManager().registerListener(this, loginListener);
        getProxy().getPluginManager().registerListener(this, chatListener);
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
