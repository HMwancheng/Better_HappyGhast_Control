package com.example.happyghastcontrol.mixin;

import com.example.happyghastcontrol.HappyGhastControlClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GhastEntity.class)
public abstract class HappyGhastMixin {

    @Inject(method = "getRiddenInput", at = @At("RETURN"), cancellable = true)
    private void modifyHappyGhastRiddenInput(PlayerEntity player, Vec3d originalInput, CallbackInfoReturnable<Vec3d> cir) {
        GhastEntity ghast = (GhastEntity)(Object)this;
        
        // Only modify behavior for happy ghasts (no target)
        if (ghast.getTarget() == null) {
            // Get original input values
            double originalX = originalInput.x;
            double originalY = originalInput.y;
            double originalZ = originalInput.z;
            
            // Calculate horizontal movement from player input
            float forward = 0.0F;
            float sideways = 0.0F;
            
            // Get movement input from player controls
            if (player instanceof LocalPlayer) {
                LocalPlayer localPlayer = (LocalPlayer)player;
                forward = localPlayer.input.movementForward;
                sideways = localPlayer.input.movementSideways;
            }
            
            // Handle vertical movement from custom key bindings
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
            
            // Calculate final movement vector
            double moveX = (forwardX * forward + sideX * sideways) * 0.4;
            double moveY = vertical;
            double moveZ = (forwardZ * forward + sideZ * sideways) * 0.4;
            
            // Create movement vector
            Vec3d movement = new Vec3d(moveX, moveY, moveZ);
            
            // Set the return value
            cir.setReturnValue(movement);
        }
    }
}
