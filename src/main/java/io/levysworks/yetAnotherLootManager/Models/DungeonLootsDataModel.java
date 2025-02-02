package io.levysworks.yetAnotherLootManager.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the data model for dungeon loots configuration.
 * This includes general settings and details about dungeons and their respective loot tables.
 */
public class DungeonLootsDataModel {

    /**
     * The version of the dungeon loots configuration.
     */
    @JsonProperty("version")
    private String version;

    /**
     * Indicates whether dungeon loots are enabled.
     */
    @JsonProperty("enabled")
    private Boolean enabled;

    /**
     * Indicates whether vanilla dungeon loots should be overridden.
     */
    @JsonProperty("override_vanilla")
    private Boolean override_vanilla;

    /**
     * Indicates whether debugging is enabled.
     */
    @JsonProperty("debug")
    private Boolean debug;

    /**
     * A list of dungeons and their configurations.
     */
    @JsonProperty("dungeons")
    private final List<Dungeon> dungeons = new ArrayList<>();

    /**
     * Gets the version of the dungeon loots configuration.
     *
     * @return the version string
     */
    public String getVersion() {
        return version;
    }

    /**
     * Gets whether dungeon loots are enabled.
     *
     * @return true if enabled, otherwise false
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Gets whether vanilla dungeon loots are overridden.
     *
     * @return true if overridden, otherwise false
     */
    public Boolean getOverride_vanilla() {
        return override_vanilla;
    }

    /**
     * Gets whether debugging is enabled.
     *
     * @return true if debugging is enabled, otherwise false
     */
    public Boolean getDebug() {
        return debug;
    }

    /**
     * Gets the list of configured dungeons.
     *
     * @return the list of dungeons
     */
    public List<Dungeon> getDungeons() {
        return dungeons;
    }

    /**
     * Adds a dungeon to the dungeon list.
     *
     * @param d the dungeon to add
     */
    public void addDungeon(Dungeon d) {
        dungeons.add(d);
    }

    /**
     * Represents a dungeon and its associated loot table.
     */
    public static class Dungeon {

        /**
         * The name of the dungeon.
         */
        @JsonProperty("name")
        private String name;

        /**
         * The loot table associated with the dungeon.
         */
        @JsonProperty("loot")
        private List<Loot> loot = new ArrayList<>();

        /**
         * Gets the name of the dungeon.
         *
         * @return the dungeon name
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the name of the dungeon.
         *
         * @param name the dungeon name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Gets the loot table for the dungeon.
         *
         * @return the list of loot items
         */
        public List<Loot> getLoot() {
            return loot;
        }

        /**
         * Sets the loot table for the dungeon.
         *
         * @param loot the list of loot items
         */
        public void setLoot(List<Loot> loot) {
            this.loot = loot;
        }
    }

    /**
     * Represents a loot item configuration for a dungeon.
     */
    public static class Loot {

        /**
         * The provider of the loot item.
         */
        @JsonProperty("provider")
        private String provider;

        /**
         * The name of the loot item.
         */
        @JsonProperty("item")
        private String itemName;

        /**
         * The range of the loot item's quantity.
         */
        @JsonProperty("amount")
        private Range amount;

        /**
         * The drop chance of the loot item.
         */
        @JsonProperty("chance")
        private double chance;

        /**
         * Gets the provider of the loot item.
         *
         * @return the provider name
         */
        public String getProvider() {
            return provider;
        }

        /**
         * Sets the provider of the loot item.
         *
         * @param provider the provider name
         */
        public void setProvider(String provider) {
            this.provider = provider;
        }

        /**
         * Gets the name of the loot item.
         *
         * @return the item name
         */
        public String getItemName() {
            return itemName;
        }

        /**
         * Sets the name of the loot item.
         *
         * @param itemName the item name
         */
        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        /**
         * Gets the range of the loot item's quantity.
         *
         * @return the range of quantity
         */
        public Range getAmount() {
            return amount;
        }

        /**
         * Sets the range of the loot item's quantity.
         *
         * @param amount the range of quantity
         */
        public void setAmount(Range amount) {
            this.amount = amount;
        }

        /**
         * Gets the drop chance of the loot item.
         *
         * @return the drop chance
         */
        public double getChance() {
            return chance;
        }

        /**
         * Sets the drop chance of the loot item.
         *
         * @param chance the drop chance
         */
        public void setChance(double chance) {
            this.chance = chance;
        }
    }

    /**
     * Represents a range of values, defined by a minimum and maximum.
     */
    public static class Range {

        /**
         * The minimum value in the range.
         */
        @JsonProperty("min")
        private int min;

        /**
         * The maximum value in the range.
         */
        @JsonProperty("max")
        private int max;

        /**
         * Gets the minimum value in the range.
         *
         * @return the minimum value
         */
        public int getMin() {
            return min;
        }

        /**
         * Sets the minimum value in the range.
         *
         * @param min the minimum value
         */
        public void setMin(int min) {
            this.min = min;
        }

        /**
         * Gets the maximum value in the range.
         *
         * @return the maximum value
         */
        public int getMax() {
            return max;
        }

        /**
         * Sets the maximum value in the range.
         *
         * @param max the maximum value
         */
        public void setMax(int max) {
            this.max = max;
        }
    }
}
