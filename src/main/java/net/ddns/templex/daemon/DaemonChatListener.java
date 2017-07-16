package net.ddns.templex.daemon;

import com.google.common.util.concurrent.FutureCallback;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.trulyfree.va.events.DaemonChatEvent;
import lombok.NonNull;
import net.ddns.templex.TemplexAdditionsPlugin;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DaemonChatListener implements Listener {

    private final JsonParser parser;
    private final TemplexAdditionsPlugin plugin;
    private final ConcurrentHashMap<Matcher, FutureCallback<JsonObject>> awaiting;
    private final ScheduledExecutorService timeoutHandler;

    public DaemonChatListener(final @NonNull TemplexAdditionsPlugin plugin) {
        this.parser = new JsonParser();
        this.plugin = plugin;
        this.awaiting = new ConcurrentHashMap<>();
        this.timeoutHandler = Executors.newSingleThreadScheduledExecutor();
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onDaemonChatEvent(final DaemonChatEvent event) {
        plugin.getBackgroundExecutor().submit(new Runnable() {
            @Override
            public void run() {
                plugin.getLogger().info(event.getMessage());
                JsonObject json = parser.parse(event.getMessage()).getAsJsonObject();
                for (ConcurrentHashMap.Entry<Matcher, FutureCallback<JsonObject>> entry : awaiting.entrySet()) {
                    if (entry.getKey().matches(json)) {
                        awaiting.remove(entry.getKey()).onSuccess(json);
                    }
                }
            }
        });
    }

    public void await(@NonNull final Matcher matcher, @NonNull final FutureCallback<JsonObject> callback) {
        awaiting.put(matcher, callback);
        timeoutHandler.schedule(new Runnable() {
            @Override
            public void run() {
                if (awaiting.remove(matcher, callback)) {
                    callback.onFailure(null);
                }
            }
        }, 10, TimeUnit.SECONDS);
    }

    public interface Matcher {
        boolean matches(JsonObject object);
    }

}
