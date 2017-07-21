package net.ddns.templex.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class TutorialCommand extends TabbableCommand {
    private final BaseComponent[] TUTORIAL_BEGIN = (
            new ComponentBuilder("Welcome to ").color(ChatColor.DARK_PURPLE)
                    .append("Templex").color(ChatColor.DARK_AQUA)
                    .append("! We are a completely Vanilla Survival Server. Type \"/tut how\" to see how we do this.").color(ChatColor.DARK_PURPLE)
                    .append("\nIf you wish to see what this server offers, type \"/tut offer\".").color(ChatColor.DARK_PURPLE)
                    .append("\nMost ranks are based around PlayTime. The longer you play, the more you rank up. Type \"/tut rankup\" to see this order.").color(ChatColor.DARK_PURPLE)
                    .append("\nWe operate around a Karma and Templex Credit based economy. To learn more type \"/tut eco\".").color(ChatColor.DARK_PURPLE)
                    .append("\nHow to get out of Spawn: /rtp").color(ChatColor.DARK_PURPLE)
                    .append("\nHow to tp to another player or another player to you: /tpa <name> or /tpahere <name>").color(ChatColor.DARK_PURPLE)
                    .append("\nHow to set your homes: /sethome <home name>").color(ChatColor.DARK_PURPLE)
                    .append("\nHow to go to your homes: /home <home name>").color(ChatColor.DARK_PURPLE).create()
            );
    private final BaseComponent[] TUTORIAL_HOW = (
            new ComponentBuilder("We are a completely Vanilla Survival Server. This means we only work with what Mojang gives us in their server.jar all while having the adventurous survival aspect that brought so many to Minecraft. ").color(ChatColor.DARK_PURPLE)
                    .append("How we do this: We use a BungeeCord proxy plugin that allows us to talk to the MC instance through the plugin, thus we have no scoreboard i/o. Therefore little to no lag.").color(ChatColor.DARK_PURPLE)
                    .create()
            );
    private final BaseComponent[] TUTORIAL_OFFER = (
            new ComponentBuilder("We have Vanilla minigames that give you Templex Credits and Karma for playing or winning. ").color(ChatColor.DARK_PURPLE)
                    .append("You can also tp to the store if you type \"/store\".").color(ChatColor.DARK_PURPLE)

                    // TODO

                    .append(" More will be added to this later.").color(ChatColor.DARK_PURPLE)
                    .create()
            );
    private final BaseComponent[] TUTORIAL_RANKUP = (
            new ComponentBuilder("New Comer").color(ChatColor.GREEN)
                    .append(": You get upon join.").color(ChatColor.DARK_PURPLE)
                    .append("\nWarrior").color(ChatColor.DARK_GRAY)
                    .append(": You get this after 7 hours of playing.").color(ChatColor.DARK_PURPLE)
                    .append("\nExplorer").color(ChatColor.LIGHT_PURPLE)
                    .append(": You get this after 17 hours of playing.").color(ChatColor.DARK_PURPLE)
                    .append("\nMaster").color(ChatColor.DARK_PURPLE)
                    .append(": You get this after 22 hours of playing.").color(ChatColor.DARK_PURPLE)
                    .append("\nKnight").color(ChatColor.BLUE)
                    .append(": You get this after 44 hours of playing.").color(ChatColor.DARK_PURPLE)
                    .append("\nMagician").color(ChatColor.GOLD)
                    .append(": You get this after 88 hours of playing.").color(ChatColor.DARK_PURPLE)
                    .append("\nHigh King").color(ChatColor.DARK_RED)
                    .append(": You get this after a week's worth of playing.").color(ChatColor.DARK_PURPLE)
                    .append("\nYou").color(ChatColor.DARK_RED)
                    .append("Tuber").color(ChatColor.GOLD)
                    .append(": You can get this if your YouTube channel has over 70 subscribers and you do a YouTube video of the server").color(ChatColor.DARK_PURPLE)
                    .append("or").color(ChatColor.DARK_PURPLE).italic(true)
                    .append(" you have over 2000 subscribers.").color(ChatColor.DARK_PURPLE)
                    .create()
            );
    private final BaseComponent[] TUTORIAL_ECO = (
            new ComponentBuilder("Templex Credits are a currency for cosmetics and perks. If you have a bought rank, you will get more than if you don't.").color(ChatColor.DARK_PURPLE)
                    .append("\nKarma is a currency for items and game-changing perks. You get more by playing more.").color(ChatColor.DARK_PURPLE)
                    .append("\nThe two most important commands in the economy are \"/buyrtp\" and \"/buyxp\".")
                    .create()
    );

    public TutorialCommand() {super("tutorial","nonop","tut");}

    public void execute(CommandSender commandSender, String[] strings) {
        if (strings.length == 0){
            commandSender.sendMessage(TUTORIAL_BEGIN);
            return;
        }
        switch (strings[0]) {
            case "how":
                commandSender.sendMessage(TUTORIAL_HOW);
                return;
            case "offer":
                commandSender.sendMessage(TUTORIAL_OFFER);
                return;
            case "rankup":
                commandSender.sendMessage(TUTORIAL_RANKUP);
                return;
            case "eco":
                commandSender.sendMessage(TUTORIAL_ECO);
                return;
            default:
                commandSender.sendMessage(TUTORIAL_BEGIN);
        }
    }
}
