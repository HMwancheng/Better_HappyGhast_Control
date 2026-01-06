package com.example.happyghastcontrol;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyMapping;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class HappyGhastControlClient implements ClientModInitializer {
    public static KeyMapping ascendKey;
    public static KeyMapping descendKey;

    @Override
    public void onInitializeClient() {
        // Register key bindings
        ascendKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.happyghastcontrol.ascend",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_SPACE,
                "category.happyghastcontrol"
        ));

        descendKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.happyghastcontrol.descend",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_CONTROL,
                "category.happyghastcontrol"
        ));

        // Register tick event listener for key handling
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Key handling is done in the mixin
        });
    }
}
