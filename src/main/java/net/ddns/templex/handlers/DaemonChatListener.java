package net.ddns.templex.handlers;

import com.google.common.util.concurrent.FutureCallback;
import io.github.trulyfree.va.events.DaemonChatEvent;
import lombok.AllArgsConstructor;
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

    private final TemplexAdditionsPlugin plugin;
    private final ConcurrentHashMap<StringEqualizer, FutureCallback<String>> awaiting;
    private final ScheduledExecutorService timeoutHandler;

    public DaemonChatListener(final @NonNull TemplexAdditionsPlugin plugin) {
        this.plugin = plugin;
        this.awaiting = new ConcurrentHashMap<>();
        this.timeoutHandler = Executors.newSingleThreadScheduledExecutor();
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onDaemonChatEvent(DaemonChatEvent event) {
        for (FutureCallback<String> found; (found = awaiting.remove(new StringEqualizer(event.getMessage()))) != null; ) {
            found.onSuccess(event.getMessage());
        }
    }

    public void await(@NonNull final String toMatch, @NonNull final FutureCallback<String> callback) {
        final StringEqualizer equalizer = new StringEqualizer(toMatch);
        awaiting.put(equalizer, callback);
        timeoutHandler.schedule(new Runnable() {
            @Override
            public void run() {
                if (awaiting.remove(equalizer, callback)) {
                    callback.onFailure(null);
                }
            }
        }, 10, TimeUnit.SECONDS);
    }

    @AllArgsConstructor
    private class StringEqualizer {

        private final @NonNull
        String toMatch;

        public boolean equals(Object object) {
            return object instanceof StringEqualizer && ((StringEqualizer) object).toMatch.matches(this.toMatch);
        }

    }

}
