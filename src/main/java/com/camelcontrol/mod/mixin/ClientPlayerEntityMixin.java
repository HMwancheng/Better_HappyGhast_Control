package com.camelcontrol.mod.mixin;

import com.camelcontrol.mod.CamelControlMod;
import com.camelcontrol.mod.ModConfig;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.passive.CamelEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Shadow
    public Input input;

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void onTickMovement(CallbackInfo ci) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;

        // 检查玩家是否骑乘着快乐恶魂
        if (player.getVehicle() instanceof CamelEntity camel && ModConfig.getConfig().enableCustomControls) {
            // 处理垂直移动输入
            handleVerticalMovement(player, camel);
        }
    }

    private void handleVerticalMovement(ClientPlayerEntity player, CamelEntity camel) {
        float verticalMovement = 0.0f;

        // 检查空格键（上升）
        if (input.jumping) {
            verticalMovement += ModConfig.getConfig().verticalSpeed;
        }

        // 检查Ctrl键（下降）- 在Minecraft中通常是潜行键
        if (input.sneaking) {
            verticalMovement -= ModConfig.getConfig().verticalSpeed;
        }

        if (verticalMovement != 0.0f) {
            // 应用垂直移动
            Vec3d velocity = camel.getVelocity();
            camel.setVelocity(velocity.x, velocity.y + verticalMovement, velocity.z);

            CamelControlMod.LOGGER.debug("Applied vertical camel movement: {}", verticalMovement);
        }
    }
}