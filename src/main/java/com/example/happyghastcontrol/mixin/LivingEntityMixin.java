package com.example.happyghastcontrol.mixin;

import com.example.happyghastcontrol.HappyGhastControlClient;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GhastEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "getPassengerInput", at = @At("HEAD"), cancellable = true)
    private void modifyHappyGhastRiddenInput(CallbackInfoReturnable<Vec3d> cir) {
        GhastEntity ghast = (GhastEntity)(Object)this;
        
        // Only modify behavior for happy ghasts (no target)
        if (ghast.getTarget() == null && ghast.getControllingPassenger() instanceof PlayerEntity player) {
            // Handle vertical movement from custom key bindings
            double vertical = 0.0;
            if (HappyGhastControlClient.ascendKey.isPressed()) {
                vertical += 0.4;
            }
            if (HappyGhastControlClient.descendKey.isPressed()) {
                vertical -= 0.4;
            }
            
            // Create movement vector with horizontal movement from player input
            // In 1.21.8, GhastEntity doesn't have getRiddenInput method, so we use getPassengerInput instead
            Vec3d movement = new Vec3d(0, vertical * 0.18, 0);
            
            // Set the return value
            cir.setReturnValue(movement);
        }
    }
}
