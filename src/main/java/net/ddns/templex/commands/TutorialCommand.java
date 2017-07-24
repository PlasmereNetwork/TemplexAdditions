package net.ddns.templex.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.event.TabCompleteEvent;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

public class TutorialCommand extends TabbableCommand {
    private final BaseComponent[] SYNTAX = (
            new ComponentBuilder("Syntax:\n/tutorial OR /tutorial <how|offer|rankup|eco>").color(ChatColor.RED)
                    .create()
            );
    private final BaseComponent[] TUTORIAL_BEGIN = (
            new ComponentBuilder("Welcome to ").color(ChatColor.GRAY)
                    .append("Templex").color(ChatColor.DARK_AQUA)
                    .append("! We are a completely ").color(ChatColor.GRAY)
                    .append("Vanilla Survival Server").color(ChatColor.RED)
                    .append(". Type ").color(ChatColor.GRAY)
                    .append("\"").color(ChatColor.DARK_GRAY)
                    .append("/tut how").color(ChatColor.DARK_PURPLE)
                    .append("\"").color(ChatColor.DARK_GRAY)
                    .append(" to see how we do this.").color(ChatColor.GRAY)
                    .append("\nIf you wish to see what this server offers, type ").color(ChatColor.GRAY)
                    .append("\"").color(ChatColor.DARK_GRAY)
                    .append("/tut offer").color(ChatColor.DARK_PURPLE)
                    .append("\"").color(ChatColor.DARK_GRAY)
                    .append(".").color(ChatColor.GRAY)
                    .append("\nMost ranks are based around ").color(ChatColor.GRAY)
                    .append("PlayTime").color(ChatColor.RED)
                    .append(". The longer you play, the more you rank up. Type ").color(ChatColor.GRAY)
                    .append("\"").color(ChatColor.DARK_GRAY)
                    .append("/tut rankup").color(ChatColor.DARK_PURPLE)
                    .append("\"").color(ChatColor.DARK_GRAY)
                    .append(" to see this order.").color(ChatColor.GRAY)
                    .append("\nWe operate around a Karma and Templex Credit based economy. To learn more type ")
                    .append("\"").color(ChatColor.DARK_GRAY)
                    .append("/tut eco").color(ChatColor.DARK_PURPLE)
                    .append("\"").color(ChatColor.DARK_GRAY)
                    .append(".").color(ChatColor.GRAY)
                    .append("\nHow to get out of Spawn").color(ChatColor.GREEN)
                    .append(": ").color(ChatColor.DARK_GRAY)
                    .append("/rtp").color(ChatColor.DARK_PURPLE)
                    .append("\nHow to tp to another player or another player to you").color(ChatColor.GREEN)
                    .append(": ").color(ChatColor.DARK_GRAY)
                    .append("/tpa <name>").color(ChatColor.DARK_PURPLE)
                    .append(" or ").color(ChatColor.GREEN)
                    .append("/tpahere <name>").color(ChatColor.DARK_PURPLE)
                    .append("\nHow to set your homes").color(ChatColor.GREEN)
                    .append(": ").color(ChatColor.DARK_GRAY)
                    .append("/sethome <home name>").color(ChatColor.DARK_PURPLE)
                    .append("\nHow to go to your homes").color(ChatColor.GREEN)
                    .append(": ").color(ChatColor.DARK_GRAY)
                    .append("/home <home name>").color(ChatColor.DARK_PURPLE).create()
    );
    private final BaseComponent[] TUTORIAL_HOW = (
            new ComponentBuilder("We are a completely ").color(ChatColor.GRAY)
                    .append("Vanilla Survival Server").color(ChatColor.RED)
                    .append(". This means we only work with what ").color(ChatColor.GRAY)
                    .append("Mojang").color(ChatColor.DARK_RED)
                    .append(" gives us in their ").color(ChatColor.GRAY)
                    .append("server.jar").color(ChatColor.RED)
                    .append(" all while having the adventurous survival aspect that brought so many to ").color(ChatColor.GRAY)
                    .append("Minecraft").color(ChatColor.GOLD)
                    .append(". How we do this").color(ChatColor.GRAY)
                    .append(": ").color(ChatColor.DARK_GRAY)
                    .append("We use a ").color(ChatColor.GRAY)
                    .append("BungeeCord").color(ChatColor.RED)
                    .append(" proxy plugin that allows us to talk to the ").color(ChatColor.GRAY)
                    .append("MC instance ").color(ChatColor.GOLD)
                    .append("through the plugin, thus we have no ").color(ChatColor.GRAY)
                    .append("scoreboard i/o").color(ChatColor.RED)
                    .append(". Therefore little to no lag.").color(ChatColor.GRAY)
                    .create()
    );
    private final BaseComponent[] TUTORIAL_OFFER = (
            new ComponentBuilder("We have ").color(ChatColor.GRAY)
                    .append("Vanilla").color(ChatColor.RED)
                    .append(" minigames ").color(ChatColor.GOLD)
                    .append("that give you ").color(ChatColor.GRAY)
                    .append("Templex Credits").color(ChatColor.RED)
                    .append(" and ").color(ChatColor.GRAY)
                    .append("Karma").color(ChatColor.RED)
                    .append(" just for ").color(ChatColor.GRAY)
                    .append("playing").color(ChatColor.GOLD)
                    .append(" or ").color(ChatColor.GRAY)
                    .append("winning").color(ChatColor.GOLD)
                    .append(". You can also teleport to the store if you type ").color(ChatColor.GRAY)
                    .append("\"").color(ChatColor.DARK_GRAY)
                    .append("/store").color(ChatColor.DARK_PURPLE)
                    .append("\"").color(ChatColor.DARK_GRAY)
                    .append(". You can also teleport to other players by typing ").color(ChatColor.GRAY)
                    .append("\"").color(ChatColor.DARK_GRAY)
                    .append("/tpa <player name>").color(ChatColor.DARK_PURPLE)
                    .append("\"").color(ChatColor.DARK_GRAY)
                    .append(".").color(ChatColor.GRAY)
                    .create()
    );
    private final BaseComponent[] TUTORIAL_RANKUP = (
            new ComponentBuilder("New Comer").color(ChatColor.GREEN)
                    .append(": You get this upon join.").color(ChatColor.GRAY)
                    .append("\nWarrior").color(ChatColor.DARK_GRAY)
                    .append(": You get this after 7 hours of playing.").color(ChatColor.GRAY)
                    .append("\nExplorer").color(ChatColor.LIGHT_PURPLE)
                    .append(": You get this after 17 hours of playing.").color(ChatColor.GRAY)
                    .append("\nMaster").color(ChatColor.DARK_PURPLE)
                    .append(": You get this after 22 hours of playing.").color(ChatColor.GRAY)
                    .append("\nKnight").color(ChatColor.BLUE)
                    .append(": You get this after 44 hours of playing.").color(ChatColor.GRAY)
                    .append("\nMagician").color(ChatColor.GOLD)
                    .append(": You get this after 88 hours of playing.").color(ChatColor.GRAY)
                    .append("\nHigh King").color(ChatColor.DARK_RED)
                    .append(": You get this after a week's worth of playing.").color(ChatColor.GRAY)
                    .append("\nYou").color(ChatColor.DARK_RED)
                    .append("Tuber").color(ChatColor.GOLD)
                    .append(": You can get this if your YouTube channel has over 70 subscribers and you do a YouTube video of the server ").color(ChatColor.GRAY)
                    .append("or").color(ChatColor.GRAY).italic(true)
                    .append(" you have over 2000 subscribers.").color(ChatColor.GRAY).italic(false)
                    .create()
    );
    private final BaseComponent[] TUTORIAL_ECO = (
            new ComponentBuilder("Templex Credits").color(ChatColor.RED)
                    .append(" are a currency for ")
                    .append("cosmetics").color(ChatColor.LIGHT_PURPLE)
                    .append(" and ").color(ChatColor.GRAY)
                    .append("perks").color(ChatColor.LIGHT_PURPLE)
                    .append(". If you have a ").color(ChatColor.GRAY)
                    .append("bought rank").color(ChatColor.GOLD)
                    .append(", you will get more ").color(ChatColor.GRAY)
                    .append("Templex Credits").color(ChatColor.RED)
                    .append(" than if you don't.").color(ChatColor.GRAY)
                    .append("\nKarma").color(ChatColor.RED)
                    .append(" is a currency for ").color(ChatColor.GRAY)
                    .append("items").color(ChatColor.LIGHT_PURPLE)
                    .append(" and ").color(ChatColor.GRAY)
                    .append("game-changing perks").color(ChatColor.LIGHT_PURPLE)
                    .append(". You get more by playing more.").color(ChatColor.GRAY)
                    .append("\nThe three most important commands for the ").color(ChatColor.GRAY)
                    .append("economy").color(ChatColor.GOLD)
                    .append(" are ").color(ChatColor.GRAY)
                    .append("\"").color(ChatColor.DARK_GRAY)
                    .append("/buyrtp").color(ChatColor.DARK_PURPLE)
                    .append("\"").color(ChatColor.DARK_GRAY)
                    .append(", ").color(ChatColor.GRAY)
                    .append("\"").color(ChatColor.DARK_GRAY)
                    .append("/buyxp").color(ChatColor.DARK_PURPLE)
                    .append("\"").color(ChatColor.DARK_GRAY)
                    .append(", and ").color(ChatColor.GRAY)
                    .append("\"").color(ChatColor.DARK_GRAY)
                    .append("/store").color(ChatColor.DARK_PURPLE)
                    .append("\"").color(ChatColor.DARK_GRAY)
                    .append(".").color(ChatColor.GRAY)
                    .create()
    );

