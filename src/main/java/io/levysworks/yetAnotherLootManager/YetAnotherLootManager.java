package io.levysworks.yetAnotherLootManager;

import io.levysworks.yetAnotherLootManager.Listeners.LootGenerationListener;
import io.levysworks.yetAnotherLootManager.Models.DungeonDataModel;
import org.bukkit.plugin.java.JavaPlugin;

public final class YetAnotherLootManager extends JavaPlugin {

    public static DungeonDataModel dungeonDataModel;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        ConfigLoader.ReadConfigFile();

        getServer().getPluginManager().registerEvents(new LootGenerationListener(), this);

        this.getCommand("yalm").setExecutor(new ReloadCommandExecutor());
        this.getCommand("yalm").setTabCompleter(new ReloadCommandExecutor());

        if (dungeonDataModel == null) {
            getLogger().warning("The DungeonDataModel cannot be loaded!");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
