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

    @Inject(method = "getRiddenInput", at = @At("RETURN"), cancellable = true)
    private void modifyHappyGhastRiddenInput(PlayerEntity player, Vec3d originalInput, CallbackInfoReturnable<Vec3d> cir) {
        GhastEntity ghast = (GhastEntity)(Object)this;
        
        // Only modify behavior for happy ghasts (no target)
        if (ghast.getTarget() == null) {
            // Handle vertical movement from custom key bindings
            double vertical = 0.0;
            if (HappyGhastControlClient.ascendKey.isPressed()) {
                vertical += 0.4;
            }
            if (HappyGhastControlClient.descendKey.isPressed()) {
                vertical -= 0.4;
            }
            
            // Use the original input's horizontal components and apply our vertical movement
            Vec3d movement = new Vec3d(originalInput.x * 0.18, vertical * 0.18, originalInput.z * 0.18);
            
            // Set the return value
            cir.setReturnValue(movement);
        }
    }
}
