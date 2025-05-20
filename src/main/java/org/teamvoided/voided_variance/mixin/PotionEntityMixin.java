package org.teamvoided.voided_variance.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.LingeringPotionItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PotionEntity.class)
public abstract class PotionEntityMixin implements FlyingItemEntity {
    @ModifyReturnValue(method = "isLingering", at = @At("RETURN"))
    boolean trueIsLingering(boolean original) {
        return original || getStack().getItem() instanceof LingeringPotionItem;
    }
}
