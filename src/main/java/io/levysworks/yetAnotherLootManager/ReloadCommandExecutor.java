package io.levysworks.yetAnotherLootManager;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReloadCommandExecutor implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (command.getName().equalsIgnoreCase("yalm")) {
            if (args.length == 0) {
                commandSender.sendMessage(ChatColor.RED + "You need to specify an argument.");
                return false;
            } else if (args[0].equalsIgnoreCase("reload")) {
                commandSender.sendMessage(ChatColor.GREEN + "YALM has been reloaded!");
                ConfigLoader.EmptyDungeonDataModel();
                ConfigLoader.ReadConfigFile();
                return true;
            } else {
                commandSender.sendMessage(ChatColor.RED + "Invalid argument: " + args[0]);
                return false;
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (command.getName().equalsIgnoreCase("yalm")) {
            if (args.length == 1) {
                List<String> completions = new ArrayList<>();
                List<String> options = Arrays.asList("reload", "list");

                for (String option : options) {
                    if (option.toLowerCase().startsWith(args[0].toLowerCase())) {
                        completions.add(option);
                    }
                }

                return completions;
            }
        }
        return List.of();
    }
}
