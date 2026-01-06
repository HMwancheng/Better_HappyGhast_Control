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

    @Inject(method = "getRiddenInput", at = @At("HEAD"), cancellable = true)
    private void modifyHappyGhastRiddenInput(PlayerEntity player, Vec3d originalInput, CallbackInfoReturnable<Vec3d> cir) {
        GhastEntity ghast = (GhastEntity)(Object)this;
        
        // Only modify behavior for happy ghasts (no target)
        if (ghast.getTarget() == null) {
            // Get player movement inputs
            float forward = player.movementForward;
            float sideways = player.movementSideways;
            
            // Handle vertical movement
            float vertical = 0.0f;
            if (HappyGhastControlClient.ascendKey.isPressed()) {
                vertical += 0.5f;
            }
            if (HappyGhastControlClient.descendKey.isPressed()) {
                vertical -= 0.5f;
            }
            
            // Create movement vector
            Vec3d movement = new Vec3d(sideways, vertical, forward);
            
            // Apply movement scaling (matches reference MOD behavior)
            movement = movement.multiply(0.18);
            
            // Set the return value
            cir.setReturnValue(movement);
        }
    }
}
