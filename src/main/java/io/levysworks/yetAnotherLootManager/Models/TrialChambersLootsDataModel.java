package io.levysworks.yetAnotherLootManager.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the data model for loot items in trial chambers.
 * This model is used to store information about available loot for vaults
 * and ominous vaults in the game.
 */
public class TrialChambersLootsDataModel {

    /** The version of the loot data model. */
    @JsonProperty("version")
    private String version;

    /** Indicates whether the loot system is enabled. */
    @JsonProperty("enabled")
    private boolean enabled;

    /** Indicates whether debug mode is enabled for loot generation. */
    @JsonProperty("debug")
    private boolean debug;

    /** List of loot items available in a standard vault. */
    @JsonProperty("vault")
    private List<LootItem> vault = new ArrayList<>();

    /** List of loot items available in an ominous vault. */
    @JsonProperty("ominous_vault")
    private List<LootItem> ominousVault = new ArrayList<>();

    /**
     * Gets the version of the loot data model.
     *
     * @return The version string.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the version of the loot data model.
     *
     * @param version The version string to set.
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Checks if the loot system is enabled.
     *
     * @return True if enabled, false otherwise.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets whether the loot system is enabled.
     *
     * @param enabled True to enable, false to disable.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Checks if debug mode is enabled.
     *
     * @return True if debug mode is enabled, false otherwise.
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     * Sets whether debug mode is enabled.
     *
     * @param debug True to enable debug mode, false to disable.
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * Gets the list of loot items for the standard vault.
     *
     * @return A list of {@link LootItem} objects.
     */
    public List<LootItem> getVault() {
        return vault;
    }

    /**
     * Sets the list of loot items for the standard vault.
     *
     * @param vault The list of loot items to set.
     */
    public void setVault(List<LootItem> vault) {
        this.vault = vault;
    }

    /**
     * Gets the list of loot items for the ominous vault.
     *
     * @return A list of {@link LootItem} objects.
     */
    public List<LootItem> getOminousVault() {
        return ominousVault;
    }

    /**
     * Sets the list of loot items for the ominous vault.
     *
     * @param ominousVault The list of loot items to set.
     */
    public void setOminousVault(List<LootItem> ominousVault) {
        this.ominousVault = ominousVault;
    }

    /**
     * Represents a loot item in the vault.
     * Contains item details, provider, amount, and drop chance.
     */
    public static class LootItem {

        /** The provider of the loot item (e.g., Vanilla, Nexo). */
        @JsonProperty("provider")
        private String provider;

        /** The name or ID of the loot item. */
        @JsonProperty("item")
        private String item;

        /** The amount range of the loot item. */
        @JsonProperty("amount")
        private Amount amount;

        /** The probability of the loot item being selected. */
        @JsonProperty("chance")
        private double chance;

        /**
         * Gets the provider of the loot item.
         *
         * @return The provider name.
         */
        public String getProvider() {
            return provider;
        }

        /**
         * Sets the provider of the loot item.
         *
         * @param provider The provider name to set.
         */
        public void setProvider(String provider) {
            this.provider = provider;
        }

        /**
         * Gets the item name or ID.
         *
         * @return The item name or ID.
         */
        public String getItem() {
            return item;
        }

        /**
         * Sets the item name or ID.
         *
         * @param item The item name or ID to set.
         */
        public void setItem(String item) {
            this.item = item;
        }

        /**
         * Gets the amount range of the loot item.
         *
         * @return The {@link Amount} object representing min and max values.
         */
        public Amount getAmount() {
            return amount;
        }

        /**
         * Sets the amount range of the loot item.
         *
         * @param amount The {@link Amount} object to set.
         */
        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        /**
         * Gets the probability of the loot item being selected.
         *
         * @return The chance value as a double.
         */
        public double getChance() {
            return chance;
        }

        /**
         * Sets the probability of the loot item being selected.
         *
         * @param chance The chance value to set.
         */
        public void setChance(double chance) {
            this.chance = chance;
        }
    }

    /**
     * Represents the quantity range for a loot item.
     * Defines the minimum and maximum possible amounts.
     */
    public static class Amount {

        /** The minimum amount of the loot item. */
        @JsonProperty("min")
        private int min;

        /** The maximum amount of the loot item. */
        @JsonProperty("max")
        private int max;

        /**
         * Gets the minimum amount of the loot item.
         *
         * @return The minimum amount.
         */
        public int getMin() {
            return min;
        }

        /**
         * Sets the minimum amount of the loot item.
         *
         * @param min The minimum amount to set.
         */
        public void setMin(int min) {
            this.min = min;
        }

        /**
         * Gets the maximum amount of the loot item.
         *
         * @return The maximum amount.
         */
        public int getMax() {
            return max;
        }

        /**
         * Sets the maximum amount of the loot item.
         *
         * @param max The maximum amount to set.
         */
        public void setMax(int max) {
            this.max = max;
        }
    }
}
