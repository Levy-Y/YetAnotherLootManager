package io.levysworks.yetAnotherLootManager.Listeners;

import com.nexomc.nexo.api.NexoItems;
import com.nexomc.nexo.items.ItemBuilder;
import io.levysworks.yetAnotherLootManager.Models.TrialChambersLootsDataModel.LootItem;
import io.levysworks.yetAnotherLootManager.YetAnotherLootManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Vault;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseLootEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class VaultRewardListener implements Listener {
    /**
     * Handles the BlockDispenseLootEvent when a vault dispenses loot.
     * It modifies the default loot by removing a random item and adding a weighted selection
     * from either the ominous or standard vault loot pools.
     *
     * @param event The event triggered when a block dispenses loot.
     */
    @EventHandler
    public void onVaultReward(BlockDispenseLootEvent event) {
        List<ItemStack> defaultLoot = event.getDispensedLoot();

        defaultLoot.remove(new Random().nextInt(defaultLoot.size()));

        if (event.getBlock().getBlockData() instanceof Vault vault) {
            if (YetAnotherLootManager.trialChambersLootsDataModel.getOminousVault().isEmpty()) return;

            if (vault.isOminous()) {

                LootItem selectedItem = doWeightedSelection(YetAnotherLootManager.trialChambersLootsDataModel.getOminousVault());
                ItemStack itemToAdd = buildItemFromLootItem(selectedItem);

                if (itemToAdd == null) {
                    Bukkit.getLogger().warning("Could not add loot item to vault!");
                    return;
                }
                defaultLoot.addFirst(itemToAdd);

            } else {
                if (YetAnotherLootManager.trialChambersLootsDataModel.getVault().isEmpty()) return;

                LootItem selectedItem = doWeightedSelection(YetAnotherLootManager.trialChambersLootsDataModel.getVault());
                ItemStack itemToAdd = buildItemFromLootItem(selectedItem);

                if (itemToAdd == null) {
                    Bukkit.getLogger().warning("Could not add loot item to vault!");
                    return;
                }
                defaultLoot.addFirst(itemToAdd);
            }
        }
    }

    /**
     * Performs a weighted random selection from a list of loot items.
     * The selection is based on the chance values of each LootItem.
     *
     * @param lootItems The list of loot items to select from.
     * @return A selected LootItem based on weighted probability, or null if the list is empty.
     */
    private LootItem doWeightedSelection(List<LootItem> lootItems) {
        double totalChance = lootItems.stream().mapToDouble(LootItem::getChance).sum();
        double randomValue = new Random().nextDouble() * totalChance;

        for (LootItem item : lootItems) {
            randomValue -= item.getChance();
            if (randomValue <= 0) {
                return item;
            }
        }
        return null;
    }

    /**
     * Builds an ItemStack from the given LootItem.
     * Supports both vanilla and Nexo item providers, handling errors if the item does not exist.
     *
     * @param lootItem The LootItem to convert into an ItemStack.
     * @return The built ItemStack, or null if the item could not be created.
     */
    private ItemStack buildItemFromLootItem(LootItem lootItem) {
        ItemStack builtItem = null;
        int amount;

        if (lootItem.getAmount().getMin() >= lootItem.getAmount().getMax()) {
            amount = lootItem.getAmount().getMin();
        } else {
            amount = new Random().nextInt(lootItem.getAmount().getMin(), lootItem.getAmount().getMax());
        }

        if (lootItem.getItem() == null) return null;

        switch (lootItem.getProvider().toUpperCase()) {
            case "VANILLA": {
                try {
                    builtItem = new ItemStack(Material.valueOf(lootItem.getItem().toUpperCase()), amount);
                } catch (IllegalArgumentException e) {
                    if (YetAnotherLootManager.trialChambersLootsDataModel.isDebug()) {
                        Bukkit.getLogger().warning("Vanilla item does not exist with name: " + lootItem.getItem());
                    }
                }
                break;
            }
            case "NEXO": {
                try {
                    ItemBuilder itemBuilder = NexoItems.itemFromId(lootItem.getItem());

                    if (itemBuilder != null) {
                        ItemStack item = itemBuilder.build();
                        item.setAmount(amount);
                        builtItem = item;
                    } else if (YetAnotherLootManager.trialChambersLootsDataModel.isDebug()) {
                        Bukkit.getLogger().warning("Nexo item does not exist with name: " + lootItem.getItem());
                    }
                } catch (NoClassDefFoundError e) {
                    if (YetAnotherLootManager.trialChambersLootsDataModel.isDebug()) {
                        Bukkit.getLogger().warning("Nexo isn't installed on the server!");
                    }
                }
                break;
            }
        }

        return builtItem;
    }
}
