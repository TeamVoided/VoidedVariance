package org.teamvoided.voided_variance.item

import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.tags.FluidTags
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.BottleItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.alchemy.PotionContents
import net.minecraft.world.item.alchemy.Potions
import net.minecraft.world.level.ClipContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.gameevent.GameEvent
import net.minecraft.world.phys.HitResult


class CustomGlassBottleItem(val output: Item, properties: Properties) : BottleItem(properties) {

    override fun use(level: Level, user: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = user.getItemInHand(hand)
        val hit = getPlayerPOVHitResult(level, user, ClipContext.Fluid.SOURCE_ONLY)
        if (hit.type == HitResult.Type.BLOCK) {
            val pos = hit.blockPos
            if (!level.mayInteract(user, pos)) {
                return InteractionResultHolder.pass(stack)
            }
            if (level.getFluidState(pos).`is`(FluidTags.WATER)) {
                level.playSound(user, user.x, user.y, user.z, SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0f, 1.0f)
                level.gameEvent(user, GameEvent.FLUID_PICKUP, pos)
                return InteractionResultHolder.sidedSuccess(
                    turnBottleIntoItem(stack, user, PotionContents.createItemStack(output, Potions.WATER)),
                    level.isClientSide()
                )
            }
        }

        return InteractionResultHolder.pass(stack)
    }

}