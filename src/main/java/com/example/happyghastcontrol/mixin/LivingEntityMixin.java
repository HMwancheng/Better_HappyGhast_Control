package com.example.happyghastcontrol.mixin;

import com.example.happyghastcontrol.HappyGhastControlClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    private void modifyHappyGhastTravel(Vec3d movementInput, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;
        
        // Check if this entity is a ridden ghast
        if (entity.getType() == EntityType.GHAST && !entity.getPassengerList().isEmpty()) {
            for (PlayerEntity passenger : entity.getPassengerList().stream().filter(e -> e instanceof PlayerEntity).map(e -> (PlayerEntity)e).toList()) {
                if (passenger instanceof ClientPlayerEntity) {
                    ClientPlayerEntity player = (ClientPlayerEntity)passenger;
                    
                    // Check if it's a happy ghast (no target)
                    GhastEntity ghast = (GhastEntity)entity;
                    if (ghast.getTarget() == null) {
                        // Cancel vanilla travel logic for happy ghasts
                        ci.cancel();
                        
                        MinecraftClient client = MinecraftClient.getInstance();
                        if (client.player == player) {
                            // Calculate movement inputs
                            float forward = 0.0F;
                            float sideways = 0.0F;
                            double vertical = 0.0;
                            
                            // WASD/Arrow keys for horizontal movement
                            if (client.options.forwardKey.isPressed()) forward += 1.0F;
                            if (client.options.backKey.isPressed()) forward -= 1.0F;
                            if (client.options.leftKey.isPressed()) sideways += 1.0F;
                            if (client.options.rightKey.isPressed()) sideways -= 1.0F;
                            
                            // Custom keys for vertical movement
                            if (HappyGhastControlClient.ascendKey.isPressed()) vertical += 0.4;
                            if (HappyGhastControlClient.descendKey.isPressed()) vertical -= 0.4;
                            
                            // Get player's rotation for direction
                            float yaw = player.getYaw();
                            
                            // Convert yaw to radians
                            double yawRad = Math.toRadians(yaw);
                            
                            // Calculate movement direction
                            Vec3d forwardDir = new Vec3d(
                                -Math.sin(yawRad),
                                0.0,
                                Math.cos(yawRad)
                            ).normalize();
                            
                            Vec3d sideDir = new Vec3d(
                                -forwardDir.z,
                                0.0,
                                forwardDir.x
                            ).normalize();
                            
                            // Calculate movement vector components
                            double moveX = forwardDir.x * forward + sideDir.x * sideways;
                            double moveY = vertical;
                            double moveZ = forwardDir.z * forward + sideDir.z * sideways;
                            
                            // Create movement vector (no normalize to avoid issues when no input)
                            Vec3d movement = new Vec3d(moveX, moveY, moveZ).multiply(0.4);
                            
                            // Apply movement to entity
                            entity.setVelocity(movement);
                            entity.velocityModified = true;
                        }
                    }
                }
            }
        }
    }
}
