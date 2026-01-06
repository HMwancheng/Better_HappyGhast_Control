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

    @Inject(method = "getRiddenInput(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;", at = @At("RETURN"), cancellable = true)
    private void modifyHappyGhastRiddenInput(PlayerEntity player, Vec3d originalInput, CallbackInfoReturnable<Vec3d> cir) {
        GhastEntity ghast = (GhastEntity)(Object)this;
        
        // Only modify behavior for happy ghasts (no target)
        if (ghast.getTarget() == null) {
            // Get player movement inputs
            float forward = player.input.movementForward;
            float sideways = player.input.movementSideways;
            
            // Handle vertical movement
            double vertical = 0.0;
            if (HappyGhastControlClient.ascendKey.isPressed()) {
                vertical += 0.4;
            }
            if (HappyGhastControlClient.descendKey.isPressed()) {
                vertical -= 0.4;
            }
            
            // Get player's yaw for direction
            float yaw = player.getYaw();
            double yawRad = Math.toRadians(yaw);
            
            // Calculate forward and sideways direction vectors
            double forwardX = -Math.sin(yawRad);
            double forwardZ = Math.cos(yawRad);
            double sideX = -forwardZ;
            double sideZ = forwardX;
            
            // Calculate movement vector
            double x = (forward * forwardX + sideways * sideX) * 0.18;
            double z = (forward * forwardZ + sideways * sideZ) * 0.18;
            
            // Create final movement vector
            Vec3d movement = new Vec3d(x, vertical * 0.18, z);
            
            // Set the return value
            cir.setReturnValue(movement);
        }
    }
}
