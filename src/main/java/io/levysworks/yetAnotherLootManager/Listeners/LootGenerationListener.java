package io.levysworks.yetAnotherLootManager.Listeners;

import com.nexomc.nexo.api.NexoItems;
import com.nexomc.nexo.items.ItemBuilder;
import io.levysworks.yetAnotherLootManager.YetAnotherLootManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.Random;

public class LootGenerationListener implements Listener {
    /**
     * Handles the loot generation process when a {@link LootGenerateEvent} is triggered.
     * Customizes loot tables based on the configured dungeons and their loot specifications.
     *
     * @param event The {@link LootGenerateEvent} containing the loot table and context of the event.
     */
    @EventHandler
    public void onLootGeneration(LootGenerateEvent event) {
         if (!YetAnotherLootManager.dungeonLootsDataModel.getEnabled()) return;

         for (var dungeon : YetAnotherLootManager.dungeonLootsDataModel.getDungeons()) {

             var dungeonName = "minecraft:chests/".concat(dungeon.getName());
             if (event.getLootTable().getKey().toString().equals(dungeonName)) {

                 if (YetAnotherLootManager.dungeonLootsDataModel.getOverride_vanilla()) {
                     event.getLoot().clear();
                 }

                 for (var item : dungeon.getLoot()) {
                     if (item.getChance() == 0.0) {
                         Bukkit.getLogger().warning(dungeonName + " has 0% chance to be generated, you might want to remove unused loot!");
                     }

                     var chance = new Random().nextDouble();

                     if (YetAnotherLootManager.dungeonLootsDataModel.getOverride_vanilla()) {
                         Bukkit.getLogger().warning("Rolled chance: " + chance + ", required chance: " + item.getChance());
                     }

                     if (chance <= item.getChance()) {
                        ItemStack generatedItem = GenerateLootItem(item.getItemName(), item.getProvider(), item.getAmount().getMin(), item.getAmount().getMax());

                        if (generatedItem != null) {
                            event.getLoot().add(generatedItem);
                        } else {
                            Bukkit.getLogger().warning("Generated loot item doesn't exist " + item.getItemName());
                        }
                    }
                 }
             }
         }
    }

    /**
     * Generates a loot item based on the provided item ID, provider, and quantity range.
     * The loot can be generated from either "VANILLA" or "NEXO" providers.
     *
     * @param itemID   The ID of the item to generate. Can be null if the provider handles null IDs.
     * @param Provider The loot provider ("VANILLA" or "NEXO").
     * @param min      The minimum quantity of the item to generate.
     * @param max      The maximum quantity of the item to generate.
     * @return The generated {@link ItemStack}, or null if the item could not be generated.
     */
    public ItemStack GenerateLootItem(@Nullable String itemID, String Provider, Integer min, Integer max) {
         ItemStack item = null;
         int amount;

         if (min.equals(max)) {
             amount = min;
         } else {
            amount = new Random().nextInt(min, max);
         }

         switch (Provider.toUpperCase()) {
             case "VANILLA": {
                try {
                    if (YetAnotherLootManager.dungeonLootsDataModel.getDebug()) {
                        Bukkit.getLogger().info("Generating Vanilla Loot: " + itemID + ", with amount: " + amount);
                    }


                    ItemStack itemStack = new ItemStack(Material.valueOf(itemID.toUpperCase()));
                    itemStack.setAmount(amount);
                    item = itemStack;
                } catch (IllegalArgumentException e) {
                    Bukkit.getLogger().warning("No item with itemID " + itemID + " found");
                }
                break;

             }

             case "NEXO": {
                 try {
                     ItemBuilder generatedItem = NexoItems.itemFromId(itemID);

                     if (generatedItem != null) {
                         ItemStack builtItem =  generatedItem.build();
                         builtItem.setAmount(amount);

                         item = builtItem;
                     }

                 } catch (NoClassDefFoundError e) {
                     if (YetAnotherLootManager.dungeonLootsDataModel.getDebug()) {
                         Bukkit.getLogger().warning("Nexo cannot be found on the server!");
                     }
                 }
                 break;
             }
         }
        return item;
    }
}
