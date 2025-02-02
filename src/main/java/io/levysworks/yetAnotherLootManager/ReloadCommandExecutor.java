package io.levysworks.yetAnotherLootManager;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Handles the execution and tab completion of the <br> `/yalm` command.
 * Provides functionality to reload the plugin configuration and future extensions for other subcommands.
 */
public class ReloadCommandExecutor implements CommandExecutor, TabCompleter {

    /**
     * Handles the execution of the "yalm" command.
     * This command allows reloading of different configurations, including dungeons and trial chambers.
     *
     * @param commandSender The entity that executed the command.
     * @param command The command being executed.
     * @param s The alias used for the command.
     * @param args The arguments passed with the command.
     * @return True if the command executed successfully, false otherwise.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (command.getName().equalsIgnoreCase("yalm")) {
            if (args.length == 0) {
                commandSender.sendMessage(ChatColor.RED + "You need to specify an argument.");
                return false;
            } else if (args[0].equalsIgnoreCase("reload")) {
                if (args.length < 2) {
                    commandSender.sendMessage(ChatColor.YELLOW + "Reloading all configurations!");
                    ConfigLoader.LoadDungeonConfig();
                    ConfigLoader.EmptyDungeonDataModel();
                    ConfigLoader.LoadDungeonConfig();
                    ConfigLoader.EmptyTrialChambersLootsDataModel();
                    ConfigLoader.LoadTrialChambersConfig();
                    return true;
                }

                if (args[1].equalsIgnoreCase("dungeon")) {
                    commandSender.sendMessage(ChatColor.YELLOW + "Reloading dungeons configuration!");
                    ConfigLoader.LoadDungeonConfig();
                    ConfigLoader.EmptyDungeonDataModel();
                    ConfigLoader.LoadDungeonConfig();
                    return true;
                } else if (args[1].equalsIgnoreCase("trial")) {
                    commandSender.sendMessage(ChatColor.YELLOW + "Reloading trial chambers configuration!");
                    ConfigLoader.LoadDungeonConfig();
                    ConfigLoader.EmptyTrialChambersLootsDataModel();
                    ConfigLoader.LoadTrialChambersConfig();
                    return true;
                } else {
                    commandSender.sendMessage(ChatColor.RED + "Invalid subcommand: " + args[1]);
                    return false;
                }
            }
            return false;
        }
        return false;
    }

    /**
     * Provides tab-completion suggestions for the `/yalm` command.
     * Currently supports `reload`, with subcommands `dungeon` and `trial`, as well as `list`.
     *
     * @param commandSender The sender requesting tab completion, can be a player or the console.
     * @param command       The command for which tab completion is being requested.
     * @param s             The alias used for the command.
     * @param args          The arguments provided so far.
     * @return A list of suggested completions based on the current input, or an empty list if no suggestions are available.
     */
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
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
            } else if (args.length == 2 && args[0].equalsIgnoreCase("reload")) {
                List<String> completions = new ArrayList<>();
                List<String> options = Arrays.asList("dungeon", "trial");

                for (String option : options) {
                    if (option.toLowerCase().startsWith(args[1].toLowerCase())) {
                        completions.add(option);
                    }
                }

                return completions;
            }
        }
        return List.of();
    }
}
