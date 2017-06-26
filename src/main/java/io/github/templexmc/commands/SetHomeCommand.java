package io.github.templexmc.commands;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import io.github.trulyfree.va.command.commands.TabbableCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.event.TabCompleteEvent;

/**
 * TODO
 */
public class SetHomeCommand extends TabbableCommand {

    public SetHomeCommand() {
        super("sethome", "tabbable.sethome", "sh");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) throws JsonParseException, JsonIOException, JsonSyntaxException {

    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {
        Util.pushAutocompletePlayers(event);
    }

}
