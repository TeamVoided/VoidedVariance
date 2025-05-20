package org.teamvoided.voided_variance.item

import net.minecraft.component.type.PotionContentsComponent
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.GlassBottleItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.potion.Potions
import net.minecraft.registry.tag.FluidTags
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.hit.HitResult
import net.minecraft.world.RaycastContext
import net.minecraft.world.World
import net.minecraft.world.event.GameEvent

class CustomGlassBottleItem(val output: Item, settings: Settings) : GlassBottleItem(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)
        val hit = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY)
        if (hit.type == HitResult.Type.BLOCK) {
            val pos = hit.blockPos
            if (!world.canPlayerModifyAt(user, pos)) {
                return TypedActionResult.pass(stack)
            }
            if (world.getFluidState(pos).isIn(FluidTags.WATER)) {
                world.playSound(
                    user, user.x, user.y, user.z, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0f, 1.0f
                )
                world.emitGameEvent(user, GameEvent.FLUID_PICKUP, pos)
                return TypedActionResult.success(
                    this.fill(stack, user, PotionContentsComponent.createStack(output, Potions.WATER)), world.isClient()
                )
            }
        }

        return TypedActionResult.pass(stack)
    }
}
