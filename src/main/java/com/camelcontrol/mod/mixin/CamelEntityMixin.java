package com.camelcontrol.mod.mixin;

import com.camelcontrol.mod.CamelControlMod;
import com.camelcontrol.mod.ModConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.CamelEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CamelEntity.class)
public class CamelEntityMixin {

    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    private void onTravel(Vec3d movementInput, CallbackInfo ci) {
        CamelEntity camel = (CamelEntity) (Object) this;

        // 检查是否有玩家骑乘
        if (camel.hasPassengers()) {
            Entity passenger = camel.getFirstPassenger();
            if (passenger instanceof PlayerEntity player) {
                // 如果启用了自定义控制
                if (ModConfig.getConfig().enableCustomControls) {
                    // 应用自定义移动逻辑
                    applyCustomMovement(camel, player, movementInput);
                    ci.cancel(); // 取消原版移动逻辑
                }
            }
        }
    }

    private void applyCustomMovement(CamelEntity camel, PlayerEntity player, Vec3d movementInput) {
        // 获取玩家视角的水平方向（忽略垂直角度）
        float yaw = player.getYaw();
        double horizontalMovement = movementInput.horizontalLength();

        if (horizontalMovement > 0) {
            // 计算水平移动方向
            double moveX = -Math.sin(Math.toRadians(yaw)) * horizontalMovement * ModConfig.getConfig().horizontalSpeed;
            double moveZ = Math.cos(Math.toRadians(yaw)) * horizontalMovement * ModConfig.getConfig().horizontalSpeed;

            // 应用水平移动
            Vec3d velocity = camel.getVelocity();
            camel.setVelocity(moveX, velocity.y, moveZ);
        }

        // 应用重力（如果不在地面）
        camel.getVelocity().add(0, -0.04, 0);

        // 移动实体
        camel.move(camel.getMovementType(), camel.getVelocity());

        // 应用阻力
        camel.setVelocity(camel.getVelocity().multiply(0.91, 0.98, 0.91));

        CamelControlMod.LOGGER.debug("Applied custom camel movement: horizontal={}, vertical={}",
            horizontalMovement, camel.getVelocity().y);
    }
}