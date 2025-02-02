package io.levysworks.yetAnotherLootManager;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;
import io.levysworks.yetAnotherLootManager.Models.DungeonLootsDataModel;
import io.levysworks.yetAnotherLootManager.Models.TrialChambersLootsDataModel;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.file.Files;
import java.util.logging.Level;

/**
 * Utility class for loading and managing the configuration file for the plugin.
 * Handles reading, parsing, and validating the `dungeon_config.yml` file to populate the {@link DungeonLootsDataModel}.
 */
public class ConfigLoader {

    /** YAML mapper for parsing YAML files into Java objects. */
    private static final YAMLMapper yamlMapper = new YAMLMapper();

    /** Reference to the plugin instance. */
    private static final Plugin plugin = YetAnotherLootManager.getPlugin(YetAnotherLootManager.class);

    public static void loadDefaultConfigs() {
        try {
            File chambersConfig = new File(plugin.getDataFolder(), "chambers_config.yml");
            if (!chambersConfig.exists()) {
                Files.copy(plugin.getResource("chambers_config.yml"), chambersConfig.toPath());
            }

            File dungeonsConfig = new File(plugin.getDataFolder(), "dungeon_config.yml");
            if (!dungeonsConfig.exists()) {
                Files.copy(plugin.getResource("dungeon_config.yml"), dungeonsConfig.toPath());
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to create configuration files: " + e.getMessage());
        }
    }

    /**
     * Reads and parses the `dungeon_config.yml` file into the {@link DungeonLootsDataModel}.
     * Logs warnings if any dungeons are missing a loot section or if the file is invalid.
     * Handles file-not-found and parsing errors gracefully.
     */
    public static void LoadDungeonConfig() {
        try {
            File configFile = new File(plugin.getDataFolder(), "dungeon_config.yml");

            YetAnotherLootManager.dungeonLootsDataModel = yamlMapper.readValue(new FileInputStream(configFile), DungeonLootsDataModel.class);



            YetAnotherLootManager.dungeonLootsDataModel.getDungeons().forEach(dungeon -> {
                if (dungeon.getLoot().isEmpty()) {
                    Bukkit.getLogger().warning("Dungeon " + dungeon.getName() + " has no loot section in the dungeon_config.yml!");
                };
            });
        } catch (MismatchedInputException | FileNotFoundException e) {
            YetAnotherLootManager.getPlugin(YetAnotherLootManager.class).getLogger().log(Level.SEVERE, "Failed to read dungeon_config.yml, it is either empty, or doesn't exist.");
            YetAnotherLootManager.getPlugin(YetAnotherLootManager.class).getLogger().log(Level.WARNING, "Keeping cached dungeon_config.yml file.");
        } catch (StreamReadException e) {
            extracted(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads and parses the `chambers_config.yml` file into the {@link TrialChambersLootsDataModel}.
     * Logs warnings if any vaults are missing a loot section or if the file is invalid.
     * Handles file-not-found and parsing errors gracefully.
     */
    public static void LoadTrialChambersConfig() {
        try {
            File configFile = new File(plugin.getDataFolder(), "chambers_config.yml");

            YetAnotherLootManager.trialChambersLootsDataModel = yamlMapper.readValue(new FileInputStream(configFile), TrialChambersLootsDataModel.class);

            if (YetAnotherLootManager.trialChambersLootsDataModel.getVault().isEmpty() && YetAnotherLootManager.trialChambersLootsDataModel.getOminousVault().isEmpty()) {
                Bukkit.getLogger().warning("Vault, and Ominous Vault are empty in the chambers_config.yml!");
            };

        } catch (MismatchedInputException | FileNotFoundException e) {
            YetAnotherLootManager.getPlugin(YetAnotherLootManager.class).getLogger().log(Level.SEVERE, "Failed to read chambers_config.yml, it is either empty, or doesn't exist.");
            YetAnotherLootManager.getPlugin(YetAnotherLootManager.class).getLogger().log(Level.WARNING, "Keeping cached chambers_config.yml file.");
        } catch (StreamReadException e) {
            extracted(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Helper method to handle {@link StreamReadException} during configuration file reading.
     *
     * @param e The exception to handle.
     * @throws RuntimeException Wrapping the original exception for debugging purposes.
     */
    private static void extracted(StreamReadException e) {
        throw new RuntimeException(e);
    }

    /**
     * Clears the {@link DungeonLootsDataModel}, setting it to null.
     * This can be used to reset the configuration data.
     */
    public static void EmptyDungeonDataModel() {
        YetAnotherLootManager.dungeonLootsDataModel = null;
    }

    /**
     * Clears the {@link TrialChambersLootsDataModel}, setting it to null.
     * This can be used to reset the configuration data.
     */
    public static void EmptyTrialChambersLootsDataModel() {
        YetAnotherLootManager.trialChambersLootsDataModel = null;
    }
}
