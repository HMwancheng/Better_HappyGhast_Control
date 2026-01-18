import net.minecraft.client.network.ClientPlayerEntity;

// ... (in applyCustomMovement)
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
		
		// Mark as on ground to prevent fall damage accumulation
		// ghast.setOnGround(false); // Ghasts should fly, not be on ground
		
		// Apply movement
		ghast.move(net.minecraft.entity.MovementType.SELF, ghast.getVelocity());
		
		// Apply air resistance (drag)
		ghast.setVelocity(ghast.getVelocity().multiply(0.95, 0.95, 0.95));
	}
}
