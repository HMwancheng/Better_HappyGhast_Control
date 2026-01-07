package com.camelcontrol.mod;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CamelControlMod implements ModInitializer {
    public static final String MOD_ID = "camelcontrol";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Camel Control Mod");

        // 注册配置
        ModConfig.register();
    }
}