    public TutorialCommand() {
        super("tutorial", "nonop", "tut");
    }

    public void execute(CommandSender commandSender, String[] strings) {
        final BaseComponent[] TELLOPS = (
                new ComponentBuilder("Tutorial PL").color(ChatColor.GOLD)
                        .append(" : ").color(ChatColor.DARK_GRAY)
                        .append(commandSender.getName()).color(ChatColor.GRAY)
                        .append(" was just showed a tutorial!").color(ChatColor.RED)
                        .create()
        );
        if (strings.length == 0) {
            commandSender.sendMessage(TUTORIAL_BEGIN);
            CommandUtil.tellOps(TELLOPS);
            return;
        }
        switch (strings[0]) {
            case "how":
                commandSender.sendMessage(TUTORIAL_HOW);
                CommandUtil.tellOps(TELLOPS);
                return;
            case "offer":
                commandSender.sendMessage(TUTORIAL_OFFER);
                CommandUtil.tellOps(TELLOPS);
                return;
            case "rankup":
                commandSender.sendMessage(TUTORIAL_RANKUP);
                CommandUtil.tellOps(TELLOPS);
                return;
            case "eco":
                commandSender.sendMessage(TUTORIAL_ECO);
                CommandUtil.tellOps(TELLOPS);
                return;
            default:
                commandSender.sendMessage(SYNTAX);
        }
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {
        String[] items = event.getCursor().split(" ");
        switch (items.length) {
            case 2:
                switch (items[1]) {
                    // ???
                }
        }
    }
}
