package org.teamvoided.voided_variance.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.LingeringPotionItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ThrownPotion.class)
public abstract class ThrownPotionMixin implements ItemSupplier {

    @ModifyReturnValue(method = "isLingering", at = @At("RETURN"))
    boolean trueIsLingering(boolean original) {
        return original || getItem().getItem() instanceof LingeringPotionItem;
    }

}