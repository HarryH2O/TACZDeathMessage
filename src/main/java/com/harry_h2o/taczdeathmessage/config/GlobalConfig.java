package com.harry_h2o.taczdeathmessage.config;

import java.util.ArrayList;
import java.util.List;

public class GlobalConfig {
    public Messages messages = new Messages();
    public boolean defaultWorldEnabled = true;
    public boolean enableAnimalMessages = false;
    public boolean enableMonsterMessages = false;

    public static class Messages {
        public PlayerMessages player = new PlayerMessages();
        public List<CommandEntry> postCommands = new ArrayList<>();
        public EntityMessages animal = new EntityMessages("{killer} 用 {gun} 猎杀了 {victim}", "");
        public EntityMessages monster = new EntityMessages("{killer} 用 {gun} 消灭了 {victim}", "");
        public String headshotSuffix = "（爆头）";
    }

    public static class PlayerMessages {
        public String normal = "{killer} 使用 {gun} 击杀了 {victim}";
        public String headshot = "{killer} 使用 {gun} 爆头击杀了 {victim}";
    }

    public static class CommandEntry {
        public String identity;
        public String command;
    }

    public static class EntityMessages {
        public String normal;
        public String headshot;

        public EntityMessages(String normal, String headshot) {
            this.normal = normal;
            this.headshot = headshot;
        }
    }
}