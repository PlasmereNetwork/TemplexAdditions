package net.ddns.templex.commands.home;

import lombok.Getter;
import lombok.NonNull;
import net.ddns.templex.TemplexAdditionsPlugin;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.*;

public final class HomeHandler implements Listener {

    @Getter
    private final TemplexAdditionsPlugin plugin;
    private final ExecutorService homeSaverThread;

    @Getter
    private final ConcurrentMap<ProxiedPlayer, HomeSet> activeHomes;

    public HomeHandler(@NonNull TemplexAdditionsPlugin plugin) {
        this.plugin = plugin;
        this.homeSaverThread = Executors.newSingleThreadExecutor();
        this.activeHomes = new ConcurrentHashMap<>();
    }

    public HomeSet loadHomeSet(final ProxiedPlayer owner) throws ExecutionException, InterruptedException {
        return this.homeSaverThread.submit(new Callable<HomeSet>() {
            @Override
            public HomeSet call() throws Exception {
                HomeSet set = null;
                try {
                    set = plugin.getConfigHandler().getConfig(String.format("homes%s%s.json", File.separator, owner.toString()), HomeSet.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return set;
            }
        }).get();
    }

    public void saveHomeSet(final @NonNull HomeSet homes) {
        this.homeSaverThread.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    new File(plugin.getDataFolder(), "homes").mkdirs();
                    plugin.getConfigHandler().saveConfig(String.format("homes%s%s.json", File.separator, homes.getOwner().toString()), homes);
                } catch (IOException ignored) {
                }
            }
        });
    }

    public void saveAllHomes() {
        for (HomeSet homes : activeHomes.values()) {
            saveHomeSet(homes);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onChatEvent(ChatEvent event) {
        plugin.getLogger().info(event.getMessage());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPostLoginEvent(PostLoginEvent event) {
        HomeSet set = null;
        try {
            set = loadHomeSet(event.getPlayer());
        } catch (ExecutionException | InterruptedException ignored) {
        }
        if (set == null) {
            set = new HomeSet(event.getPlayer().getUniqueId());
        }
        activeHomes.put(event.getPlayer(), set);
        saveHomeSet(set);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerDisconnectEvent(PlayerDisconnectEvent event) {
        saveHomeSet(activeHomes.remove(event.getPlayer()));
    }

}
