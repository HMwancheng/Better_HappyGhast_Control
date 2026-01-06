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
                            // Handle horizontal movement with WASD/arrow keys
                            float forward = 0.0F;
                            float sideways = 0.0F;
                            
                            // Calculate movement input from player controls
                            forward = player.input.movementForward;
                            sideways = player.input.movementSideways;
                            
                            // Handle vertical movement from custom key bindings
                            double vertical = 0.0;
                            if (HappyGhastControlClient.ascendKey.isPressed()) {
                                vertical += 0.4;
                            }
                            if (HappyGhastControlClient.descendKey.isPressed()) {
                                vertical -= 0.4;
                            }
                            
                            // Get player rotation for direction
                            float yaw = player.getYaw();
                            float pitch = player.getPitch();
                            
                            // Calculate forward direction based on yaw
                            float yawRad = (float) Math.toRadians(yaw);
                            double forwardX = -Math.sin(yawRad) * Math.cos(Math.toRadians(pitch));
                            double forwardZ = Math.cos(yawRad) * Math.cos(Math.toRadians(pitch));
                            
                            // Calculate sideways direction (perpendicular to forward)
                            double sideX = -forwardZ;
                            double sideZ = forwardX;
                            
                            // Create movement vector
                            double moveX = (forwardX * forward + sideX * sideways) * 0.4;
                            double moveY = vertical;
                            double moveZ = (forwardZ * forward + sideZ * sideways) * 0.4;
                            
                            Vec3d movement = new Vec3d(moveX, moveY, moveZ);
                            
                            // Update entity velocity
                            entity.setVelocity(movement);
                            entity.velocityModified = true;
                        }
                    }
                }
            }
        }
    }
}
