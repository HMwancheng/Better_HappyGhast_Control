package com.happyghast.control.mixin;

import com.happyghast.control.HappyGhastControlClient;
import com.happyghast.control.config.ModConfig;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    private void applyCustomMovement(Vec3d input, CallbackInfo ci) {
        LivingEntity ghast = (LivingEntity) (Object) this;
        
        // Only run for Happy Ghasts
        // Using string check to avoid dependency on the entity type class if it's not easily accessible,
        // or assuming "happy_ghast" id.
        // Better: Check registry key or cast if we had the class. 
        // For now, let's assume if it has passengers and is a Ghast-like entity.
        // But the user said "Happy Ghast". Let's check registry key if possible, or just proceed if we are "this".
        // Actually, the mixin is on LivingEntity, so "this" is the entity.
        
        if (!ghast.hasPassengers() || !(ghast.getFirstPassenger() instanceof PlayerEntity player)) {
            return;
        }

        // Check if this is a Happy Ghast (by checking registry key string to be safe/generic)
        String entityId = net.minecraft.registry.Registries.ENTITY_TYPE.getId(ghast.getType()).toString();
        if (!entityId.equals("minecraft:happy_ghast") && !entityId.contains("happy_ghast")) {
            return;
        }

        if (!(player instanceof ClientPlayerEntity clientPlayer)) {
            return;
        }

        // Get movement inputs from player
        float forward = clientPlayer.input.movementForward;
        float strafe = clientPlayer.input.movementSideways;
        
        // Calculate horizontal movement based on player's view direction
        float yaw = player.getYaw();
        float speedMultiplier = 0.5f * (float) ModConfig.getMovementMultiplier();
        
        // Convert player input to movement vector in world space
        // Only affect horizontal movement (X and Z), not vertical
        double moveX = (strafe * Math.cos(Math.toRadians(yaw)) + forward * Math.sin(Math.toRadians(yaw))) * speedMultiplier;
        double moveZ = (strafe * Math.sin(Math.toRadians(yaw)) - forward * Math.cos(Math.toRadians(yaw))) * speedMultiplier;
        
        // Handle vertical movement
        double moveY = 0;
        if (ModConfig.isVerticalControlEnabled()) {
            // Check if ascend key is pressed (Space)
            if (HappyGhastControlClient.ascendKey.isPressed()) {
                moveY = speedMultiplier * 0.8;
            }
            // Check if descend key is pressed (Ctrl)
            else if (HappyGhastControlClient.descendKey.isPressed()) {
                moveY = -speedMultiplier * 0.8;
            }
        }
        
        // Apply the calculated velocity to the ghast
        Vec3d velocity = new Vec3d(moveX, moveY, moveZ);
        ghast.setVelocity(velocity);
        
        // Update ghast rotation to match player's view
        ghast.setYaw(player.getYaw());
        ghast.setPitch(player.getPitch() * 0.5f);
        ghast.bodyYaw = ghast.getYaw();
        ghast.headYaw = ghast.getYaw();
        
        // Apply movement
        ghast.move(net.minecraft.entity.MovementType.SELF, ghast.getVelocity());
        
        // Apply air resistance (drag)
        ghast.setVelocity(ghast.getVelocity().multiply(0.95, 0.95, 0.95));
        
        // Cancel vanilla travel logic
        ci.cancel();
    }
}
