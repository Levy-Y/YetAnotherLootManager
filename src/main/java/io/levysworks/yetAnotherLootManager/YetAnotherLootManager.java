package io.levysworks.yetAnotherLootManager;

import io.levysworks.yetAnotherLootManager.Listeners.LootGenerationListener;
import io.levysworks.yetAnotherLootManager.Listeners.VaultRewardListener;
import io.levysworks.yetAnotherLootManager.Models.DungeonLootsDataModel;
import io.levysworks.yetAnotherLootManager.Models.TrialChambersLootsDataModel;
import org.bukkit.plugin.java.JavaPlugin;

public final class YetAnotherLootManager extends JavaPlugin {
    /**
     * The global instance of {@link DungeonLootsDataModel} used by the plugin.
     * This object represents the parsed data from the `dungeon_config.yml` file and contains
     * the configuration for dungeon loot generation.
     *
     * <p>It is populated by {@link ConfigLoader#LoadDungeonConfig()} and can be reset using
     * {@link ConfigLoader#EmptyDungeonDataModel()}.</p>
     *
     * <p>Used throughout the plugin to access the dungeon and loot configuration data.</p>
     */
    public static DungeonLootsDataModel dungeonLootsDataModel;

    /**
     * Holds the data model for the Trial Chambers loot configuration.
     *
     * <p>This static field stores the deserialized configuration data for the Trial Chambers,
     * which is loaded from the corresponding YAML configuration file. It contains all the
     * necessary information regarding loot generation for Trial Chambers, such as item details,
     * providers, and generation rules.</p>
     *
     * <p>It is populated by {@link ConfigLoader#LoadTrialChambersConfig()} and can be reset using
     * {@link ConfigLoader#EmptyDungeonDataModel()}.</p>
     *
     * <p>Used throughout the plugin to access the trial chambers loot configuration data.</p>
     */
    public static TrialChambersLootsDataModel trialChambersLootsDataModel;

    @Override
    public void onEnable() {
        // Generate default configs if they do not exist
        ConfigLoader.loadDefaultConfigs();

        // Read and cache configuration files
        ConfigLoader.LoadTrialChambersConfig();
        ConfigLoader.LoadDungeonConfig();

        // Register event listeners
        getServer().getPluginManager().registerEvents(new LootGenerationListener(), this);
        getServer().getPluginManager().registerEvents(new VaultRewardListener(), this);

        // Register the `yalm` command with its tab completion
        this.getCommand("yalm").setExecutor(new ReloadCommandExecutor());
        this.getCommand("yalm").setTabCompleter(new ReloadCommandExecutor());

        if (dungeonLootsDataModel == null) {
            getLogger().warning("The DungeonDataModel cannot be loaded!");
        }

        if (trialChambersLootsDataModel == null) {
            getLogger().warning("The TrialChambersLootsDataModel cannot be loaded!");
        }
    }

    @Override
    public void onDisable() {}
}
