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
        
        // 1. Check for player passengers (Happy Ghast supports multiple, we find the first player)
        PlayerEntity player = null;
        for (Entity passenger : ghast.getPassengerList()) {
            if (passenger instanceof PlayerEntity) {
                player = (PlayerEntity) passenger;
                break;
            }
        }
        
        if (player == null) {
            return;
        }

        // 2. Identification check for Happy Ghast
        String entityId = net.minecraft.registry.Registries.ENTITY_TYPE.getId(ghast.getType()).toString();
        
        // Very broad check to ensure we hit it even if names vary in snapshots
        boolean isHappyGhast = entityId.contains("happy") && entityId.contains("ghast");
        
        if (!isHappyGhast) {
            return;
        }

        // 3. Ensure we are on the client and the player is the local player
        if (!(player instanceof ClientPlayerEntity clientPlayer)) {
            return;
        }

        net.minecraft.client.MinecraftClient client = net.minecraft.client.MinecraftClient.getInstance();
        if (clientPlayer != client.player) {
            return;
        }

        // Get movement inputs from game options (more reliable than input class)
        boolean pressingForward = client.options.forwardKey.isPressed();
        boolean pressingBack = client.options.backKey.isPressed();
        boolean pressingLeft = client.options.leftKey.isPressed();
        boolean pressingRight = client.options.rightKey.isPressed();

		float forwardInput = (pressingForward ? 1.0f : 0.0f) - (pressingBack ? 1.0f : 0.0f);
		float strafeInput = (pressingLeft ? 1.0f : 0.0f) - (pressingRight ? 1.0f : 0.0f);
        
        // Horizontal Movement Math
        float yaw = player.getYaw();
        float speedMultiplier = 0.5f * (float) ModConfig.getMovementMultiplier();
        double rad = Math.toRadians(yaw);
        
        // Corrected WASD to World-Space Vector translation
        // Minecraft 0 South, 90 West, 180 North, 270 East
        double moveX = (-strafeInput * Math.cos(rad) + forwardInput * Math.sin(-rad)) * speedMultiplier;
        double moveZ = (strafeInput * Math.sin(rad) + forwardInput * Math.cos(rad)) * speedMultiplier;
        
        // Vertical Movement
        double moveY = 0;
        if (ModConfig.isVerticalControlEnabled()) {
            if (HappyGhastControlClient.ascendKey.isPressed()) {
                moveY = speedMultiplier * 0.8;
            } else if (HappyGhastControlClient.descendKey.isPressed()) {
                moveY = -speedMultiplier * 0.8;
            }
        }
        
        // Apply velocity directly
        ghast.setVelocity(moveX, moveY, moveZ);
        
        // Sync rotation to player's view
        ghast.setYaw(player.getYaw());
        ghast.setPitch(player.getPitch() * 0.5f);
        ghast.bodyYaw = ghast.getYaw();
        ghast.headYaw = ghast.getYaw();
        
        // Replicate critical parts of LivingEntity.travel()
        // We Use MovementType.PLAYER because it's player-driven movement
        ghast.move(net.minecraft.entity.MovementType.SELF, ghast.getVelocity());
        
        // Friction/Drag application (same as creative mode flight/ghast roaming)
        ghast.setVelocity(ghast.getVelocity().multiply(0.95));
        
        // Cancel vanilla logic to prevent "move where looking" behavior
        ci.cancel();
    }
}
