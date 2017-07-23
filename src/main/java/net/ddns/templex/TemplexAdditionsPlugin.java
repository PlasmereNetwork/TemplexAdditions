package net.ddns.templex;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.config.ConfigHandler;
import lombok.Getter;
import net.ddns.templex.chat.ChatListener;
import net.ddns.templex.commands.*;
import net.ddns.templex.commands.attribute.AttributeCommand;
import net.ddns.templex.commands.attribute.AttributeHandler;
import net.ddns.templex.commands.home.HomeCommand;
import net.ddns.templex.commands.home.HomeHandler;
import net.ddns.templex.commands.home.SetHomeCommand;
import net.ddns.templex.commands.team.TeamCommand;
import net.ddns.templex.commands.team.TeamHandler;
import net.ddns.templex.daemon.DaemonChatListener;
import net.ddns.templex.login.PlayerLoginListener;
import net.ddns.templex.ping.PingListener;
import net.ddns.templex.player.config.Specials;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TemplexAdditionsPlugin extends Plugin {

    @Getter
    private final ExecutorService backgroundExecutor = Executors.newCachedThreadPool();
    @Getter
    private AttributeHandler attributeHandler;
    @Getter
    private HomeHandler homeHandler;
    @Getter
    private ConfigHandler configHandler;
    @Getter
    private TeamHandler teamHandler;
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
        this.attributeHandler = new AttributeHandler(this);
        this.homeHandler = new HomeHandler(this);
        this.configHandler = new ConfigHandler(this);
        this.teamHandler = new TeamHandler(this);
        this.daemonChatListener = new DaemonChatListener(this);
        this.pingListener = new PingListener();
        this.loginListener = new PlayerLoginListener(this);
        this.chatListener = new ChatListener(this);
        registerCommands();
        getProxy().getPluginManager().registerListener(this, homeHandler);
        getProxy().getPluginManager().registerListener(this, daemonChatListener);
        getProxy().getPluginManager().registerListener(this, pingListener);
        getProxy().getPluginManager().registerListener(this, loginListener);
        getProxy().getPluginManager().registerListener(this, chatListener);
    }

    public void registerCommands() {
        addedCommands = Arrays.asList(
                new AttributeCommand(this),
                new BanIPCommand(),
                new DaemonizeCommand(),
                new DaemonSpeakCommand(),
                new EndCommand(this),
                new RefreshCommand(this),
                new RulesCommand(),
                new RTPCommand(),
                new SpawnCommand(this),
                new SurvivalCommand(),
                new TeamCommand(teamHandler),
                new TPACommand(),
                new TPAHereCommand(),
                new HomeCommand(homeHandler),
                new SetHomeCommand(homeHandler),
                new FireworkCommand(),
                new TutorialCommand(),
                new TauntCommand()
        );
        for (TabbableCommand command : addedCommands) {
            getProxy().getPluginManager().registerCommand(this, command);
        }
        Specials.assignSpecialAttributes(this); // TODO find a better place for this.
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
