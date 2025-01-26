package io.levysworks.yetAnotherLootManager.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class DungeonDataModel {
    @JsonProperty("version")
    private String version;

    @JsonProperty("enabled")
    private Boolean enabled;

    @JsonProperty("override_vanilla")
    private Boolean override_vanilla;

    @JsonProperty("debug")
    private Boolean debug;
    
    public String getVersion() {
        return version;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Boolean getOverride_vanilla() {
        return override_vanilla;
    }

    public Boolean getDebug() {
        return debug;
    }

    @JsonProperty("dungeons")
    private final List<Dungeon> dungeons = new ArrayList<>();

    public List<Dungeon> getDungeons() {
        return dungeons;
    }

    public void addDungeon(Dungeon d) {
        dungeons.add(d);
    }

    public static class Dungeon {
        @JsonProperty("name")
        private String name;

        @JsonProperty("loot")
        private List<Loot> loot = new ArrayList<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Loot> getLoot() {
            return loot;
        }

        public void setLoot(List<Loot> loot) {
            this.loot = loot;
        }
    }

    public static class Loot {
        @JsonProperty("provider")
        private String provider;

        @JsonProperty("item")
        private String itemName;

        @JsonProperty("amount")
        private Range amount;

        @JsonProperty("chance")
        private double chance;

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public Range getAmount() {
            return amount;
        }

        public void setAmount(Range amount) {
            this.amount = amount;
        }

        public double getChance() {
            return chance;
        }

        public void setChance(double chance) {
            this.chance = chance;
        }
    }

    public static class Range {
        @JsonProperty("min")
        private int min;

        @JsonProperty("max")
        private int max;

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }
    }
}
