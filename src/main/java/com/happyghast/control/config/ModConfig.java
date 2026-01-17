package com.happyghast.control.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.happyghast.control.HappyGhastControlMod;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModConfig {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final File CONFIG_FILE = new File("config/happyghast_control.json");
	
	private static ConfigData config = new ConfigData();
	
	public static class ConfigData {
		public KeybindingConfig keybindings = new KeybindingConfig();
		public double movementMultiplier = 1.0;
		public boolean enableVerticalControl = true;
		public boolean requireShiftForDescend = false;
	}
	
	public static class KeybindingConfig {
		public String ascend = "key.keyboard.space";
		public String descend = "key.keyboard.left.control";
	}
	
	/**
	 * Load configuration from file, or create default if it doesn't exist
	 */
	public static void load() {
		if (!CONFIG_FILE.exists()) {
			save();
			HappyGhastControlMod.LOGGER.info("Created default configuration file");
			return;
		}
		
		try (FileReader reader = new FileReader(CONFIG_FILE)) {
			config = GSON.fromJson(reader, ConfigData.class);
			HappyGhastControlMod.LOGGER.info("Loaded configuration from file");
		} catch (IOException e) {
			HappyGhastControlMod.LOGGER.error("Failed to load configuration", e);
			config = new ConfigData();
		}
	}
	
	/**
	 * Save current configuration to file
	 */
	public static void save() {
		try {
			CONFIG_FILE.getParentFile().mkdirs();
			try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
				GSON.toJson(config, writer);
			}
			HappyGhastControlMod.LOGGER.info("Saved configuration to file");
		} catch (IOException e) {
			HappyGhastControlMod.LOGGER.error("Failed to save configuration", e);
		}
	}
	
	/**
	 * Get the current configuration
	 */
	public static ConfigData get() {
		return config;
	}
	
	/**
	 * Get movement speed multiplier
	 */
	public static double getMovementMultiplier() {
		return config.movementMultiplier;
	}
	
	/**
	 * Check if vertical control is enabled
	 */
	public static boolean isVerticalControlEnabled() {
		return config.enableVerticalControl;
	}
}
