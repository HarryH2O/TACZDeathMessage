package com.harry_h2o.taczdeathmessage.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.fml.loading.FMLPaths;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigLoader {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_DIR = FMLPaths.CONFIGDIR.get().resolve("taczdeathmessage");
    public static GlobalConfig globalConfig = new GlobalConfig();
    public static WorldConfig worldConfig = new WorldConfig();

    public static void loadAll() {
        try {
            if (!Files.exists(CONFIG_DIR)) {
                Files.createDirectories(CONFIG_DIR);
            }
            loadGlobalConfig();
            loadWorldConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadGlobalConfig() {
        Path path = CONFIG_DIR.resolve("config.json");
        try {
            if (Files.exists(path)) {
                globalConfig = GSON.fromJson(new FileReader(path.toFile()), GlobalConfig.class);
            } else {
                saveGlobalConfig();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadWorldConfig() {
        Path path = CONFIG_DIR.resolve("worlds.json");
        try {
            if (Files.exists(path)) {
                worldConfig = GSON.fromJson(new FileReader(path.toFile()), WorldConfig.class);
            } else {
                saveWorldConfig();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveGlobalConfig() {
        try (FileWriter writer = new FileWriter(CONFIG_DIR.resolve("config.json").toFile())) {
            GSON.toJson(globalConfig, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveWorldConfig() {
        try (FileWriter writer = new FileWriter(CONFIG_DIR.resolve("worlds.json").toFile())) {
            GSON.toJson(worldConfig, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}