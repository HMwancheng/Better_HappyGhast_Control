package com.happyghast.control;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HappyGhastControlMod implements ModInitializer {
	public static final String MOD_ID = "happyghast_control";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Happy Ghast Control Common");
	}
}

