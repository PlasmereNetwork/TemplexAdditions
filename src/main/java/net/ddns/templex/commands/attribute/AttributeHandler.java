package net.ddns.templex.commands.attribute;

import lombok.Getter;
import net.ddns.templex.TemplexAdditionsPlugin;

import java.util.concurrent.ConcurrentHashMap;

public class AttributeHandler {

    @Getter
    private final TemplexAdditionsPlugin plugin;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Getter
    private final ConcurrentHashMap<String, Attribute> attributes;

    public AttributeHandler(TemplexAdditionsPlugin plugin) {
        this.plugin = plugin;
        this.attributes = new ConcurrentHashMap<>();
    }

}
