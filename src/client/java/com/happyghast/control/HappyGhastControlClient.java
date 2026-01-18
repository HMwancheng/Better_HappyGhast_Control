package com.happyghast.control;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.happyghast.control.config.ModConfig;

public class HappyGhastControlClient implements ClientModInitializer {
	public static final String MOD_ID = "happyghast_control";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// Custom keybindings for Happy Ghast control
	public static KeyBinding ascendKey;
	public static KeyBinding descendKey;

	@Override
	public void onInitializeClient() {
		LOGGER.info("Initializing Happy Ghast Control Client");

		// Load configuration
		ModConfig.load();

		// Register keybindings
		
		// Create category object (required for 1.21 KeyBinding constructor)
		// 1.21.x requires an Identifier for Category creation.
		KeyBinding.Category categoryObj = KeyBinding.Category.create(Identifier.of(MOD_ID, "controls"));
		
		ascendKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.happyghast_control.ascend",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_SPACE,
			categoryObj
		));

		descendKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.happyghast_control.descend",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_LEFT_CONTROL,
			categoryObj
		));

		LOGGER.info("Happy Ghast Control Client initialized successfully");
	}
}
