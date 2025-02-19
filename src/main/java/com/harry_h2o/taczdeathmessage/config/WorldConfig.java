package com.harry_h2o.taczdeathmessage.config;

import java.util.HashMap;
import java.util.Map;

public class WorldConfig {
    public Map<String, WorldData> worlds = new HashMap<>();

    public static class WorldData {
        public Boolean enabled;
        public Boolean enableAnimals;
        public Boolean enableMonsters;
        public GlobalConfig.Messages messages;
    }
}