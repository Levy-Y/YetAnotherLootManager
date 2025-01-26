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
    @EventHandler
    public void onLootGeneration(LootGenerateEvent event) {
         if (!YetAnotherLootManager.dungeonDataModel.getEnabled()) return;

         for (var dungeon : YetAnotherLootManager.dungeonDataModel.getDungeons()) {

             var dungeonName = "minecraft:chests/".concat(dungeon.getName());
             if (event.getLootTable().getKey().toString().equals(dungeonName)) {

                 if (YetAnotherLootManager.dungeonDataModel.getOverride_vanilla()) {
                     event.getLoot().clear();
                 }

                 for (var item : dungeon.getLoot()) {
                     if (item.getChance() == 0.0) {
                         Bukkit.getLogger().warning(dungeonName + " has 0% chance to be generated, you might want to remove unused loot!");
                     }

                     var chance = new Random().nextDouble();

                     if (YetAnotherLootManager.dungeonDataModel.getOverride_vanilla()) {
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
                    if (YetAnotherLootManager.dungeonDataModel.getDebug()) {
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
                     if (YetAnotherLootManager.dungeonDataModel.getDebug()) {
                         Bukkit.getLogger().warning("Nexo cannot be found on the server!");
                     }
                 }
                 break;
             }
         }
        return item;
    }
}
