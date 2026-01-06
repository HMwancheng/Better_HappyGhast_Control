package com.example.happyghastcontrol.mixin;

import com.example.happyghastcontrol.injector.IPlayerInjector;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GhastEntity.class)
public abstract class HappyGhastMixin {

    @Inject(method = "getRiddenInput", at = @At("HEAD"), cancellable = true)
    private void betterHappyGhast$modifyRiddenInput(PlayerEntity player, Vec3d movementInput, CallbackInfoReturnable<Vec3d> cir) {
        GhastEntity ghast = (GhastEntity)(Object)this;
        
        if (ghast.getTarget() == null) {
            double speedMultiplier = 1.0;
            
            if (ghast.getWorld().getRegistryKey() != net.minecraft.registry.RegistryKey.of(
                    net.minecraft.registry.RegistryKeys.WORLD, 
                    net.minecraft.util.Identifier.of("the_end"))) {
                speedMultiplier = 1.2;
            }
            
            float forward = player.input.forward;
            float strafe = player.input.sideways;
            float vertical = 0.0f;
            
            if (player instanceof IPlayerInjector injector && injector.betterHappyGhast$isCtrlDown()) {
                vertical -= 0.5f;
            }
            
            Vec3d movement = new Vec3d(strafe, vertical, forward);
            movement = movement.multiply(0.18 * speedMultiplier);
            
            cir.setReturnValue(movement);
        }
    }
}
