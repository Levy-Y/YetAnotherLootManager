package io.levysworks.yetAnotherLootManager;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import io.levysworks.yetAnotherLootManager.Models.DungeonDataModel;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;

public class ConfigLoader {
    private static final YAMLMapper yamlMapper = new YAMLMapper();
    private static final Plugin plugin = YetAnotherLootManager.getPlugin(YetAnotherLootManager.class);

    public static void ReadConfigFile() {
        try {
            File configFile = new File(plugin.getDataFolder(), "config.yml");

            YetAnotherLootManager.dungeonDataModel = yamlMapper.readValue(new FileInputStream(configFile), DungeonDataModel.class);

            YetAnotherLootManager.dungeonDataModel.getDungeons().forEach(dungeon -> {
                if (dungeon.getLoot().isEmpty()) {
                    Bukkit.getLogger().warning("Dungeon " + dungeon.getName() + " has no loot section in the config.yml!");
                };
            });
        } catch (MismatchedInputException | FileNotFoundException e) {
            YetAnotherLootManager.getPlugin(YetAnotherLootManager.class).getLogger().log(Level.SEVERE, "Failed to read config.yml, it is either empty, or doesn't exist.");
            YetAnotherLootManager.getPlugin(YetAnotherLootManager.class).getLogger().log(Level.WARNING, "Keeping cached config.yml file.");
        } catch (StreamReadException e) {
            extracted(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void extracted(StreamReadException e) {
        throw new RuntimeException(e);
    }

    public static void EmptyDungeonDataModel() {
        YetAnotherLootManager.dungeonDataModel = null;
    }
}
