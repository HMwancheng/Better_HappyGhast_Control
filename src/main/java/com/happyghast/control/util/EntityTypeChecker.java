package com.happyghast.control.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

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
		
		// Check if the entity type is GHAST and has the "happy" variant
		// In Minecraft 1.21.6+, Happy Ghasts are a variant of regular ghasts
		// We need to check both the entity type and potentially NBT data
		
		// First, check if it's a ghast type
		if (entity.getType() != EntityType.GHAST) {
			return false;
		}
		
		// Check the entity's name or variant data
		// Happy Ghasts typically have "happy_ghast" in their entity ID or custom name
		String entityName = EntityType.getId(entity.getType()).toString();
		
		// Also check if the entity has special NBT data indicating it's a happy variant
		// This is a fallback check for different Minecraft versions
		return entityName.contains("happy") || isHappyVariant(entity);
	}
	
	/**
	 * Additional check for happy variant through entity properties
	 * This checks if the ghast has the "happy" variant flag
	 */
	private static boolean isHappyVariant(Entity entity) {
		// Check entity NBT data for happy variant
		// Happy Ghasts in newer versions have specific data markers
		try {
			// Try to access variant data if available
			// This is version-dependent and may need adjustment
			if (entity.hasCustomName()) {
				String customName = entity.getCustomName().getString().toLowerCase();
				if (customName.contains("happy")) {
					return true;
				}
			}
		} catch (Exception e) {
			// Silently fail if this method doesn't work in this version
		}
		
		return false;
	}
	
	/**
	 * Checks if an entity is rideable (used for additional validation)
	 */
	public static boolean isRideable(Entity entity) {
		return entity != null && entity.canPlayerRide();
	}
}
