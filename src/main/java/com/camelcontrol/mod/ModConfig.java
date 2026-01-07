package com.camelcontrol.mod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig {
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("camelcontrol.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static class ConfigData {
        public float horizontalSpeed = 0.5f;
        public float verticalSpeed = 0.3f;
        public boolean enableCustomControls = true;
    }

    private static ConfigData config = new ConfigData();

    public static void register() {
        loadConfig();
    }

    public static ConfigData getConfig() {
        return config;
    }

    public static void loadConfig() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                config = GSON.fromJson(json, ConfigData.class);
                CamelControlMod.LOGGER.info("Loaded Camel Control config");
            } catch (IOException e) {
                CamelControlMod.LOGGER.error("Failed to load Camel Control config", e);
            }
        } else {
            saveConfig();
        }
    }

    public static void saveConfig() {
        try {
            String json = GSON.toJson(config);
            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, json);
            CamelControlMod.LOGGER.info("Saved Camel Control config");
        } catch (IOException e) {
            CamelControlMod.LOGGER.error("Failed to save Camel Control config", e);
        }
    }
}