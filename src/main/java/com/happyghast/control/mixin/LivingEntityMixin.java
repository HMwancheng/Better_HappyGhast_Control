package com.happyghast.control.mixin;

import com.happyghast.control.HappyGhastControlMod;
import com.happyghast.control.config.ModConfig;
import com.happyghast.control.util.EntityTypeChecker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	
	/**
	 * Inject into the travel method to modify movement behavior for Happy Ghasts
	 * This method is called every tick to update entity movement
	 */
	@Inject(method = "travel", at = @At("HEAD"), cancellable = true)
	private void onTravel(Vec3d movementInput, CallbackInfo ci) {
		LivingEntity entity = (LivingEntity) (Object) this;
		
		// Only process if this entity is being ridden
		if (!entity.hasPassengers()) {
			return;
		}
		
		// Check if the first passenger is a player
		Entity firstPassenger = entity.getFirstPassenger();
		if (!(firstPassenger instanceof PlayerEntity player)) {
			return;
		}
		
		// Check if this is a Happy Ghast
		if (!EntityTypeChecker.isHappyGhast(entity)) {
			return;
		}
		
		// Only process on client side for the local player
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.player == null || !client.player.equals(player)) {
			return;
		}
		
		// Apply custom movement logic
		applyCustomMovement(entity, player, movementInput);
		
		// Cancel the original travel method since we're handling it
		ci.cancel();
	}
	
	/**
	 * Apply custom movement logic for Happy Ghast riding
	 */
	private void applyCustomMovement(LivingEntity ghast, PlayerEntity player, Vec3d movementInput) {
		// Get movement inputs from player
		float forward = player.input.movementForward;
		float strafe = player.input.movementSideways;
		
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
			if (HappyGhastControlMod.ascendKey.isPressed()) {
				moveY = speedMultiplier * 0.8;
			}
			// Check if descend key is pressed (Ctrl)
			else if (HappyGhastControlMod.descendKey.isPressed()) {
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
		
		// Mark as on ground to prevent fall damage accumulation
		// ghast.setOnGround(false); // Ghasts should fly, not be on ground
		
		// Apply movement
		ghast.move(net.minecraft.entity.MovementType.SELF, ghast.getVelocity());
		
		// Apply air resistance (drag)
		ghast.setVelocity(ghast.getVelocity().multiply(0.95, 0.95, 0.95));
	}
}
