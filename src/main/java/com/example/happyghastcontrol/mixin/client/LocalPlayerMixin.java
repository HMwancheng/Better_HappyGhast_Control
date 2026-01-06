package com.example.happyghastcontrol.mixin.client;

import com.example.happyghastcontrol.injector.IPlayerInjector;
import net.minecraft.client.input.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public class LocalPlayerMixin implements IPlayerInjector {
    @Unique
    private boolean betterHappyGhast$ctrlDown = false;

    @Inject(method = "tick", at = @At("HEAD"))
    private void betterHappyGhast$trackCtrlKey(boolean slowDown, float slowDownFactor, CallbackInfo ci) {
        KeyboardInput input = (KeyboardInput)(Object)this;
        boolean currentCtrlDown = input.pressingSneak;
        
        if (currentCtrlDown != betterHappyGhast$ctrlDown) {
            betterHappyGhast$ctrlDown = currentCtrlDown;
        }
    }

    @Override
    public boolean betterHappyGhast$isCtrlDown() {
        return betterHappyGhast$ctrlDown;
    }
}
