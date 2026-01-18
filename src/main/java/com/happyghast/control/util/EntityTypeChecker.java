package com.happyghast.control.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class EntityTypeChecker {
	
	/**
	 * Checks if the given entity is a Happy Ghast
	 * @param entity The entity to check
	 * @return true if the entity is a Happy Ghast, false otherwise
	 */
	public static boolean isHappyGhast(Entity entity) {
		if (entity == null) {
			return false;
		}
		
		// Check if the entity type is GHAST
		// Happy Ghasts are a variant of ghasts introduced in Minecraft 1.21.6+
		// For version compatibility, we check multiple indicators
		
		// Get the entity type identifier
		Identifier entityId = Registries.ENTITY_TYPE.getId(entity.getType());
		if (entityId == null) {
			return false;
		}
		
		String entityIdString = entityId.toString();
		
		// Check if the identifier contains "happy_ghast" or "ghast"
		// In versions where Happy Ghasts exist, the ID will be "minecraft:happy_ghast"
		// In older versions, this will gracefully return false
		if (entityIdString.equals("minecraft:happy_ghast")) {
			return true;
		}
		
		// Fallback: check custom name if entity has one
		// This allows for testing/compatibility across versions
		if (entity.hasCustomName()) {
			try {
				String customName = entity.getCustomName().getString().toLowerCase();
				if (customName.contains("happy")) {
					// Also verify it's a ghast type
					if (entityIdString.contains("ghast")) {
						return true;
					}
				}
			} catch (Exception e) {
				// Silently handle any version-specific issues
			}
		}
		
		return false;
	}
	
	/**
	 * Checks if an entity is rideable
	 * This is a simple check for demonstration purposes
	 */
	public static boolean isRideable(Entity entity) {
		return entity != null;
	}
}
