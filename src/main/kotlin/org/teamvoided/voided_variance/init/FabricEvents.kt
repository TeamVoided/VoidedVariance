package org.teamvoided.voided_variance.init

import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Items
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.world.World
import org.teamvoided.voided_variance.block.CompositeBlock.Companion.POS_TO_CORNER
import org.teamvoided.voided_variance.block.CompositeBlock.Companion.addToComposite
import org.teamvoided.voided_variance.block.CompositeBlock.Companion.getCornerPosition
import org.teamvoided.voided_variance.block.CompositeBlock.Companion.getOffset
import org.teamvoided.voidlib.helpers.map

object FabricEvents {
    fun init() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register { builder ->
            builder.addBottomIngredient(VVItems.TINTED_POTION)
            builder.addBottomIngredient(VVItems.TINTED_SPLASH_POTION)
            builder.addBottomIngredient(VVItems.TINTED_LINGERING_POTION)

            builder.addItemRecipe(VVItems.TINTED_POTION, Items.GUNPOWDER, VVItems.TINTED_SPLASH_POTION)
            builder.addItemRecipe(VVItems.TINTED_SPLASH_POTION, Items.DRAGON_BREATH, VVItems.TINTED_LINGERING_POTION)
        }

        UseBlockCallback.EVENT.register(::addToCompositeFromCoreItem)
    }

    fun addToCompositeFromCoreItem(player: PlayerEntity, world: World, hand: Hand, hit: BlockHitResult): ActionResult {
        if (hit.type != HitResult.Type.BLOCK) return ActionResult.PASS
        val stack = player.getStackInHand(hand)
        if (!stack.isOf(Items.HEAVY_CORE)) return ActionResult.PASS

        val hitState = world.getBlockState(hit.blockPos)
        if (hitState.isOf(VVBlocks.HEAVY_CUBE)) {
            val corner = POS_TO_CORNER[getCornerPosition(hit).add(hit.side.getOffset().map { it * -2 })]
            if (corner != null && !hitState.get(corner)) return ActionResult.PASS
        }

        val pos = hit.blockPos.offset(hit.side)
        val state = world.getBlockState(pos)
        if (!state.isOf(VVBlocks.HEAVY_CUBE)) return ActionResult.PASS
        if (!world.canPlayerModifyAt(player, pos)) return ActionResult.PASS

        val clickedPos = getCornerPosition(BlockHitResult(hit.pos, hit.side.opposite, pos, hit.isInsideBlock))
        val cornerToBeAdded = POS_TO_CORNER[clickedPos] ?: return ActionResult.PASS
        addToComposite(state, cornerToBeAdded, world, pos, player, stack)
        return ActionResult.SUCCESS
    }
}