package com.happyghast.control.mixin;

import com.happyghast.control.util.EntityTypeChecker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	
	/**
	 * Inject into the tick method to update player-specific behavior while riding
	 * This ensures the player's inputs are properly captured
	 */
	@Inject(method = "tick", at = @At("HEAD"))
	private void onTick(CallbackInfo ci) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		
		// Check if player is riding a Happy Ghast
		if (player.hasVehicle()) {
			Entity vehicle = player.getVehicle();
			if (EntityTypeChecker.isHappyGhast(vehicle)) {
				// Ensure player can look around while riding
				// This is handled automatically by Minecraft, but we ensure it's not blocked
				// Additional custom behavior can be added here if needed
			}
		}
	}
	
	/**
	 * Modify the travel method for the player to prevent default riding behavior
	 * when on a Happy Ghast
	 */
	@Inject(method = "travel", at = @At("HEAD"), cancellable = false)
	private void onPlayerTravel(net.minecraft.util.math.Vec3d movementInput, CallbackInfo ci) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		
		// If riding a Happy Ghast, the movement is handled by the LivingEntityMixin
		// This method just ensures player inputs are properly registered
		if (player.hasVehicle() && EntityTypeChecker.isHappyGhast(player.getVehicle())) {
			// Player inputs are automatically captured by Minecraft's input system
			// No need to cancel here, just let it process normally
			// The actual movement override happens in LivingEntityMixin
		}
	}
}
