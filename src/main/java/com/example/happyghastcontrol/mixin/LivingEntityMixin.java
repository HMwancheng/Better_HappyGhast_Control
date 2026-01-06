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
        if (entity.getType() == EntityType.GHAST && entity.hasPassengers()) {
            for (PlayerEntity passenger : entity.getPassengers().stream().filter(e -> e instanceof PlayerEntity).map(e -> (PlayerEntity)e).toList()) {
                if (passenger instanceof ClientPlayerEntity) {
                    ClientPlayerEntity player = (ClientPlayerEntity)passenger;
                    
                    // Check if it's a happy ghast (using the isHappy method)
                    GhastEntity ghast = (GhastEntity)entity;
                    if (ghast.isHappy()) {
                        // Cancel vanilla travel logic for happy ghasts
                        ci.cancel();
                        
                        MinecraftClient client = MinecraftClient.getInstance();
                        if (client.player == player) {
                            // Handle horizontal movement with WASD/arrow keys
                            float forward = 0.0F;
                            float sideways = 0.0F;
                            
                            // Get key bindings from client options
                            KeyBinding forwardKey = client.options.forwardKey;
                            KeyBinding backwardKey = client.options.backKey;
                            KeyBinding leftKey = client.options.leftKey;
                            KeyBinding rightKey = client.options.rightKey;
                            
                            // Calculate horizontal movement input
                            if (forwardKey.isPressed()) {
                                forward += 1.0F;
                            }
                            if (backwardKey.isPressed()) {
                                forward -= 1.0F;
                            }
                            if (leftKey.isPressed()) {
                                sideways += 1.0F;
                            }
                            if (rightKey.isPressed()) {
                                sideways -= 1.0F;
                            }
                            
                            // Handle vertical movement
                            double vertical = 0.0;
                            if (HappyGhastControlClient.ascendKey.isPressed()) {
                                vertical += 1.0;
                            }
                            if (HappyGhastControlClient.descendKey.isPressed()) {
                                vertical -= 1.0;
                            }
                            
                            // Apply movement
                            Vec3d playerRotation = player.getRotationVector();
                            Vec3d horizontalDirection = new Vec3d(playerRotation.x, 0.0, playerRotation.z).normalize();
                            Vec3d sideDirection = new Vec3d(-horizontalDirection.z, 0.0, horizontalDirection.x).normalize();
                            
                            Vec3d movement = new Vec3d(
                                    horizontalDirection.x * forward + sideDirection.x * sideways,
                                    vertical,
                                    horizontalDirection.z * forward + sideDirection.z * sideways
                            ).normalize();
                            
                            // Apply movement speed (keep original speed)
                            double speed = 0.4; // Original ghast movement speed
                            movement = movement.multiply(speed);
                            
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
